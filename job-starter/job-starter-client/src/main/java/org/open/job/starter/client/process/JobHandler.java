package org.open.job.starter.client.process;

import org.open.job.core.Message;

/**
 * @author lijunping on 2022/1/19
 */
public interface JobHandler {
    /**
     * binding jobHandler name
     * <p>
     * Only execute the {@link #handler} method when the JobHandler name matches the return value
     *
     * @return The {@link JobHandler} binding JobHandler name
     */
    String bindingJobHandlerName();

    /**
     * Execute jobHandler
     *
     * @param message          The {@link Message} instance
     */
    void handler(Message message);
}
