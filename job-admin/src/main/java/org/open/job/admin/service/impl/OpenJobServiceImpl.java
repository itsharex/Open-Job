package org.open.job.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.open.job.admin.convert.OpenJobConvert;
import org.open.job.admin.dto.create.OpenJobCreateDTO;
import org.open.job.admin.dto.req.OpenJobReqDTO;
import org.open.job.admin.dto.resp.OpenJobRespDTO;
import org.open.job.admin.dto.update.OpenJobUpdateDTO;
import org.open.job.admin.entity.OpenJobDO;
import org.open.job.admin.mapper.OpenJobMapper;
import org.open.job.admin.event.JobLogEvent;
import org.open.job.admin.service.OpenJobLogService;
import org.open.job.admin.service.OpenJobService;
import org.open.job.common.enums.CommonStatusEnum;
import org.open.job.common.exception.ServiceException;
import org.open.job.common.serialize.SerializationUtils;
import org.open.job.common.vo.PageResult;
import org.open.job.core.Message;
import org.open.job.core.exception.RpcException;
import org.open.job.starter.schedule.core.ScheduleTaskManage;
import org.open.job.starter.schedule.cron.CronExpression;
import org.open.job.starter.schedule.domain.ScheduleTask;
import org.open.job.starter.server.cluster.ClusterInvokerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author lijunping on 2022/2/17
 */
@Slf4j
@Service
public class OpenJobServiceImpl extends ServiceImpl<OpenJobMapper, OpenJobDO> implements OpenJobService {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ClusterInvokerFactory clusterInvokerFactory;
    private final ScheduleTaskManage scheduleTaskManage;
    private final OpenJobLogService openJobLogService;
    private final OpenJobMapper openJobMapper;

    public OpenJobServiceImpl(ApplicationEventPublisher applicationEventPublisher, ClusterInvokerFactory clusterInvokerFactory, ScheduleTaskManage scheduleTaskManage, OpenJobLogService openJobLogService, OpenJobMapper openJobMapper) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.clusterInvokerFactory = clusterInvokerFactory;
        this.scheduleTaskManage = scheduleTaskManage;
        this.openJobLogService = openJobLogService;
        this.openJobMapper = openJobMapper;
    }

    @Override
    public PageResult<OpenJobRespDTO> selectPage(OpenJobReqDTO OpenJobReqDTO) {
        Page<OpenJobDO> page = openJobMapper.queryPage(OpenJobReqDTO);
        IPage<OpenJobRespDTO> convert = page.convert(OpenJobConvert.INSTANCE::convert);
        return PageResult.build(convert);
    }

    @Override
    public OpenJobRespDTO getById(Long id) {
        OpenJobDO OpenJobDO = openJobMapper.selectById(id);
        return OpenJobConvert.INSTANCE.convert(OpenJobDO);
    }

    @Override
    public List<OpenJobRespDTO> selectList(List<Long> taskList) {
        List<OpenJobDO> jobDOS = openJobMapper.queryList(taskList);
        return OpenJobConvert.INSTANCE.convertList(jobDOS);
    }

    @Override
    public boolean save(OpenJobCreateDTO OpenJobCreateDTO) {
        String cronExpression = OpenJobCreateDTO.getCronExpression();
        if (!CronExpression.isValidExpression(cronExpression)){
            throw new ServiceException("Invalid cronExpression");
        }
        openJobMapper.insert(OpenJobConvert.INSTANCE.convert(OpenJobCreateDTO));
        return true;
    }

    @Override
    public boolean updateById(OpenJobUpdateDTO OpenJobUpdateDTO) {
        openJobMapper.updateById(OpenJobConvert.INSTANCE.convert(OpenJobUpdateDTO));
        return true;
    }

    @Override
    public boolean start(Long id) {
        OpenJobDO OpenJobDO = openJobMapper.selectById(id);
        OpenJobDO.setStatus(CommonStatusEnum.YES.getValue());
        openJobMapper.updateById(OpenJobDO);
        ScheduleTask scheduleTask = createScheduleTask(OpenJobDO);
        scheduleTaskManage.addOrUpdateScheduleTask(scheduleTask);
        return true;
    }

    @Override
    public boolean stop(Long id) {
        OpenJobDO OpenJobDO = openJobMapper.selectById(id);
        OpenJobDO.setStatus(CommonStatusEnum.NO.getValue());
        openJobMapper.updateById(OpenJobDO);
        scheduleTaskManage.removeScheduleTask(id);
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        openJobMapper.deleteById(id);
        return true;
    }

    @Override
    public boolean run(Long id) {
        OpenJobDO openJobDO = openJobMapper.selectById(id);
        byte[] serializeData = SerializationUtils.serialize(openJobDO);
        Message message = new Message();
        message.setMsgId(openJobDO.getId());
        message.setBody(serializeData);
        return dispatchJob(message);
    }

    private ScheduleTask createScheduleTask(OpenJobDO OpenJobDO){
        ScheduleTask scheduleTask = new ScheduleTask();
        scheduleTask.setTaskId(OpenJobDO.getId());
        scheduleTask.setCronExpression(OpenJobDO.getCronExpression());
        return scheduleTask;
    }

    private boolean dispatchJob(Message message){
        String cause = null;
        try {
            clusterInvokerFactory.invoke(message);
        }catch (RpcException e){
            log.error("远程调用失败：{}", e.getMessage());
            cause = e.getMessage();
        }
        createLog(message.getMsgId(), cause);
        return StringUtils.isBlank(cause);
    }

    private void createLog(Long jobId, String cause){
        final JobLogEvent logEvent = openJobLogService.createLog(jobId, cause);
        applicationEventPublisher.publishEvent(logEvent);
    }
}
