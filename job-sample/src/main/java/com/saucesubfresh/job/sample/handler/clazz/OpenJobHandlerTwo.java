package com.saucesubfresh.job.sample.handler.clazz;

import com.saucesubfresh.job.core.annotation.JobHandlerForClass;
import com.saucesubfresh.job.core.collector.OpenJobHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lijunping on 2022/2/25
 */
@Slf4j
@JobHandlerForClass(name = "job-two")
public class OpenJobHandlerTwo implements OpenJobHandler {

    @Override
    public void handler(String params) {
        log.info("JobHandlerTwo 处理任务");
    }
}
