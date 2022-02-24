package org.open.job.starter.server.remoting.support;


import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.open.job.common.json.JSON;
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

/**
 * @author lijunping on 2022/2/16
 */
@Slf4j
public class GrpcRemotingInvoker implements RemotingInvoker {

    @Override
    public void invoke(Message message, ClientInformation clientInformation) throws RpcException {
        String clientId = clientInformation.getClientId();
        ManagedChannel channel = ClientChannelManager.establishChannel(clientInformation);
        try {
            MessageServiceGrpc.MessageServiceBlockingStub messageClientStub = MessageServiceGrpc.newBlockingStub(channel);
            MessageRequestBody requestBody = new MessageRequestBody().setClientId(clientId).setMessage(message);
            String requestJsonBody = JSON.toJSON(requestBody);
            MessageResponse response = messageClientStub.messageProcessing(MessageRequest.newBuilder().setBody(requestJsonBody).build());
            MessageResponseBody responseBody = JSON.parse(response.getBody(), MessageResponseBody.class);
            if (!MessageResponseStatus.SUCCESS.equals(responseBody.getStatus())) {
                log.error("To the client: {}, the message is sent abnormally, and the message is recovered.", clientId);
                throw new RpcException("rpc failed");
            }
        } catch (StatusRuntimeException e) {
            Status.Code code = e.getStatus().getCode();
            log.error("To the client: {}, exception when sending a message, Status Code: {}", clientId, code);
            // The server status is UNAVAILABLE
            if (Status.Code.UNAVAILABLE == code) {
                ClientChannelManager.removeChannel(clientId);
                log.error("The client is unavailable, and the cached channel is deleted.");
            }
            throw new RpcException("rpc failed");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RpcException("rpc failed");
        }
    }
}