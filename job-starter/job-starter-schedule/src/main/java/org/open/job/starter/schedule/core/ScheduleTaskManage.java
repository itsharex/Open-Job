package org.open.job.starter.schedule.core;

import org.open.job.starter.schedule.domain.ScheduleTask;

import java.util.List;

/**
 * @author lijunping on 2022/1/20
 */
public interface ScheduleTaskManage {

    /**
     * 添加或更新任务
     * @param scheduleTask
     * @return
     */
    Boolean addOrUpdateScheduleTask(ScheduleTask scheduleTask);

    /**
     * 移除任务
     * @param taskId
     * @return
     */
    Boolean removeScheduleTask(Long taskId);

    /**
     * 获取调度任务
     * @return
     */
    List<Long> getScheduleTaskList();
}
