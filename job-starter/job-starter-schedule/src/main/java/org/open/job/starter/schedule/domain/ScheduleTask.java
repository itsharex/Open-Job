package org.open.job.starter.schedule.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lijunping on 2022/1/20
 */
@Data
public class ScheduleTask implements Serializable {

    /**
     * 任务 id
     */
    private Long taskId;

    /**
     * cron 表达式
     */
    private String cronExpression;
}
