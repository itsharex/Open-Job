package org.open.job.starter.server.handler;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.open.job.common.json.JSON;
import org.open.job.core.ClientStatus;
import org.open.job.core.exception.RpcException;
import org.open.job.core.grpc.ClientServiceGrpc;
import org.open.job.core.grpc.proto.ClientHeartBeatRequest;
import org.open.job.core.grpc.proto.ClientRegisterRequest;
import org.open.job.core.grpc.proto.ClientResponse;
import org.open.job.core.information.ClientInformation;
import org.open.job.core.transport.ClientHeartBeatResponseBody;
import org.open.job.core.transport.ClientRegisterResponseBody;
import org.open.job.core.transport.MessageResponseStatus;
import org.open.job.starter.server.discovery.support.GRpcRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author: 李俊平
 * @Date: 2022-01-30 17:22
 */
@Slf4j
@Component
public class GRpcMessageHandler extends ClientServiceGrpc.ClientServiceImplBase{

    @Autowired(required = false)
    private GRpcRegistryService gRpcRegistryService;

    /**
     * Register client
     *
     * @param request          client register request {@link ClientRegisterRequest}
     * @param responseObserver stream responseThreadPoolTaskExecutor
     */
    @Override
    public void register(ClientRegisterRequest request, StreamObserver<ClientResponse> responseObserver) {
        ClientRegisterResponseBody responseBody = new ClientRegisterResponseBody();
        try {
            if (StringUtils.isBlank(request.getAddress()) || (request.getPort() <= 0 || request.getPort() > 65535)) {
                throw new RpcException("The client information verification fails and the registration cannot be completed.");
            }
            log.info("Registering client, IP: {}, Port: {}", request.getAddress(), request.getPort());
            ClientInformation client = ClientInformation.valueOf(request.getAddress(), request.getPort());
            String clientId = client.getClientId();
            responseBody.setClientId(clientId);
            gRpcRegistryService.handingRegister(client);
        } catch (Exception e) {
            responseBody.setStatus(MessageResponseStatus.ERROR);
            log.error("Register client failed.", e);
        }
        String responseBodyJson = JSON.toJSON(responseBody);
        ClientResponse response = ClientResponse.newBuilder().setBody(responseBodyJson).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * Heartbeat check
     *
     * @param request          client heartbeat check request {@link ClientHeartBeatRequest}
     * @param responseObserver stream response
     */
    @Override
    public void heartbeat(ClientHeartBeatRequest request, StreamObserver<ClientResponse> responseObserver) {
        ClientHeartBeatResponseBody responseBody = new ClientHeartBeatResponseBody();
        try {
            if (StringUtils.isEmpty(request.getAddress()) || (request.getPort() <= 0 || request.getPort() > 65535)) {
                throw new RpcException("The client information that sent the heartbeat is incomplete, " +
                        "and the heartbeat check is ignored this time.");
            }
            ClientInformation client = ClientInformation.valueOf(request.getAddress(), request.getPort());
            long currentTime = System.currentTimeMillis();
            client.setLastReportTime(currentTime);
            client.setOnlineTime(currentTime);
            client.setStatus(ClientStatus.ON_LINE);
            gRpcRegistryService.handingHeartBeat(client);
        } catch (Exception e) {
            responseBody.setStatus(MessageResponseStatus.ERROR);
            log.error("Heartbeat check failed.", e);
        }
        String responseBodyJson = JSON.toJSON(responseBody);
        ClientResponse response = ClientResponse.newBuilder().setBody(responseBodyJson).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
