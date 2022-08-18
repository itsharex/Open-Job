package com.saucesubfresh.job.admin.schedule;

import com.saucesubfresh.job.admin.invoke.TaskInvoke;
import com.saucesubfresh.starter.schedule.executor.ScheduleTaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时任务远程调用实现
 * @author lijunping on 2021/8/31
 */
@Slf4j
@Component
public class ScheduleJobExecutor implements ScheduleTaskExecutor {

    private final TaskInvoke taskInvoke;

    public ScheduleJobExecutor(TaskInvoke taskInvoke) {
        this.taskInvoke = taskInvoke;
    }

    @Override
    public void execute(List<Long> taskList) {
        taskInvoke.invoke(taskList);
    }
}
