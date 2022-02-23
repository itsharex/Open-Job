package org.open.job.starter.client.process;

import lombok.extern.slf4j.Slf4j;
import org.open.job.core.Message;

/**
 * @author lijunping on 2022/1/19
 */
@Slf4j
public class DefaultCrawlerTaskProcessor implements CrawlerTaskProcessor {

    @Override
    public void processing(Message spiderRequest) {
        log.info("[开始处理任务]");
    }
}
