package org.open.job.admin.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lightcode.starter.schedule.core.ScheduleTaskManage;
import com.lightcode.starter.schedule.cron.CronExpression;
import com.lightcode.starter.schedule.domain.ScheduleTask;
import com.lightcode.starter.schedule.executor.ScheduleTaskExecutor;
import com.lightcode.starter.security.context.UserSecurityContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.open.job.admin.convert.OpenJobConvert;
import org.open.job.admin.dto.create.OpenJobCreateDTO;
import org.open.job.admin.dto.req.OpenJobReqDTO;
import org.open.job.admin.dto.resp.OpenJobRespDTO;
import org.open.job.admin.dto.update.OpenJobUpdateDTO;
import org.open.job.admin.entity.OpenJobDO;
import org.open.job.admin.mapper.OpenJobMapper;
import org.open.job.admin.service.OpenJobService;
import org.open.job.common.enums.CommonStatusEnum;
import org.open.job.common.exception.ServiceException;
import org.open.job.common.vo.PageResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author lijunping on 2022/2/17
 */
@Service
public class OpenJobServiceImpl extends ServiceImpl<OpenJobMapper, OpenJobDO> implements OpenJobService {

    private final ScheduleTaskExecutor scheduleTaskExecutor;
    private final ScheduleTaskManage scheduleTaskManage;
    private final OpenJobMapper openJobMapper;

    public OpenJobServiceImpl(ScheduleTaskExecutor scheduleTaskExecutor, ScheduleTaskManage scheduleTaskManage, OpenJobMapper openJobMapper) {
        this.scheduleTaskExecutor = scheduleTaskExecutor;
        this.scheduleTaskManage = scheduleTaskManage;
        this.openJobMapper = openJobMapper;
    }

    @Override
    public PageResult<OpenJobRespDTO> selectPage(OpenJobReqDTO openJobReqDTO) {
        Page<OpenJobDO> page = openJobMapper.queryPage(openJobReqDTO);
        IPage<OpenJobRespDTO> convert = page.convert(OpenJobConvert.INSTANCE::convert);
        return PageResult.build(convert);
    }

    @Override
    public OpenJobRespDTO getById(Long id) {
        OpenJobDO OpenJobDO = openJobMapper.selectById(id);
        return OpenJobConvert.INSTANCE.convert(OpenJobDO);
    }

    @Override
    public boolean save(OpenJobCreateDTO openJobCreateDTO) {
        String cronExpression = openJobCreateDTO.getCronExpression();
        if (!CronExpression.isValidExpression(cronExpression)){
            throw new ServiceException("Invalid cronExpression");
        }
        openJobCreateDTO.setCreateTime(LocalDateTime.now());
        openJobCreateDTO.setCreateUser(UserSecurityContextHolder.getUserId());
        int insert = openJobMapper.insert(OpenJobConvert.INSTANCE.convert(openJobCreateDTO));
        return insert != 0;
    }

    @Override
    public boolean updateById(OpenJobUpdateDTO openJobUpdateDTO) {
        OpenJobDO openJobDO = openJobMapper.selectById(openJobUpdateDTO.getId());
        OpenJobDO convert = OpenJobConvert.INSTANCE.convert(openJobUpdateDTO);
        int update = openJobMapper.updateById(convert);
        updateScheduleTask(openJobDO, convert);
        return update != 0;
    }

    private void updateScheduleTask(OpenJobDO openJobDO, OpenJobDO convert){
        boolean status = openJobDO.getStatus().equals(CommonStatusEnum.YES.getValue());
        boolean equals = StringUtils.equals(openJobDO.getCronExpression(), convert.getCronExpression());
        if (!equals && status){
            ScheduleTask scheduleTask = createScheduleTask(convert);
            scheduleTaskManage.addScheduleTask(scheduleTask);
        }
    }

    @Override
    public boolean start(Long id) {
        OpenJobDO openJobDO = openJobMapper.selectById(id);
        openJobDO.setStatus(CommonStatusEnum.YES.getValue());
        openJobMapper.updateById(openJobDO);
        ScheduleTask scheduleTask = createScheduleTask(openJobDO);
        return scheduleTaskManage.addScheduleTask(scheduleTask);
    }

    @Override
    public boolean stop(Long id) {
        OpenJobDO OpenJobDO = openJobMapper.selectById(id);
        OpenJobDO.setStatus(CommonStatusEnum.NO.getValue());
        openJobMapper.updateById(OpenJobDO);
        return scheduleTaskManage.removeScheduleTask(id);
    }

    @Override
    public boolean deleteById(Long id) {
        openJobMapper.deleteById(id);
        return true;
    }

    @Override
    public boolean run(Long id) {
        scheduleTaskExecutor.execute(List.of(id));
        return true;
    }

    @Override
    public List<String> nextTriggerTime(String cronExpress) {
        List<String> result = new ArrayList<>();
        try {
            Date lastTime = new Date();
            for (int i = 0; i < 5; i++) {
                lastTime = new CronExpression(cronExpress).getNextValidTimeAfter(lastTime);
                if (lastTime != null) {
                    result.add(DateUtil.formatDateTime(lastTime));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            result.add(e.getMessage());
        }
        return result;
    }

    private ScheduleTask createScheduleTask(OpenJobDO openJobDO){
        ScheduleTask scheduleTask = new ScheduleTask();
        scheduleTask.setTaskId(openJobDO.getId());
        scheduleTask.setCronExpression(openJobDO.getCronExpression());
        return scheduleTask;
    }
}
