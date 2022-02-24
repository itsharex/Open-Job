package org.open.job.starter.server.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import org.I0Itec.zkclient.ZkClient;
import org.open.job.common.constants.CommonConstant;
import org.open.job.starter.server.ServerConfiguration;
import org.open.job.starter.server.discovery.support.NacosRegistryService;
import org.open.job.starter.server.discovery.support.ZookeeperRegistryService;
import org.open.job.starter.server.remoting.RemotingInvoker;
import org.open.job.starter.server.remoting.support.GrpcRemotingInvoker;
import org.open.job.starter.server.store.InstanceStore;
import org.open.job.starter.server.store.support.MemoryInstanceStore;
import org.open.job.starter.server.store.support.RedissonInstanceStore;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
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
@EnableConfigurationProperties(ServerConfiguration.class)
public class ServerAutoConfiguration {

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
    public RemotingInvoker remotingInvoker(){
        return new GrpcRemotingInvoker();
    }

    @Bean
    @ConditionalOnExpression("'memory'.equals('${org.open.job.server.store:memory}')")
    public InstanceStore memoryStore(){
        return new MemoryInstanceStore();
    }

    @Bean
    @ConditionalOnExpression("'redisson'.equals('${org.open.job.server.store:redisson}')")
    public InstanceStore redissonStore(RedissonClient client){
        return new RedissonInstanceStore(client);
    }
}
