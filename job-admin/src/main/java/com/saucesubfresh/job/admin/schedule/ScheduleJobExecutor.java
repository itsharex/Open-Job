package com.saucesubfresh.job.admin.schedule;

import com.saucesubfresh.job.admin.common.enums.SystemScheduleEnum;
import com.saucesubfresh.job.admin.event.JobLogEvent;
import com.saucesubfresh.job.admin.service.OpenJobReportService;
import com.saucesubfresh.rpc.core.Message;
import com.saucesubfresh.rpc.core.exception.RpcException;
import com.saucesubfresh.rpc.server.cluster.ClusterInvoker;
import com.saucesubfresh.starter.schedule.executor.ScheduleTaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import com.saucesubfresh.job.admin.dto.create.OpenJobLogCreateDTO;
import com.saucesubfresh.job.admin.entity.OpenJobDO;
import com.saucesubfresh.job.admin.mapper.OpenJobMapper;
import com.saucesubfresh.job.common.enums.CommonStatusEnum;
import com.saucesubfresh.job.common.serialize.SerializationUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 定时任务远程调用实现
 * @author lijunping on 2021/8/31
 */
@Slf4j
@Component
public class ScheduleJobExecutor implements ScheduleTaskExecutor {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ClusterInvoker clusterInvoker;
    private final OpenJobMapper openJobMapper;
    private final OpenJobReportService openJobReportService;

    public ScheduleJobExecutor(ApplicationEventPublisher applicationEventPublisher, ClusterInvoker clusterInvoker, OpenJobMapper openJobMapper, OpenJobReportService openJobReportService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.clusterInvoker = clusterInvoker;
        this.openJobMapper = openJobMapper;
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
        List<Message> messages = jobList.stream().map(e->{
            MessageBody messageBody = new MessageBody();
            messageBody.setHandlerName(e.getHandlerName());
            messageBody.setParams(e.getParams());
            byte[] serializeData = SerializationUtils.serialize(messageBody);
            Message message = new Message();
            message.setMsgId(String.valueOf(e.getId()));
            message.setBody(serializeData);
            return message;
        }).collect(Collectors.toList());

        // 分发任务
        messages.forEach(message->{
            String cause = null;
            try {
                clusterInvoker.invoke(message);
            }catch (RpcException e){
                cause = e.getMessage();
            }
            createLog(Long.parseLong(message.getMsgId()), cause);
        });
    }

    private void executeSystemTask(List<Long> taskList){
        if (taskList.contains(SystemScheduleEnum.REPORT.getValue())){
            openJobReportService.insertReport();
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
