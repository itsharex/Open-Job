package com.saucesubfresh.job.core.config;

import com.saucesubfresh.job.core.collector.DefaultJobHandlerCollector;
import com.saucesubfresh.job.core.collector.JobHandlerCollector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lijunping on 2022/6/13
 */
@Configuration
public class JobHandlerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public JobHandlerCollector jobHandlerCollector(){
        return new DefaultJobHandlerCollector();
    }
}
