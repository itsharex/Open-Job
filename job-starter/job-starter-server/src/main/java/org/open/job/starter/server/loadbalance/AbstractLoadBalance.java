package org.open.job.starter.server.loadbalance;

import org.open.job.core.Message;
import org.open.job.core.exception.RpcException;
import org.open.job.core.information.ClientInformation;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author: 李俊平
 * @Date: 2022-02-01 10:07
 */
public abstract class AbstractLoadBalance implements LoadBalance{

    @Override
    public ClientInformation select(Message message, List<ClientInformation> invokers) throws RpcException {
        if (CollectionUtils.isEmpty(invokers)) {
            return null;
        }
        if (invokers.size() == 1) {
            return invokers.get(0);
        }
        return doSelect(message, invokers);
    }

    protected abstract ClientInformation doSelect(Message message, List<ClientInformation> invokers);
}
