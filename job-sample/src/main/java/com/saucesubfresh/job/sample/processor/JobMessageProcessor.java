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
import com.saucesubfresh.job.common.serialize.SerializationUtils;
import com.saucesubfresh.rpc.core.Message;
import com.saucesubfresh.rpc.core.exception.RpcException;
import com.saucesubfresh.rpc.server.process.MessageProcess;
import com.saucesubfresh.starter.job.register.core.JobHandlerRegister;
import com.saucesubfresh.starter.job.register.core.OpenJobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

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
        String handlerName = messageBody.getHandlerName();
        OpenJobHandler openJobHandler = jobHandlerRegister.getJobHandler(handlerName);
        if (ObjectUtils.isEmpty(openJobHandler)) {
            throw new RpcException("JobHandlerName: " + handlerName + ", there is no bound JobHandler.");
        }
        try {
            openJobHandler.handler(messageBody.getParams());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RpcException("JobHandlerName: " + handlerName + ", execute exception:" + e.getMessage());
        }
        return null;
    }
}
