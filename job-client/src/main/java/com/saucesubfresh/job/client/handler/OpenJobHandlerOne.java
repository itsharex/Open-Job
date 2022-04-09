package com.saucesubfresh.job.client.handler;

import lombok.extern.slf4j.Slf4j;
import com.saucesubfresh.job.client.annotation.JobHandler;

/**
 * @author lijunping on 2022/2/25
 */
@Slf4j
@JobHandler(name = "job-one")
public class OpenJobHandlerOne implements OpenJobHandler {

    @Override
    public void handler(String params) {
        log.info("JobHandlerOne 处理任务");
    }
}
