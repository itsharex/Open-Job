package com.saucesubfresh.job.admin.component.schedule;

import com.saucesubfresh.job.admin.common.enums.SystemScheduleEnum;
import com.saucesubfresh.job.admin.component.alarm.AlarmService;
import com.saucesubfresh.job.admin.domain.ScheduleJob;
import com.saucesubfresh.job.admin.entity.OpenJobAppDO;
import com.saucesubfresh.job.admin.event.JobLogEvent;
import com.saucesubfresh.job.admin.mapper.OpenJobAppMapper;
import com.saucesubfresh.job.admin.mapper.OpenJobLogMapper;
import com.saucesubfresh.job.admin.service.OpenJobReportService;
import com.saucesubfresh.rpc.core.Message;
import com.saucesubfresh.rpc.core.enums.ResponseStatus;
import com.saucesubfresh.rpc.core.exception.RpcException;
import com.saucesubfresh.rpc.core.transport.MessageResponseBody;
import com.saucesubfresh.rpc.server.cluster.ClusterInvoker;
import com.saucesubfresh.starter.schedule.executor.ScheduleTaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import com.saucesubfresh.job.admin.dto.create.OpenJobLogCreateDTO;
import com.saucesubfresh.job.admin.entity.OpenJobDO;
import com.saucesubfresh.job.admin.mapper.OpenJobMapper;
import com.saucesubfresh.job.common.enums.CommonStatusEnum;
import com.saucesubfresh.job.common.serialize.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 定时任务远程调用实现
 * @author lijunping on 2021/8/31
 */
@Slf4j
@Component
public class ScheduleJobExecutor implements ScheduleTaskExecutor {

    @Value("${clear-interval}")
    private Integer clearInterval;

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ClusterInvoker clusterInvoker;
    private final OpenJobMapper openJobMapper;
    private final OpenJobLogMapper openJobLogMapper;
    private final AlarmService alarmService;
    private final OpenJobAppMapper openJobAppMapper;
    private final OpenJobReportService openJobReportService;

    public ScheduleJobExecutor(ApplicationEventPublisher applicationEventPublisher,
                               ClusterInvoker clusterInvoker,
                               OpenJobMapper openJobMapper,
                               OpenJobLogMapper openJobLogMapper,
                               AlarmService alarmService,
                               OpenJobAppMapper openJobAppMapper, OpenJobReportService openJobReportService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.clusterInvoker = clusterInvoker;
        this.openJobMapper = openJobMapper;
        this.openJobLogMapper = openJobLogMapper;
        this.alarmService = alarmService;
        this.openJobAppMapper = openJobAppMapper;
        this.openJobReportService = openJobReportService;
    }

    @Override
    public void execute(List<Long> taskList){
        // 系统任务
        executeSystemTask(taskList);
        // 执行其他任务
        List<OpenJobDO> jobList = openJobMapper.queryList(taskList);
        if (CollectionUtils.isEmpty(jobList)){
            return;
        }
        // 组装任务
        List<ScheduleJob> scheduleJobs = jobList.stream().map(e->{
            ScheduleJob scheduleJob = new ScheduleJob();
            MessageBody messageBody = new MessageBody();
            messageBody.setHandlerName(e.getHandlerName());
            messageBody.setParams(e.getParams());
            byte[] serializeData = SerializationUtils.serialize(messageBody);
            Message message = new Message();
            message.setMsgId(String.valueOf(e.getId()));
            message.setBody(serializeData);
            OpenJobAppDO openJobAppDO = openJobAppMapper.selectById(e.getAppId());
            scheduleJob.setAppName(openJobAppDO.getAppName());
            scheduleJob.setMessage(message);
            return scheduleJob;
        }).collect(Collectors.toList());

        // 分发任务
        scheduleJobs.forEach(scheduleJob->{
            String cause = null;
            MessageResponseBody response = null;
            final String appName = scheduleJob.getAppName();
            final Message message = scheduleJob.getMessage();
            try {
                response = clusterInvoker.invoke(appName, message);
            }catch (RpcException e){
                cause = e.getMessage();
            }
            if (Objects.nonNull(response) && response.getStatus() != ResponseStatus.SUCCESS){
                cause = response.getErrorMsg();
            }
            createLog(Long.parseLong(message.getMsgId()), cause);
        });
    }

    private void executeSystemTask(List<Long> taskList){
        if (taskList.contains(SystemScheduleEnum.REPORT.getValue())){
            openJobReportService.insertReport();
        }
        if (taskList.contains(SystemScheduleEnum.CLEAR_LOG.getValue())){
            openJobLogMapper.clearLog(clearInterval);
        }
        if (taskList.contains(SystemScheduleEnum.ALARM_MESSAGE.getValue())){
            alarmService.sendAlarm();
        }
    }

    private void createLog(Long jobId, String cause){
        OpenJobLogCreateDTO openJobLogCreateDTO = new OpenJobLogCreateDTO();
        openJobLogCreateDTO.setJobId(jobId);
        openJobLogCreateDTO.setStatus(StringUtils.isBlank(cause) ? CommonStatusEnum.YES.getValue() : CommonStatusEnum.NO.getValue());
        openJobLogCreateDTO.setCause(cause);
        openJobLogCreateDTO.setCreateTime(LocalDateTime.now());
        JobLogEvent logEvent = new JobLogEvent(this, openJobLogCreateDTO);
        applicationEventPublisher.publishEvent(logEvent);
    }
}
