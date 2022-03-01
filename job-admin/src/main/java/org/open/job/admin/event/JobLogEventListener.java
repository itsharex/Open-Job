package org.open.job.admin.event;

import org.open.job.admin.dto.create.OpenJobLogCreateDTO;
import org.open.job.admin.service.OpenJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author lijunping on 2022/2/28
 */
@Component
public class JobLogEventListener implements ApplicationListener<JobLogEvent> {

    @Autowired
    private OpenJobLogService openJobLogService;

    @Override
    public void onApplicationEvent(JobLogEvent event) {
        final OpenJobLogCreateDTO jobLogCreateDTO = event.getJobLogCreateDTO();
        openJobLogService.save(jobLogCreateDTO);
    }
}
