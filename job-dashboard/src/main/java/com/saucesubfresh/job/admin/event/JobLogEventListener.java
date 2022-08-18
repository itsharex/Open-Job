package com.saucesubfresh.job.admin.event;

import com.saucesubfresh.job.admin.component.alarm.AlarmService;
import com.saucesubfresh.job.api.dto.create.OpenJobLogCreateDTO;
import com.saucesubfresh.job.admin.service.OpenJobLogService;
import com.saucesubfresh.job.common.enums.CommonStatusEnum;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author lijunping on 2022/2/28
 */
@Component
public class JobLogEventListener implements ApplicationListener<JobLogEvent> {

    private final OpenJobLogService openJobLogService;
    private final AlarmService alarmService;

    public JobLogEventListener(OpenJobLogService openJobLogService, AlarmService alarmService) {
        this.openJobLogService = openJobLogService;
        this.alarmService = alarmService;
    }


    @Override
    public void onApplicationEvent(JobLogEvent event) {
        final OpenJobLogCreateDTO jobLogCreateDTO = event.getJobLogCreateDTO();
        openJobLogService.save(jobLogCreateDTO);
        if (!Objects.equals(jobLogCreateDTO.getStatus(), CommonStatusEnum.NO.getValue())){
            return;
        }

        alarmService.sendAlarm(jobLogCreateDTO);
    }
}
