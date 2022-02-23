package org.open.job.admin.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author lijunping on 2021/6/22
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Import(Jackson2ObjectMapperCustomizerConfiguration.class)
public class SpringWebMvcConfig {

    /**
     * Spring Web Mvc 的全局异常，全局返回结果处理
     */
    @Configuration(proxyBeanMethods = false)
    static class WebMvcGlobalHandlerConfiguration {

        //全局异常
        @Bean
        @ConditionalOnMissingBean(GlobalExceptionHandler.class)
        public GlobalExceptionHandler globalExceptionHandler() {
            return new GlobalExceptionHandler();
        }
    }
}
