package org.open.job.starter.server.cluster.support;


import lombok.extern.slf4j.Slf4j;
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
 * 故障转移模式
 * @author: 李俊平
 * @Date: 2022-02-02 08:40
 */
@Slf4j
@Component
public class FailoverClusterInvoker extends AbstractClusterInvoker {

    public FailoverClusterInvoker(ServiceDiscovery serviceDiscovery, ServerConfiguration configuration, List<LoadBalance> loadBalances, RemotingInvoker remotingInvoker) {
        super(serviceDiscovery, configuration, loadBalances, remotingInvoker);
    }

    @Override
    protected void doInvoke(Message message, List<ClientInformation> clients) throws RpcException {
        ClientInformation clientInformation = select(message, clients);
        try {
            remotingInvoker.invoke(message, clientInformation);
        } catch (RpcException e){
            clients.remove(clientInformation);
            invoke(message, clients);
        }
    }

    private void invoke(Message message, List<ClientInformation> clients) throws RpcException{
        RpcException ex = null;
        for (ClientInformation clientInformation : clients) {
            try {
                remotingInvoker.invoke(message, clientInformation);
                if (ex != null){
                    log.warn("Rpc invoke failed {}", ex.getMessage());
                }
                return;
            }catch (RpcException e){
                ex = e;
            } catch (Throwable e) {
                ex = new RpcException(e.getMessage());
            }
        }
    }

    @Override
    public boolean support(ClusterInvokeModelEnum clusterModel) {
        return clusterModel == ClusterInvokeModelEnum.FAILOVER;
    }
}
