package com.saucesubfresh.job.admin.component.schedule;

import com.saucesubfresh.job.admin.common.enums.SystemScheduleEnum;
import com.saucesubfresh.starter.schedule.core.ScheduleTaskManage;
import com.saucesubfresh.starter.schedule.domain.ScheduleTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author lijunping on 2022/4/22
 */
@Component
public class SystemSchedule {

    @Value("${report-cron-express}")
    private String reportCronExpress;

    @Value("${clear-cron-express}")
    private String clearCronExpress;

    @Value("${alarm-cron-express}")
    private String alarmCronExpress;

    private final ScheduleTaskManage scheduleTaskManage;

    public SystemSchedule(ScheduleTaskManage scheduleTaskManage) {
        this.scheduleTaskManage = scheduleTaskManage;
    }

    /**
     * 定时插入报表任务
     */
    @PostConstruct
    public void addReportTask(){
        ScheduleTask scheduleTask = new ScheduleTask();
        scheduleTask.setTaskId(SystemScheduleEnum.REPORT.getValue());
        scheduleTask.setCronExpression(reportCronExpress);
        scheduleTaskManage.addScheduleTask(scheduleTask);
    }

    /**
     * 定时清除任务日志任务
     */
    @PostConstruct
    public void clearTaskLogTask(){
        ScheduleTask scheduleTask = new ScheduleTask();
        scheduleTask.setTaskId(SystemScheduleEnum.CLEAR_LOG.getValue());
        scheduleTask.setCronExpression(clearCronExpress);
        scheduleTaskManage.addScheduleTask(scheduleTask);
    }

    /**
     * 定时发送钉钉消息任务
     */
    @PostConstruct
    public void sendPaddingAlarmTask(){
        ScheduleTask scheduleTask = new ScheduleTask();
        scheduleTask.setTaskId(SystemScheduleEnum.ALARM_MESSAGE.getValue());
        scheduleTask.setCronExpression(alarmCronExpress);
        scheduleTaskManage.addScheduleTask(scheduleTask);
    }
}
