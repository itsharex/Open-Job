package org.open.job.starter.client.handler;


import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.open.job.common.json.JSON;
import org.open.job.common.serialize.SerializationUtils;
import org.open.job.core.Message;
import org.open.job.core.MessageBody;
import org.open.job.core.PacketType;
import org.open.job.core.exception.RpcException;
import org.open.job.core.grpc.MessageServiceGrpc;
import org.open.job.core.grpc.proto.MessageRequest;
import org.open.job.core.grpc.proto.MessageResponse;
import org.open.job.core.transport.MessageRequestBody;
import org.open.job.core.transport.MessageResponseBody;
import org.open.job.core.transport.MessageResponseStatus;
import org.open.job.starter.client.ClientConfiguration;
import org.open.job.starter.client.process.JobHandler;
import org.open.job.starter.client.process.JobHandlerManager;
import org.open.job.starter.client.registry.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lijunping on 2022/1/24
 */
@Slf4j
@Component
public class GRpcMessageHandler extends MessageServiceGrpc.MessageServiceImplBase{

    private static final String SPLIT_SYMBOL = "::";

    @Autowired
    private JobHandlerManager jobHandlerManager;

    @Autowired
    private ClientConfiguration configuration;

    @Autowired
    private RegistryService registryService;

    @Override
    public void messageProcessing(MessageRequest request, StreamObserver<MessageResponse> responseObserver) {
        MessageResponseBody responseBody = new MessageResponseBody();
        try {
            String requestJsonBody = request.getBody();
            MessageRequestBody requestBody = JSON.parse(requestJsonBody, MessageRequestBody.class);
            Message message = requestBody.getMessage();
            PacketType command = message.getCommand();
            boolean result;
            switch (command){
                case REGISTER:
                    result = registryService.register(configuration.getServerAddress(), configuration.getServerPort());
                    break;
                case DEREGISTER:
                    String clientId = requestBody.getClientId();
                    String[] clientInfo = StringUtils.split(clientId, SPLIT_SYMBOL);
                    result = registryService.deRegister(clientInfo[0], Integer.parseInt(clientInfo[1]));
                    break;
                case MESSAGE:
                    final byte[] body = message.getBody();
                    final MessageBody messageBody = SerializationUtils.deserialize(body, MessageBody.class);
                    JobHandler jobHandler = jobHandlerManager.getJobHandler(messageBody.getHandlerName());
                    jobHandler.handler(messageBody.getParams());
                    result = true;
                    break;
                default:
                    throw new RpcException("UnSupport message packet" + command);
            }
            responseBody.setStatus(result ? MessageResponseStatus.SUCCESS : MessageResponseStatus.ERROR);
        } catch (Exception e) {
            responseBody.setStatus(MessageResponseStatus.ERROR);
            log.error(e.getMessage(), e);
        } finally {
            String responseJsonBody = JSON.toJSON(responseBody);
            MessageResponse response = MessageResponse.newBuilder().setBody(responseJsonBody).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
