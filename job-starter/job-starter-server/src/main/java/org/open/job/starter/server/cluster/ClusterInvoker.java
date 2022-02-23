package org.open.job.starter.server.cluster;


import org.open.job.core.Message;
import org.open.job.core.exception.RpcException;
import org.open.job.starter.server.enums.ClusterInvokeModelEnum;

/**
 * 对外提供的接口，提供给用户使用
 * @author lijunping on 2022/1/21
 */
public interface ClusterInvoker {

    /**
     * 给客户端发送消息
     * @param message
     * @throws RpcException
     */
    void invoke(Message message) throws RpcException;

    /**
     * 是否支持该调用模式
     * @param clusterModel
     * @return
     */
    boolean support(ClusterInvokeModelEnum clusterModel);
}
