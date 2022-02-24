package org.open.job.starter.server.discovery.support;

import lombok.extern.slf4j.Slf4j;
import org.open.job.core.information.ClientInformation;
import org.open.job.starter.server.ServerConfiguration;
import org.open.job.starter.server.discovery.AbstractServiceDiscovery;
import org.open.job.starter.server.remoting.RemotingInvoker;
import org.open.job.starter.server.store.InstanceStore;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * Start some services required by the server
 * @author: 李俊平
 * @Date: 2021-10-31 14:53
 */
@Slf4j
public class GRpcRegistryService extends AbstractServiceDiscovery implements InitializingBean, DisposableBean {
    private final ServerConfiguration configuration;
    private final ScheduledExecutorService expiredScheduledExecutor;

    /**
     * There is a list of online clients
     */
    private static final ConcurrentMap<String, ClientInformation> CLIENTS = new ConcurrentHashMap<>();

    public GRpcRegistryService(ServerConfiguration configuration, RemotingInvoker remotingInvoker, InstanceStore instanceStore) {
        super(remotingInvoker, instanceStore);
        this.configuration = configuration;
        this.expiredScheduledExecutor = Executors.newScheduledThreadPool(configuration.getExpiredPoolSize());
    }

    /**
     * Start eliminate expired client
     * <p>
     * If the client's last heartbeat time is greater than the timeout threshold,
     * the update status is performed
     */
    private void startEliminateExpiredClient() {
        this.expiredScheduledExecutor.scheduleAtFixedRate(this::handingExpired, 5, configuration.getCheckClientExpiredIntervalSeconds(), TimeUnit.SECONDS);
    }

    /**
     * List of registered clients
     *
     * @param information waiting to be registered
     */
    public void handingRegister(ClientInformation information) {
        CLIENTS.put(information.getClientId(), information);
        updateCache(new ArrayList<>(CLIENTS.values()));
    }

    /**
     * Update the last heartbeat time of the client
     * <p>
     * When receiving the heartbeat, if the client is not registered, perform registration
     *
     * @param client waiting to update their heartbeat time
     */
    public void handingHeartBeat(ClientInformation client) {
        log.info("Receiving client: {}, heartbeat sent.", client.getClientId());
        ClientInformation cacheClient = CLIENTS.get(client.getClientId());
        if (Objects.isNull(cacheClient)) {
            this.handingRegister(client);
        } else {
            cacheClient.setLastReportTime(client.getLastReportTime());
        }
    }

    /**
     * Dealing with client expiration
     */
    private void handingExpired() {
        if (CollectionUtils.isEmpty(CLIENTS)) {
            return;
        }
        long currentTime = System.currentTimeMillis();
        int clientSize = CLIENTS.size();
        CLIENTS.values().forEach(client -> {
            String clientId = client.getClientId();
            long intervalSeconds = (currentTime - client.getLastReportTime()) / 1000;
            if (intervalSeconds > configuration.getExpiredExcludeThresholdSeconds()) {
                CLIENTS.remove(clientId);
                log.info("Job Client：{}，status updated to offline.", clientId);
            }
        });
        // 如果清除了不健康的客户端，则更新缓存
        if (clientSize != CLIENTS.size()){
            updateCache(new ArrayList<>(CLIENTS.values()));
        }
    }

    @Override
    protected List<ClientInformation> doLookup() {
        return new ArrayList<>(CLIENTS.values());
    }

    @Override
    public void destroy() throws Exception {
        this.expiredScheduledExecutor.shutdown();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.startEliminateExpiredClient();
        log.info("Job ClientExpiredExecutor successfully started.");
    }
}
