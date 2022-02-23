package org.open.job.starter.server.cluster;

import org.open.job.core.Message;
import org.open.job.core.exception.RpcException;
import org.open.job.starter.server.ServerConfiguration;
import org.open.job.starter.server.enums.ClusterInvokeModelEnum;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: 李俊平
 * @Date: 2022-02-01 22:25
 */
@Component
public class ClusterInvokerFactory {

    private final ServerConfiguration configuration;
    private final List<ClusterInvoker> clusterInvokers;

    public ClusterInvokerFactory(ServerConfiguration configuration, List<ClusterInvoker> clusterInvokers) {
        this.configuration = configuration;
        this.clusterInvokers = clusterInvokers;
    }

    /**
     * Rpc 调用
     * @param message
     * @throws RpcException
     */
    public void invoke(Message message) throws RpcException {
        ClusterInvokeModelEnum clusterInvokeModelEnum = ClusterInvokeModelEnum.of(configuration.getClusterModel());
        ClusterInvoker clusterInvoker = clusterInvokers.stream()
                .filter(client -> client.support(clusterInvokeModelEnum))
                .findFirst()
                .orElseThrow(()-> new RpcException("No ClusterInvoker find" + clusterInvokeModelEnum.getName()));
        clusterInvoker.invoke(message);
    }
}
