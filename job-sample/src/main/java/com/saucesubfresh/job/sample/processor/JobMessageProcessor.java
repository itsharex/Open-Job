package com.saucesubfresh.job.sample.processor;

import com.saucesubfresh.job.common.domain.MessageBody;
import com.saucesubfresh.job.common.serialize.SerializationUtils;
import com.saucesubfresh.job.core.collector.JobHandlerCollector;
import com.saucesubfresh.job.core.collector.OpenJobHandler;
import com.saucesubfresh.rpc.core.Message;
import com.saucesubfresh.rpc.core.exception.RpcException;
import com.saucesubfresh.rpc.server.process.MessageProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @author lijunping on 2022/2/25
 */
@Slf4j
@Component
public class JobMessageProcessor implements MessageProcess {

    private final JobHandlerCollector jobHandlerCollector;

    public JobMessageProcessor(JobHandlerCollector jobHandlerCollector) {
        this.jobHandlerCollector = jobHandlerCollector;
    }

    @Override
    public byte[] process(Message message) {
        final byte[] body = message.getBody();
        final MessageBody messageBody = SerializationUtils.deserialize(body, MessageBody.class);
        String handlerName = messageBody.getHandlerName();
        OpenJobHandler openJobHandler = jobHandlerCollector.getJobHandler(handlerName);
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
