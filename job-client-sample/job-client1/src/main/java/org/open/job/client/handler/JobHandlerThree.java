package org.open.job.client.handler;

import lombok.extern.slf4j.Slf4j;
import org.open.job.client.constants.JobHandlerNameConstants;
import org.springframework.stereotype.Component;

/**
 * @author lijunping on 2022/2/25
 */
@Slf4j
@Component
public class JobHandlerThree implements JobHandler {


    @Override
    public String bindingJobHandlerName() {
        return JobHandlerNameConstants.JOB_THREE;
    }

    @Override
    public void handler(String params) {
        log.info("JobHandlerThree 处理任务");
    }
}
