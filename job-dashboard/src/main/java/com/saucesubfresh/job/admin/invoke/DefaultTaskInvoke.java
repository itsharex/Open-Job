/*
 * Copyright © 2022 organization SauceSubFresh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.saucesubfresh.job.admin.invoke;

import com.saucesubfresh.job.admin.domain.ScheduleJob;
import com.saucesubfresh.job.admin.entity.OpenJobAppDO;
import com.saucesubfresh.job.admin.entity.OpenJobDO;
import com.saucesubfresh.job.admin.event.JobLogEvent;
import com.saucesubfresh.job.admin.mapper.OpenJobAppMapper;
import com.saucesubfresh.job.admin.mapper.OpenJobMapper;
import com.saucesubfresh.job.api.dto.create.OpenJobLogCreateDTO;
import com.saucesubfresh.job.common.domain.MessageBody;
import com.saucesubfresh.job.common.enums.CommonStatusEnum;
import com.saucesubfresh.job.common.serialize.SerializationUtils;
import com.saucesubfresh.rpc.client.cluster.ClusterInvoker;
import com.saucesubfresh.rpc.core.Message;
import com.saucesubfresh.rpc.core.enums.ResponseStatus;
import com.saucesubfresh.rpc.core.exception.RpcException;
import com.saucesubfresh.rpc.core.transport.MessageResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author lijunping on 2022/8/18
 */
@Slf4j
@Component
public class DefaultTaskInvoke implements TaskInvoke{

    private final OpenJobMapper openJobMapper;
    private final ClusterInvoker clusterInvoker;
    private final OpenJobAppMapper openJobAppMapper;
    private final ApplicationEventPublisher eventPublisher;

    public DefaultTaskInvoke(OpenJobMapper openJobMapper,
                             ClusterInvoker clusterInvoker,
                             OpenJobAppMapper openJobAppMapper,
                             ApplicationEventPublisher eventPublisher) {
        this.openJobMapper = openJobMapper;
        this.clusterInvoker = clusterInvoker;
        this.openJobAppMapper = openJobAppMapper;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void invoke(List<Long> taskList) {
        if (CollectionUtils.isEmpty(taskList)){
            return;
        }
        List<OpenJobDO> jobList = openJobMapper.queryList(taskList);

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
        scheduleJobs.forEach(scheduleJob -> {
            String errMsg = null;
            MessageResponseBody response = null;
            try {
                response = doInvoke(scheduleJob);
            }catch (RpcException e){
                log.error(e.getMessage(), e);
                errMsg = e.getMessage();
            }
            recordLog(scheduleJob, response, errMsg);
        });
    }

    private MessageResponseBody doInvoke(ScheduleJob scheduleJob){
        MessageResponseBody response;
        Message message = scheduleJob.getMessage();
        message.setNamespace(scheduleJob.getAppName());
        try {
            response = clusterInvoker.invoke(message);
        }catch (RpcException e){
            throw new RpcException(e.getMessage());
        }
        if (Objects.nonNull(response) && response.getStatus() != ResponseStatus.SUCCESS){
            throw new RpcException("Job 服务端处理失败");
        }
        return response;
    }

    private void recordLog(ScheduleJob scheduleJob, MessageResponseBody response, String cause){
        long jobId = Long.parseLong(scheduleJob.getMessage().getMsgId());
        String requestId = Optional.ofNullable(response).map(MessageResponseBody::getRequestId).orElse("");
        OpenJobLogCreateDTO openJobLogCreateDTO = new OpenJobLogCreateDTO();
        openJobLogCreateDTO.setJobId(jobId);
        openJobLogCreateDTO.setStatus(StringUtils.isBlank(cause) ? CommonStatusEnum.YES.getValue() : CommonStatusEnum.NO.getValue());
        openJobLogCreateDTO.setCause(cause);
        openJobLogCreateDTO.setCreateTime(LocalDateTime.now());
        JobLogEvent logEvent = new JobLogEvent(this, openJobLogCreateDTO);
        eventPublisher.publishEvent(logEvent);
    }
}
