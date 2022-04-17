package com.saucesubfresh.job.admin.event;

import com.saucesubfresh.job.admin.dto.create.OpenJobLogCreateDTO;
import com.saucesubfresh.job.admin.service.OpenJobLogService;
import com.saucesubfresh.starter.alarm.provider.dingtalk.DingtalkAlarmExecutor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author lijunping on 2022/2/28
 */
@Component
public class JobLogEventListener implements ApplicationListener<JobLogEvent> {

    private final OpenJobLogService openJobLogService;
    private final DingtalkAlarmExecutor alarmExecutor;

    public JobLogEventListener(OpenJobLogService openJobLogService, DingtalkAlarmExecutor alarmExecutor) {
        this.openJobLogService = openJobLogService;
        this.alarmExecutor = alarmExecutor;
    }


    @Override
    public void onApplicationEvent(JobLogEvent event) {
        final OpenJobLogCreateDTO jobLogCreateDTO = event.getJobLogCreateDTO();
        openJobLogService.save(jobLogCreateDTO);
        alarmExecutor.doAlarm(null, callbackMessage -> {});
    }
}
