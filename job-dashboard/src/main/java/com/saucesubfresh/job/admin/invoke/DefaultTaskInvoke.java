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

import com.saucesubfresh.job.admin.entity.OpenJobAppDO;
import com.saucesubfresh.job.admin.entity.OpenJobDO;
import com.saucesubfresh.job.admin.event.JobLogEvent;
import com.saucesubfresh.job.admin.mapper.OpenJobAppMapper;
import com.saucesubfresh.job.admin.mapper.OpenJobMapper;
import com.saucesubfresh.job.api.dto.create.OpenJobLogCreateDTO;
import com.saucesubfresh.job.common.constants.CommonConstant;
import com.saucesubfresh.job.common.domain.MessageBody;
import com.saucesubfresh.job.common.enums.CommandEnum;
import com.saucesubfresh.job.common.enums.CommonStatusEnum;
import com.saucesubfresh.job.common.enums.RouteStrategyEnum;
import com.saucesubfresh.job.common.serialize.SerializationUtils;
import com.saucesubfresh.rpc.client.cluster.ClusterInvoker;
import com.saucesubfresh.rpc.client.remoting.RemotingInvoker;
import com.saucesubfresh.rpc.core.Message;
import com.saucesubfresh.rpc.core.enums.ResponseStatus;
import com.saucesubfresh.rpc.core.exception.RpcException;
import com.saucesubfresh.rpc.core.information.ServerInformation;
import com.saucesubfresh.rpc.core.transport.MessageResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
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
    protected final RemotingInvoker remotingInvoker;
    private final OpenJobAppMapper openJobAppMapper;
    private final ApplicationEventPublisher eventPublisher;

    public DefaultTaskInvoke(OpenJobMapper openJobMapper,
                             ClusterInvoker clusterInvoker,
                             RemotingInvoker remotingInvoker,
                             OpenJobAppMapper openJobAppMapper,
                             ApplicationEventPublisher eventPublisher) {
        this.openJobMapper = openJobMapper;
        this.clusterInvoker = clusterInvoker;
        this.remotingInvoker = remotingInvoker;
        this.openJobAppMapper = openJobAppMapper;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void invoke(List<Long> taskList) {
        if (CollectionUtils.isEmpty(taskList)){
            return;
        }
        List<OpenJobDO> jobList = openJobMapper.queryList(taskList);
        jobList.forEach(e -> {
            LocalDateTime time = Objects.isNull(e.getUpdateTime()) ? e.getCreateTime(): e.getUpdateTime();
            Message message = new Message();
            message.setMsgId(String.valueOf(e.getId()));
            MessageBody messageBody = new MessageBody();
            messageBody.setJobId(e.getId());
            messageBody.setHandlerName(e.getHandlerName());
            messageBody.setParams(e.getParams());
            messageBody.setScript(e.getScript());
            messageBody.setScriptUpdateTime(String.valueOf(time.toEpochSecond(ZoneOffset.of("+8"))));

            if (StringUtils.isNotBlank(e.getShardingNodes())){
                List<String> shardingNodes = Arrays.asList(e.getShardingNodes().split(","));
                messageBody.setShardingNodes(shardingNodes);
            }
            messageBody.setCommand(CommandEnum.SCHEDULE.getValue());
            byte[] serializeData = SerializationUtils.serialize(messageBody);
            message.setBody(serializeData);
            OpenJobAppDO openJobAppDO = openJobAppMapper.selectById(e.getAppId());
            message.setNamespace(openJobAppDO.getAppName());

            if (RouteStrategyEnum.of(e.getRouteStrategy()) == RouteStrategyEnum.SHARDING){
                executeSharding(e, message, messageBody.getShardingNodes());
                return;
            }

            String serverId = null, errMsg = null;
            MessageResponseBody response = null;
            try {
                response = clusterInvoker.invoke(message);
            }catch (RpcException ex){
                errMsg = ex.getMessage();
                serverId = ex.getServerId();
                recordLog(e, null, errMsg, serverId);
                return;
            }
            if (response.getStatus() != ResponseStatus.SUCCESS){
                errMsg = response.getMsg();
            }
            serverId = response.getServerId();
            recordLog(e, response, errMsg, serverId);
        });

    }


    private void executeSharding(OpenJobDO openJobDO, Message message, List<String> shardingNodes){
        if (CollectionUtils.isEmpty(shardingNodes)){
            return;
        }

        List<ServerInformation> servers = shardingNodes.stream().map(e->{
            String[] split = StringUtils.split(e, CommonConstant.Symbol.MH);
            return new ServerInformation(split[0], Integer.parseInt(split[1]));
        }).collect(Collectors.toList());


        for (ServerInformation server : servers) {
            String serverId = null, errMsg = null;
            MessageResponseBody response = null;
            try {
                response = remotingInvoker.invoke(message, server);
            } catch (RpcException ex) {
                errMsg = ex.getMessage();
                serverId = ex.getServerId();
                recordLog(openJobDO, null, errMsg, serverId);
                return;
            }
            if (response.getStatus() != ResponseStatus.SUCCESS) {
                errMsg = response.getMsg();
            }
            serverId = response.getServerId();
            recordLog(openJobDO, response, errMsg, serverId);
        }
    }

    private void recordLog(OpenJobDO jobDO, MessageResponseBody response, String cause, String serverId){
        OpenJobLogCreateDTO openJobLogCreateDTO = new OpenJobLogCreateDTO();
        String requestId = Optional.ofNullable(response).map(MessageResponseBody::getRequestId).orElse("");
        openJobLogCreateDTO.setAppId(jobDO.getAppId());
        openJobLogCreateDTO.setJobId(jobDO.getId());
        openJobLogCreateDTO.setStatus(StringUtils.isBlank(cause) ? CommonStatusEnum.YES.getValue() : CommonStatusEnum.NO.getValue());
        openJobLogCreateDTO.setCause(cause);
        openJobLogCreateDTO.setServerId(serverId);
        openJobLogCreateDTO.setCreateTime(LocalDateTime.now());
        JobLogEvent logEvent = new JobLogEvent(this, openJobLogCreateDTO);
        eventPublisher.publishEvent(logEvent);
    }
}
