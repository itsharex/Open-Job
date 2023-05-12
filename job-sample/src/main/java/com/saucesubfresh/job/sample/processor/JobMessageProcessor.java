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
package com.saucesubfresh.job.sample.processor;

import com.saucesubfresh.job.common.domain.MessageBody;
import com.saucesubfresh.job.common.domain.ResponseBody;
import com.saucesubfresh.job.common.enums.CommandEnum;
import com.saucesubfresh.job.common.json.JSON;
import com.saucesubfresh.job.common.metrics.SystemMetricsInfo;
import com.saucesubfresh.job.common.metrics.SystemMetricsUtils;
import com.saucesubfresh.job.common.serialize.SerializationUtils;
import com.saucesubfresh.rpc.core.Message;
import com.saucesubfresh.rpc.core.exception.RpcException;
import com.saucesubfresh.rpc.server.process.MessageProcess;
import com.saucesubfresh.starter.job.register.core.JobHandlerRegister;
import com.saucesubfresh.starter.job.register.core.OpenJobHandler;
import com.saucesubfresh.starter.job.register.param.JobParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * @author lijunping on 2022/2/25
 */
@Slf4j
@Component
public class JobMessageProcessor implements MessageProcess {

    private final JobHandlerRegister jobHandlerRegister;

    public JobMessageProcessor(JobHandlerRegister jobHandlerRegister) {
        this.jobHandlerRegister = jobHandlerRegister;
    }

    @Override
    public byte[] process(Message message) {
        final byte[] body = message.getBody();
        final MessageBody messageBody = SerializationUtils.deserialize(body, MessageBody.class);
        CommandEnum command = CommandEnum.of(messageBody.getCommand());
        if (Objects.isNull(command)){
            throw new RpcException("the parameter command must not be null");
        }

        switch (command){
            case SCHEDULE:
                handlerSchedule(messageBody);
                return null;
            case METRICS:
                ResponseBody responseBody = handlerMetrics();
                return SerializationUtils.serialize(responseBody);
            default:
                throw new UnsupportedOperationException("Unsupported Operation");
        }
    }

    private void handlerSchedule(MessageBody messageBody){
        String handlerName = messageBody.getHandlerName();
        OpenJobHandler openJobHandler = jobHandlerRegister.getJobHandler(handlerName);
        if (ObjectUtils.isEmpty(openJobHandler)) {
            throw new RpcException("JobHandlerName: " + handlerName + ", there is no bound JobHandler.");
        }

        JobParam jobParam = JobParam.builder()
                .jobId(messageBody.getJobId())
                .params(messageBody.getParams())
                .script(messageBody.getScript())
                .scriptUpdateTime(messageBody.getScriptUpdateTime())
                .shardingNodes(messageBody.getShardingNodes())
                .build();

        try {
            openJobHandler.handler(jobParam);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RpcException("JobHandlerName: " + handlerName + ", execute exception:" + e.getMessage());
        }
    }

    private ResponseBody handlerMetrics(){
        ResponseBody responseBody = new ResponseBody();
        SystemMetricsInfo systemMetricsInfo = new SystemMetricsInfo();
        int cpuProcessorNum = SystemMetricsUtils.getCPUProcessorNum();
        double cpuLoadPercent = SystemMetricsUtils.getCPULoadPercent();
        double jvmUsedMemory = SystemMetricsUtils.getJvmUsedMemory();
        double jvmMaxMemory = SystemMetricsUtils.getJvmMaxMemory();
        double jvmMemoryUsage = SystemMetricsUtils.getJvmMemoryUsage(jvmUsedMemory, jvmMaxMemory);
        long[] diskInfo = SystemMetricsUtils.getDiskInfo();
        long freeDiskSpace = diskInfo[0];
        long totalDiskSpace = diskInfo[1];
        double diskUsed = SystemMetricsUtils.getDiskUsed(totalDiskSpace, freeDiskSpace);
        double diskTotal = SystemMetricsUtils.getDiskTotal(totalDiskSpace);
        double diskUsage = SystemMetricsUtils.getDiskUsage(diskUsed, diskTotal);

        systemMetricsInfo.setCpuProcessors(cpuProcessorNum);
        systemMetricsInfo.setCpuLoad(cpuLoadPercent);
        systemMetricsInfo.setJvmMaxMemory(jvmMaxMemory);
        systemMetricsInfo.setJvmUsedMemory(jvmUsedMemory);
        systemMetricsInfo.setJvmMemoryUsage(jvmMemoryUsage);
        systemMetricsInfo.setDiskUsed(diskUsed);
        systemMetricsInfo.setDiskTotal(diskTotal);
        systemMetricsInfo.setDiskUsage(diskUsage);

        responseBody.setData(JSON.toJSON(systemMetricsInfo));
        return responseBody;
    }
}
