/*
 * Copyright © 2022 organization SauceSubFresh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.saucesubfresh.job.admin.schedule;

import com.saucesubfresh.job.admin.invoke.TaskInvoke;
import com.saucesubfresh.starter.schedule.executor.ScheduleTaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 定时任务远程调用实现
 * @author lijunping on 2021/8/31
 */
@Slf4j
@Component
public class ScheduleJobExecutor implements ScheduleTaskExecutor {

    private final TaskInvoke taskInvoke;
    private final ThreadPoolExecutor executor;

    public ScheduleJobExecutor(TaskInvoke taskInvoke,
                               ThreadPoolExecutor executor) {
        this.taskInvoke = taskInvoke;
        this.executor = executor;
    }

    @Override
    public void execute(List<Long> taskList) {
        executor.execute(()->taskInvoke.invoke(taskList));
    }
}
