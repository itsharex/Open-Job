package org.open.job.client.handler;

import com.saucesubfresh.rpc.client.process.MessageProcess;
import com.saucesubfresh.rpc.core.Message;
import com.saucesubfresh.rpc.core.exception.RpcException;
import lombok.extern.slf4j.Slf4j;
import org.open.job.client.annotation.JobHandler;
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
    private final ConcurrentMap<String, OpenJobHandler> processorMap = new ConcurrentHashMap<>();

    /**
     * 根据 handlerName 获取 JobHandler
     * @param handlerName 绑定的 handlerName
     * @return JobHandler
     */
    public OpenJobHandler getJobHandler(String handlerName){
        final OpenJobHandler openJobHandler = processorMap.get(handlerName);
        if (ObjectUtils.isEmpty(openJobHandler)) {
            throw new RpcException("JobHandlerName: " + handlerName + ", there is no bound JobHandler.");
        }
        return openJobHandler;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(JobHandler.class);
        if (ObjectUtils.isEmpty(beans)){
            log.warn("No JobHandler instance is defined.");
        }else {
            beans.forEach((k,v)->{
                JobHandler annotation = v.getClass().getAnnotation(JobHandler.class);
                this.processorMap.put(annotation.name(), (OpenJobHandler) v);
            });
        }
    }

    @Override
    public boolean process(Message message) {
        final byte[] body = message.getBody();
        final MessageBody messageBody = SerializationUtils.deserialize(body, MessageBody.class);
        OpenJobHandler openJobHandler = this.getJobHandler(messageBody.getHandlerName());
        openJobHandler.handler(messageBody.getParams());
        return true;
    }
}
