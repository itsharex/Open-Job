package org.open.job.admin.schedule;


import lombok.extern.slf4j.Slf4j;
import org.open.job.admin.dto.resp.OpenJobRespDTO;
import org.open.job.admin.service.OpenJobService;
import org.open.job.common.serialize.SerializationUtils;
import org.open.job.core.Message;
import org.open.job.core.exception.RpcException;
import org.open.job.starter.schedule.executor.ScheduleTaskExecutor;
import org.open.job.starter.server.cluster.ClusterInvokerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 定时任务远程调用实现
 * @author lijunping on 2021/8/31
 */
@Slf4j
@Component
public class ScheduleJobExecutor implements ScheduleTaskExecutor {

    @Autowired
    private ClusterInvokerFactory clusterInvokerFactory;

    @Autowired
    private OpenJobService openJobService;

    @Override
    public void execute(List<Long> taskList){
        List<OpenJobRespDTO> jobList = openJobService.selectList(taskList);
        if (CollectionUtils.isEmpty(jobList)){
            return;
        }
        // 1 组装任务
        List<Message> messages = jobList.stream().map(e->{
            byte[] serializeData = SerializationUtils.serialize(e);
            Message message = new Message();
            message.setMsgId(e.getId());
            message.setBody(serializeData);
            return message;
        }).collect(Collectors.toList());

        // 2 分发任务
        messages.forEach(message->{
            try {
                clusterInvokerFactory.invoke(message);
            }catch (RpcException e){
                log.error("远程调用失败：{}", e.getMessage());
            }
        });

    }
}
