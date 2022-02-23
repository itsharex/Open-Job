package org.open.job.starter.server.cluster.support;


import org.open.job.core.Message;
import org.open.job.core.exception.RpcException;
import org.open.job.core.information.ClientInformation;
import org.open.job.starter.server.ServerConfiguration;
import org.open.job.starter.server.cluster.AbstractClusterInvoker;
import org.open.job.starter.server.discovery.ServiceDiscovery;
import org.open.job.starter.server.enums.ClusterInvokeModelEnum;
import org.open.job.starter.server.loadbalance.LoadBalance;
import org.open.job.starter.server.remoting.RemotingInvoker;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 广播调用模式
 * @author lijunping on 2022/1/20
 */
@Component
public class BroadcastClusterInvoker extends AbstractClusterInvoker {

    public BroadcastClusterInvoker(ServiceDiscovery serviceDiscovery, ServerConfiguration configuration, List<LoadBalance> loadBalances, RemotingInvoker remotingInvoker) {
        super(serviceDiscovery, configuration, loadBalances, remotingInvoker);
    }

    @Override
    protected void doInvoke(Message message, List<ClientInformation> clients) throws RpcException {
        clients.forEach(clientInformation -> remotingInvoker.invoke(message, clientInformation));
    }

    @Override
    public boolean support(ClusterInvokeModelEnum clusterModel) {
        return clusterModel == ClusterInvokeModelEnum.BROADCAST;
    }
}
