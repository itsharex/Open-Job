package org.open.job.starter.client.process;

import org.open.job.core.Message;

/**
 * @author lijunping on 2022/1/19
 */
public interface JobTaskProcessor {

    void processing(Message message);
}
