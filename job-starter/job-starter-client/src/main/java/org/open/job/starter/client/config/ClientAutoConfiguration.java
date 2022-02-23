package org.open.job.starter.client.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import org.I0Itec.zkclient.ZkClient;
import org.open.job.common.constants.CommonConstant;
import org.open.job.starter.client.ClientConfiguration;
import org.open.job.starter.client.process.CrawlerTaskProcessor;
import org.open.job.starter.client.process.DefaultCrawlerTaskProcessor;
import org.open.job.starter.client.registry.support.NacosRegistryService;
import org.open.job.starter.client.registry.support.ZookeeperRegistryService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * NamingService 和 zkClient 客户端和服务端的配置要保持一致
 * @author lijunping on 2022/1/20
 */
@Configuration
@EnableConfigurationProperties(ClientConfiguration.class)
public class ClientAutoConfiguration {

    @Bean
    @ConditionalOnBean(NacosRegistryService.class)
    @ConditionalOnMissingBean
    public NamingService namingService() throws NacosException {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.USERNAME, "nacos");
        properties.put(PropertyKeyConst.PASSWORD, "nacos");
        properties.put(PropertyKeyConst.SERVER_ADDR, String.format(CommonConstant.ADDRESS_PATTERN, "127.0.0.1", 8848));
        return NacosFactory.createNamingService(properties);
    }

    @Bean
    @ConditionalOnBean(ZookeeperRegistryService.class)
    @ConditionalOnMissingBean
    public ZkClient zkClient(){
        System.setProperty("zookeeper.sasl.client", "false");
        return new ZkClient(String.format(CommonConstant.ADDRESS_PATTERN, "localhost", 2181), 5000);
    }

    @Bean
    @ConditionalOnMissingBean
    public CrawlerTaskProcessor crawlerTaskProcessor(){
        return new DefaultCrawlerTaskProcessor();
    }

}
