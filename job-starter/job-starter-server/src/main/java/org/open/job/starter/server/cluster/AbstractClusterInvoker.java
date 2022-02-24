package org.open.job.starter.server.cluster;


import lombok.extern.slf4j.Slf4j;
import org.open.job.core.Message;
import org.open.job.core.exception.RpcException;
import org.open.job.core.information.ClientInformation;
import org.open.job.starter.server.ServerConfiguration;
import org.open.job.starter.server.discovery.ServiceDiscovery;
import org.open.job.starter.server.enums.LoadBalanceModelEnum;
import org.open.job.starter.server.loadbalance.LoadBalance;
import org.open.job.starter.server.remoting.RemotingInvoker;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author lijunping on 2022/1/21
 */
@Slf4j
public abstract class AbstractClusterInvoker implements ClusterInvoker{

    private final ServiceDiscovery serviceDiscovery;
    protected final ServerConfiguration configuration;
    protected final List<LoadBalance> loadBalances;
    protected final RemotingInvoker remotingInvoker;

    public AbstractClusterInvoker(ServiceDiscovery serviceDiscovery, ServerConfiguration configuration, List<LoadBalance> loadBalances, RemotingInvoker remotingInvoker){
        this.serviceDiscovery = serviceDiscovery;
        this.configuration = configuration;
        this.loadBalances = loadBalances;
        this.remotingInvoker = remotingInvoker;
    }

    @Override
    public void invoke(Message messages) throws RpcException {
        final List<ClientInformation> clientList = lookup();
        doInvoke(messages, clientList);
    }

    /**
     * 通过服务发现找到所有在线的客户端
     */
    protected List<ClientInformation> lookup() {
        List<ClientInformation> clients = serviceDiscovery.lookup();
        if (CollectionUtils.isEmpty(clients)) {
            throw new RpcException("No healthy clients were found.");
        }
        return clients;
    }

    /**
     * 通过负载均衡策略找出合适的客户端进行调用
     */
    protected ClientInformation select(Message message, List<ClientInformation> clients) throws RpcException{
        LoadBalanceModelEnum loadBalanceModelEnum = LoadBalanceModelEnum.of(configuration.getLoadBalance());
        LoadBalance loadBalance = loadBalances.stream().filter(e -> e.support(loadBalanceModelEnum)).findFirst().orElseThrow(()->new RpcException("No LoadBalance find" + loadBalanceModelEnum.getName()));
        return loadBalance.select(message, clients);
    }

    protected abstract void doInvoke(Message message, List<ClientInformation> clients) throws RpcException;
}