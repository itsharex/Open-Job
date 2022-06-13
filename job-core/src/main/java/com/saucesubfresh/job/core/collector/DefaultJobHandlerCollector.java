package com.saucesubfresh.job.core.collector;

import com.saucesubfresh.job.core.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author: 李俊平
 * @Date: 2022-06-12 17:30
 */
@Slf4j
public class DefaultJobHandlerCollector extends AbstractJobHandlerCollector implements ApplicationContextAware, SmartInitializingSingleton {

    private ApplicationContext applicationContext;

    @Override
    public void collectJobHandler() {
        this.collectClazzJobHandler();
        this.collectMethodJobHandler();
    }

    @Override
    public void afterSingletonsInstantiated() {
        this.collectJobHandler();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private void collectClazzJobHandler(){
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(JobHandler.class);
        if (ObjectUtils.isEmpty(beans)){
            log.warn("No JobHandler instance is defined.");
        }else {
            beans.forEach((k,v)->{
                JobHandler annotation = v.getClass().getAnnotation(JobHandler.class);
                handlerMap.put(annotation.name(), (OpenJobHandler) v);
            });
        }
    }

    private void collectMethodJobHandler(){
        String[] beanDefinitionNames = applicationContext.getBeanNamesForType(Object.class, false, true);
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);
            // referred to ：org.springframework.context.event.EventListenerMethodProcessor.processBean
            Map<Method, JobHandler> annotatedMethods = null;
            try {
                annotatedMethods = MethodIntrospector.selectMethods(bean.getClass(),
                        new MethodIntrospector.MetadataLookup<JobHandler>() {
                            @Override
                            public JobHandler inspect(Method method) {
                                return AnnotatedElementUtils.findMergedAnnotation(method, JobHandler.class);
                            }
                        });
            } catch (Throwable ex) {
                log.error("jobHandler resolve error for bean[" + beanDefinitionName + "].", ex);
            }
            if (annotatedMethods == null || annotatedMethods.isEmpty()) {
                continue;
            }

            for (Map.Entry<Method, JobHandler> methodJobHandlerEntry : annotatedMethods.entrySet()) {
                Method executeMethod = methodJobHandlerEntry.getKey();
                JobHandler annotation = methodJobHandlerEntry.getValue();
                buildJobHandler(annotation, bean, executeMethod);
            }
        }
    }

    private void buildJobHandler(JobHandler jobHandler, Object bean, Method executeMethod){
        if (jobHandler == null) {
            return;
        }

        String name = jobHandler.value();
        Class<?> clazz = bean.getClass();
        String methodName = executeMethod.getName();
        if (StringUtils.isBlank(name)) {
            throw new RuntimeException("open-job method-jobHandler name invalid, for[" + clazz + "#" + methodName + "] .");
        }
        if (handlerMap.get(name) != null) {
            throw new RuntimeException("open-job jobHandler[" + name + "] naming conflicts.");
        }
        executeMethod.setAccessible(true);
        handlerMap.put(name, new MethodJobHandler(bean, executeMethod));
    }
}
