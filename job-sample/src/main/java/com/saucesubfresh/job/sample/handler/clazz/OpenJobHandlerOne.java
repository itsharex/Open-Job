package com.saucesubfresh.job.sample.handler.clazz;

import com.saucesubfresh.job.core.annotation.JobHandler;
import com.saucesubfresh.job.core.collector.OpenJobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lijunping on 2022/2/25
 */
@Slf4j
@JobHandler(name = "job-one")
@Component
public class OpenJobHandlerOne implements OpenJobHandler {

    @Override
    public void handler(String params) {
        log.info("JobHandlerOne 处理任务");
    }
}
