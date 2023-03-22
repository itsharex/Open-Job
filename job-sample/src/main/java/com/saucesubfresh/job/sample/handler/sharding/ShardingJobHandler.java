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
package com.saucesubfresh.job.sample.handler.sharding;

import com.saucesubfresh.rpc.core.constants.CommonConstant;
import com.saucesubfresh.starter.job.register.annotation.JobHandler;
import com.saucesubfresh.starter.job.register.core.OpenJobHandler;
import com.saucesubfresh.starter.job.register.param.JobParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lijunping on 2023/03/21
 */
@Slf4j
@JobHandler(name = "sharding-job")
@Component
public class ShardingJobHandler implements OpenJobHandler {

    @Value("${com.saucesubfresh.rpc.server.server-address}")
    private String serverAddress;

    @Value("${com.saucesubfresh.rpc.server.server-port}")
    private Integer serverPort;

    @Override
    public void handler(JobParam jobParam) {
        String currentServerAddress = String.format(CommonConstant.ADDRESS_PATTERN, serverAddress, serverPort);
        List<String> totalTasks = new ArrayList<>();
        for (int i = 1; i < 1000; i++) {
            totalTasks.add("task" + i);
        }
        List<String> currentTasks = new ArrayList<>();
        List<String> shardingNodes = jobParam.getShardingNodes();
        for (int i = 0; i < totalTasks.size(); i++) {
            if (StringUtils.equals(currentServerAddress, shardingNodes.get(i % shardingNodes.size()))) {
                currentTasks.add(totalTasks.get(i));
            }
        }

        log.info("sharding-job 处理任务数量" + currentTasks.size());
    }
}
