package com.saucesubfresh.job.core.collector;

import com.saucesubfresh.job.core.annotation.JobHandlerForClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * @author lijunping on 2022/2/25
 */
@Slf4j
//@Component
public class JobHandlerCollectorForClass extends AbstractJobHandlerCollector implements InitializingBean, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void collectJobHandler() {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(JobHandlerForClass.class);
        if (ObjectUtils.isEmpty(beans)){
            log.warn("No JobHandlerForClass instance is defined.");
        }else {
            beans.forEach((k,v)->{
                JobHandlerForClass annotation = v.getClass().getAnnotation(JobHandlerForClass.class);
                handlerMap.put(annotation.name(), (OpenJobHandler) v);
            });
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.collectJobHandler();
    }
}
