package com.saucesubfresh.job.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saucesubfresh.job.admin.convert.OpenJobConvert;
import com.saucesubfresh.job.admin.dto.create.OpenJobCreateDTO;
import com.saucesubfresh.job.admin.dto.req.OpenJobReqDTO;
import com.saucesubfresh.job.admin.dto.resp.OpenJobRespDTO;
import com.saucesubfresh.job.admin.dto.update.OpenJobUpdateDTO;
import com.saucesubfresh.job.admin.entity.OpenJobDO;
import com.saucesubfresh.job.admin.mapper.OpenJobMapper;
import com.saucesubfresh.job.admin.service.OpenJobService;
import com.saucesubfresh.job.common.enums.CommonStatusEnum;
import com.saucesubfresh.job.common.exception.ServiceException;
import com.saucesubfresh.job.common.time.LocalDateTimeUtil;
import com.saucesubfresh.job.common.vo.PageResult;
import com.saucesubfresh.starter.schedule.core.ScheduleTaskManage;
import com.saucesubfresh.starter.schedule.cron.CronExpression;
import com.saucesubfresh.starter.schedule.domain.ScheduleTask;
import com.saucesubfresh.starter.schedule.executor.ScheduleTaskExecutor;
import com.saucesubfresh.starter.security.context.UserSecurityContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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

    @Override
    public boolean start(Long id) {
        OpenJobDO openJobDO = openJobMapper.selectById(id);
        openJobDO.setStatus(CommonStatusEnum.YES.getValue());
        openJobMapper.updateById(openJobDO);
        ScheduleTask scheduleTask = createScheduleTask(openJobDO);
        try {
            scheduleTaskManage.addScheduleTask(scheduleTask);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public boolean stop(Long id) {
        OpenJobDO openJobDO = openJobMapper.selectById(id);
        openJobDO.setStatus(CommonStatusEnum.NO.getValue());
        openJobMapper.updateById(openJobDO);
        ScheduleTask scheduleTask = createScheduleTask(openJobDO);
        try {
            scheduleTaskManage.removeScheduleTask(scheduleTask);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public boolean deleteById(Long id) {
        OpenJobDO openJobDO = openJobMapper.selectById(id);
        openJobMapper.deleteById(id);
        ScheduleTask scheduleTask = createScheduleTask(openJobDO);
        try {
            scheduleTaskManage.removeScheduleTask(scheduleTask);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public boolean run(Long id) {
        OpenJobDO openJobDO = openJobMapper.selectById(id);
        ScheduleTask scheduleTask = createScheduleTask(openJobDO);
        scheduleTaskExecutor.execute(List.of(scheduleTask));
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
                    result.add(formatTime(lastTime));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            result.add(e.getMessage());
        }
        return result;
    }

    @Override
    public String validateCron(String cronExpress) {
        try {
            CronExpression.validateExpression(cronExpress);
            return "success";
        } catch (ParseException e) {
            return e.getMessage();
        }
    }

    private void updateScheduleTask(OpenJobDO oldJob, OpenJobDO newJob){
        boolean status = oldJob.getStatus().equals(CommonStatusEnum.YES.getValue());
        boolean equals = StringUtils.equals(oldJob.getCronExpression(), newJob.getCronExpression());
        if (!equals && status){
            ScheduleTask scheduleTask = createScheduleTask(newJob);
            scheduleTaskManage.addScheduleTask(scheduleTask);
        }
    }

    private ScheduleTask createScheduleTask(OpenJobDO openJobDO){
        ScheduleTask scheduleTask = new ScheduleTask();
        scheduleTask.setTaskId(openJobDO.getId());
        scheduleTask.setCronExpression(openJobDO.getCronExpression());
        return scheduleTask;
    }

    private String formatTime(Date date){
        LocalDateTime localDateTime = LocalDateTimeUtil.convertDateToLDT(date);
        return LocalDateTimeUtil.format(localDateTime, LocalDateTimeUtil.DATETIME_FORMATTER);
    }
}
