package org.open.job.client.handler;

import lombok.extern.slf4j.Slf4j;
import org.open.job.client.annotation.JobHandler;

/**
 * @author lijunping on 2022/2/25
 */
@Slf4j
@JobHandler(name = "openJobHandlerThree")
public class OpenJobHandlerThree implements OpenJobHandler {

    @Override
    public void handler(String params) {
        log.info("JobHandlerThree 处理任务");
    }
}
