package org.open.job.client.handler;

import com.lightcode.rpc.client.process.MessageProcess;
import com.lightcode.rpc.core.Message;
import com.lightcode.rpc.core.exception.RpcException;
import lombok.extern.slf4j.Slf4j;
import org.open.job.common.serialize.SerializationUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lijunping on 2022/2/25
 */
@Slf4j
@Component
public class JobHandlerManager implements MessageProcess, InitializingBean, ApplicationContextAware {
    private ApplicationContext applicationContext;
    private final ConcurrentMap<String, JobHandler> processorMap = new ConcurrentHashMap<>();

    /**
     * 根据 handlerName 获取 JobHandler
     * @param handlerName 绑定的 handlerName
     * @return JobHandler
     */
    public JobHandler getJobHandler(String handlerName){
        final JobHandler jobHandler = processorMap.get(handlerName);
        if (ObjectUtils.isEmpty(jobHandler)) {
            throw new RpcException("JobHandlerName: " + handlerName + ", there is no bound JobHandler.");
        }
        return jobHandler;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, JobHandler> beans = this.applicationContext.getBeansOfType(JobHandler.class);
        if (ObjectUtils.isEmpty(beans)){
            log.warn("No JobHandler instance is defined.");
        }else {
            beans.forEach((k, v)-> this.processorMap.put(v.bindingJobHandlerName(), v));
        }
    }

    @Override
    public boolean process(Message message) {
        final byte[] body = message.getBody();
        final MessageBody messageBody = SerializationUtils.deserialize(body, MessageBody.class);
        JobHandler jobHandler = this.getJobHandler(messageBody.getHandlerName());
        jobHandler.handler(messageBody.getParams());
        return true;
    }
}
