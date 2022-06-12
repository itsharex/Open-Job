package com.saucesubfresh.job.sample.handler.method;

import com.saucesubfresh.job.core.annotation.JobHandlerForMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lijunping on 2022/2/25
 */
@Slf4j
@Component
public class OpenJobHandlerMethodOne{

    @JobHandlerForMethod(name = "job-method-one1")
    public void handlerOne1(String params) {
        log.info("JobHandlerOne 处理任务, 任务参数 {}", params);
    }

    @JobHandlerForMethod(name = "job-method-one2")
    public void handlerOne2(String params) {
        log.info("JobHandlerOne 处理任务, 任务参数 {}", params);
    }
}
