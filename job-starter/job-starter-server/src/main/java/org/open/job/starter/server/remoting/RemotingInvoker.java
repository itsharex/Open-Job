package org.open.job.starter.server.remoting;

import org.open.job.core.Message;
import org.open.job.core.exception.RpcException;
import org.open.job.core.information.ClientInformation;

/**
 * @author lijunping on 2022/2/16
 */
public interface RemotingInvoker {

    /**
     * 该接口是给系统调用的
     * @param message
     * @param clientInformation
     * @throws RpcException
     */
    void invoke(Message message, ClientInformation clientInformation) throws RpcException;
}
