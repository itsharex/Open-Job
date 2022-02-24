package org.open.job.starter.client.registry;

import org.apache.commons.lang3.StringUtils;
import org.open.job.core.exception.RpcException;
import org.open.job.starter.client.ClientConfiguration;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author lijunping on 2022/1/24
 */
public abstract class AbstractRegistryService implements RegistryService, InitializingBean {

    protected final ClientConfiguration configuration;

    public AbstractRegistryService(ClientConfiguration configuration){
        if (configuration.getServerPort() <= 0 || configuration.getServerPort() > 65535) {
            throw new RpcException("The Target Server port must be greater than 0 and less than 65535.");
        }
        if (StringUtils.isBlank(configuration.getServerAddress())) {
            throw new RpcException("The Target server address cannot be empty.");
        }
        this.configuration = configuration;
    }
    @Override
    public void register(String serverAddress, int serverPort) {
        doRegister(serverAddress, serverPort);
    }

    /**
     * Register current client to server
     * <p>
     * Use child threads for registration logic
     *
     */
    private void registerToServer() {
        Thread thread = new Thread(() -> this.register(configuration.getServerAddress(), configuration.getServerPort()));
        thread.start();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        registerToServer();
    }

    protected abstract void doRegister(String serverAddress, int serverPort);
}