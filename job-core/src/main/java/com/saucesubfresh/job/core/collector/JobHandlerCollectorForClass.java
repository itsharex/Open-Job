package com.saucesubfresh.job.core.collector;

import com.saucesubfresh.job.core.annotation.JobHandlerForClazz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(JobHandlerForClazz.class);
        if (ObjectUtils.isEmpty(beans)){
            log.warn("No JobHandler instance is defined.");
        }else {
            beans.forEach((k,v)->{
                JobHandlerForClazz annotation = v.getClass().getAnnotation(JobHandlerForClazz.class);
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
