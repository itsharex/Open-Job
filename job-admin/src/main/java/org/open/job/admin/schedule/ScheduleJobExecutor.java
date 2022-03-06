package org.open.job.admin.schedule;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.open.job.admin.dto.create.OpenJobLogCreateDTO;
import org.open.job.admin.entity.OpenJobDO;
import org.open.job.admin.event.JobLogEvent;
import org.open.job.admin.mapper.OpenJobMapper;
import org.open.job.common.enums.CommonStatusEnum;
import org.open.job.common.serialize.SerializationUtils;
import org.open.job.core.Message;
import org.open.job.core.MessageBody;
import org.open.job.core.exception.RpcException;
import org.open.job.starter.schedule.executor.ScheduleTaskExecutor;
import org.open.job.starter.server.cluster.ClusterInvokerFactory;
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
public class ScheduleJobExecutor implements ScheduleTaskExecutor{

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ClusterInvokerFactory clusterInvokerFactory;
    private final OpenJobMapper openJobMapper;

    public ScheduleJobExecutor(ApplicationEventPublisher applicationEventPublisher, ClusterInvokerFactory clusterInvokerFactory, OpenJobMapper openJobMapper) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.clusterInvokerFactory = clusterInvokerFactory;
        this.openJobMapper = openJobMapper;
    }

    @Override
    public void execute(List<Long> taskList){
        List<OpenJobDO> jobList = openJobMapper.queryList(taskList);
        if (CollectionUtils.isEmpty(jobList)){
            return;
        }
        // 1 组装任务
        List<Message> messages = jobList.stream().map(e->{
            MessageBody messageBody = new MessageBody();
            messageBody.setHandlerName(e.getHandlerName());
            messageBody.setParams(e.getParams());
            byte[] serializeData = SerializationUtils.serialize(messageBody);
            Message message = new Message();
            message.setMsgId(e.getId());
            message.setBody(serializeData);
            return message;
        }).collect(Collectors.toList());

        // 2 分发任务
        messages.forEach(message->{
            String cause = null;
            try {
                clusterInvokerFactory.invoke(message);
            }catch (RpcException e){
                cause = e.getMessage();
            }
            createLog(message.getMsgId(), cause);
        });
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
