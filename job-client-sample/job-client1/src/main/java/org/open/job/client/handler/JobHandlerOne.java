package org.open.job.client.handler;

import lombok.extern.slf4j.Slf4j;
import org.open.job.client.constants.JobHandlerNameConstants;
import org.springframework.stereotype.Component;

/**
 * @author lijunping on 2022/2/25
 */
@Slf4j
@Component
public class JobHandlerOne implements JobHandler {

    @Override
    public String bindingJobHandlerName() {
        return JobHandlerNameConstants.JOB_ONE;
    }

    @Override
    public void handler(String params) {
        log.info("JobHandlerOne 处理任务");
    }
}
