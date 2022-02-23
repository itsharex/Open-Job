package org.open.job.starter.client.registry.support;

import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.open.job.common.json.JSON;
import org.open.job.core.exception.RpcException;
import org.open.job.core.grpc.ClientServiceGrpc;
import org.open.job.core.grpc.proto.ClientHeartBeatRequest;
import org.open.job.core.grpc.proto.ClientRegisterRequest;
import org.open.job.core.grpc.proto.ClientResponse;
import org.open.job.core.information.ServerInformation;
import org.open.job.core.thread.CrawlerThreadFactory;
import org.open.job.core.transport.ClientRegisterResponseBody;
import org.open.job.core.transport.MessageResponseStatus;
import org.open.job.starter.client.ClientConfiguration;
import org.open.job.starter.client.ServerChannelManager;
import org.open.job.starter.client.registry.AbstractRegistryService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Register to Server in Grpc mode
 * @author: 李俊平
 * @Date: 2021-10-31 14:13
 */
@Slf4j
public class GRpcRegistryService extends AbstractRegistryService {
    private static final String THREAD_NAME_PREFIX = "heartbeat";
    private ScheduledExecutorService heartBeatExecutorService;

    public GRpcRegistryService(ClientConfiguration configuration) {
        super(configuration);
        this.heartBeatExecutorService = Executors.newScheduledThreadPool(5, new CrawlerThreadFactory(THREAD_NAME_PREFIX));
    }

    /**
     * Register client to server
     * <p>
     * If the connection fails, perform a retry,
     * and when the number of retries reaches the upper limit retry register times,
     * terminate the registration
     */
    @Override
    public void doRegister(String serverAddress, int serverPort) {
        boolean unregister = false;
        int maxTimes = configuration.getRetryRegisterTimes();
        int currentTimes = 0;
        while (!unregister) {
            try {
                ServerInformation serverInformation = ServerInformation.valueOf(serverAddress, serverPort);
                ManagedChannel channel = ServerChannelManager.establishChannel(serverInformation);
                ClientServiceGrpc.ClientServiceFutureStub stub = ClientServiceGrpc.newFutureStub(channel);
                ClientRegisterRequest request = ClientRegisterRequest.newBuilder()
                        .setAddress(configuration.getClientAddress())
                        .setPort(configuration.getClientPort())
                        .build();
                ListenableFuture<ClientResponse> listenableFuture = stub.register(request);
                String responseJsonBody = listenableFuture.get().getBody();
                ClientRegisterResponseBody responseBody = JSON.parse(responseJsonBody, ClientRegisterResponseBody.class);
                if (MessageResponseStatus.SUCCESS.equals(responseBody.getStatus())) {
                    // Start a heartbeat after successful registration
                    this.heartBeat(serverInformation);
                    log.info("Registered to Server successfully, ClientId: {}", responseBody.getClientId());
                    unregister = true;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            if (!unregister) {
                currentTimes++;
                if (currentTimes > maxTimes) {
                    throw new RpcException("The number of registration retries reaches the upper limit, " +
                            "the maximum number of times：" + configuration.getRetryRegisterTimes());
                }
                try {
                    Thread.sleep(configuration.getRetryRegisterIntervalMilliSeconds());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void deRegister(String clientAddress, int clientPort) {
        heartBeatExecutorService.shutdown();
    }

    /**
     * Send heart beat to server
     */
    private void heartBeat(ServerInformation serverInformation) {
        if (heartBeatExecutorService.isShutdown()){
            heartBeatExecutorService = Executors.newScheduledThreadPool(5, new CrawlerThreadFactory(THREAD_NAME_PREFIX));
        }
        heartBeatExecutorService.scheduleAtFixedRate(() -> {
            try {
                log.info("{}:{}- begin send heartBeat message", configuration.getClientAddress(), configuration.getClientPort());
                ManagedChannel channel = ServerChannelManager.establishChannel(serverInformation);
                ClientServiceGrpc.ClientServiceBlockingStub stub = ClientServiceGrpc.newBlockingStub(channel);
                ClientHeartBeatRequest request = ClientHeartBeatRequest.newBuilder()
                        .setAddress(configuration.getClientAddress())
                        .setPort(configuration.getClientPort())
                        .build();
                stub.heartbeat(request);
            } catch (StatusRuntimeException e) {
                Status.Code code = e.getStatus().getCode();
                log.error("Send a heartbeat check exception to Server: {}, Status Code: {}", serverInformation, code);
                // The server status is UNAVAILABLE
                if (Status.Code.UNAVAILABLE == code) {
                    ServerChannelManager.removeChannel(serverInformation.getServerId());
                    log.error("The service is unavailable, and the cached channel is deleted.");
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }, 5, configuration.getHeartBeatIntervalSeconds(), TimeUnit.SECONDS);
    }
}
