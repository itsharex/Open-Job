package org.open.job.starter.client.process;

import org.open.job.core.Message;

/**
 * @author lijunping on 2022/1/19
 */
public interface CrawlerTaskProcessor {

    void processing(Message message);
}
