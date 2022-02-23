package org.open.job.starter.client.registry.support;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import org.open.job.starter.client.ClientConfiguration;
import org.open.job.starter.client.registry.AbstractRegistryService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Register to Server in Nacos mode
 * @author: 李俊平
 * @Date: 2021-10-31 14:38
 */
@Slf4j
public class NacosRegistryService extends AbstractRegistryService implements InitializingBean, DisposableBean, BeanFactoryAware {
    private static final String CLIENT_NAME = "crawler-client";
    private BeanFactory beanFactory;
    private NamingService namingService;

    public NacosRegistryService(ClientConfiguration configuration) {
        super(configuration);
    }
    /**
     * Register client to nacos server
     * <p>
     * If there is an instance of {@link NamingService} in the running project,
     * use it directly, otherwise create a new instance
     *
     * @param serverAddress The server address
     * @param serverPort    The server port
     */
    @Override
    public void doRegister(String serverAddress, int serverPort) {
        try {
            Instance instance = new Instance();
            instance.setIp(this.configuration.getClientAddress());
            instance.setPort(this.configuration.getClientPort());
            // metadata map
            Map<String, String> metadata = new HashMap<>();
            instance.setMetadata(metadata);
            // register to nacos server
            this.namingService.registerInstance(CLIENT_NAME, instance);
            log.info("Current client registered to nacos server successfully.");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void deRegister(String clientAddress, int clientPort) {
        try {
            this.namingService.deregisterInstance(CLIENT_NAME, clientAddress, clientPort);
        } catch (NacosException e) {
            log.error("deRegister instance failed {}", e.getMessage());
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void destroy() throws Exception {
        this.namingService.shutDown();
        log.info("The client is successfully offline from the nacos server.");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            this.namingService = this.beanFactory.getBean(NamingService.class);
        } catch (BeansException e) {
            log.warn("No NamingService instance is provided, a new instance will be automatically created for use");
        }
        super.afterPropertiesSet();
    }
}
