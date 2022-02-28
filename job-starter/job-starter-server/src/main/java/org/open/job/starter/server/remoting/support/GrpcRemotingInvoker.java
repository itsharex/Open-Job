package org.open.job.starter.server.remoting.support;

import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.open.job.common.json.JSON;
import org.open.job.common.random.SnowflakeUtils;
import org.open.job.core.Message;
import org.open.job.core.exception.RpcException;
import org.open.job.core.grpc.MessageServiceGrpc;
import org.open.job.core.grpc.proto.MessageRequest;
import org.open.job.core.grpc.proto.MessageResponse;
import org.open.job.core.information.ClientInformation;
import org.open.job.core.transport.MessageRequestBody;
import org.open.job.core.transport.MessageResponseBody;
import org.open.job.core.transport.MessageResponseStatus;
import org.open.job.starter.server.ClientChannelManager;
import org.open.job.starter.server.remoting.RemotingInvoker;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author lijunping on 2022/2/16
 */
@Slf4j
public class GrpcRemotingInvoker implements RemotingInvoker {

    private static final SnowflakeUtils SNOWFLAKE_UTILS =
            new SnowflakeUtils(
                    ThreadLocalRandom.current().nextInt(1, 30),
                    ThreadLocalRandom.current().nextInt(1, 30));

    @Override
    public void invoke(Message message, ClientInformation clientInformation) throws RpcException {
        String clientId = clientInformation.getClientId();
        ManagedChannel channel = ClientChannelManager.establishChannel(clientInformation);
        try {
            MessageServiceGrpc.MessageServiceBlockingStub messageClientStub = MessageServiceGrpc.newBlockingStub(channel);
            final long random = SNOWFLAKE_UTILS.nextId();
            MessageRequestBody requestBody = new MessageRequestBody().setClientId(clientId).setMessage(message).setRequestId(String.valueOf(random));
            String requestJsonBody = JSON.toJSON(requestBody);
            MessageResponse response = messageClientStub.messageProcessing(MessageRequest.newBuilder().setBody(requestJsonBody).build());
            MessageResponseBody responseBody = JSON.parse(response.getBody(), MessageResponseBody.class);
            if (!MessageResponseStatus.SUCCESS.equals(responseBody.getStatus())) {
                log.error("To the client: {}, the message is sent abnormally, and the message is recovered.", clientId);
                throw new RpcException(String.format("To the client: %s, the message is sent abnormally, and the message is recovered.", clientId));
            }
        } catch (StatusRuntimeException e) {
            Status.Code code = e.getStatus().getCode();
            log.error("To the client: {}, exception when sending a message, Status Code: {}", clientId, code);
            // The server status is UNAVAILABLE
            if (Status.Code.UNAVAILABLE == code) {
                ClientChannelManager.removeChannel(clientId);
                log.error("The client is unavailable, and the cached channel is deleted.");
            }
            throw new RpcException(String.format("To the client: %s, exception when sending a message, Status Code: %s", clientId, code));
        } catch (Exception e) {
            if (e instanceof RpcException){
                return;
            }
            log.error(e.getMessage(), e);
            throw new RpcException(e.getMessage());
        }
    }
}
