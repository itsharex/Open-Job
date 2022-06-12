package com.saucesubfresh.job.sample.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author: 李俊平
 * @Date: 2022-03-19 10:24
 */
@Configuration
public class JobConfiguration {

    @Bean
    public NamingService namingService() throws NacosException {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.USERNAME, "nacos");
        properties.put(PropertyKeyConst.PASSWORD, "nacos");
        properties.put(PropertyKeyConst.SERVER_ADDR, "127.0.0.1:8848");
        return NacosFactory.createNamingService(properties);
    }
}
