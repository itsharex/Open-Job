package com.saucesubfresh.job.sample.handler.method;

import com.saucesubfresh.job.core.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lijunping on 2022/2/25
 */
@Slf4j
@Component
public class OpenJobHandlerMethodTwo {

    @JobHandler(name = "job-method-two1")
    public void handlerTwo1(String params) {
        log.info("JobHandlerOne 处理任务, 任务参数 {}", params);
    }

    @JobHandler(name = "job-method-two2")
    public void handlerTwo2(String params) {
        log.info("JobHandlerOne 处理任务, 任务参数 {}", params);
    }
}
