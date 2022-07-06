package com.saucesubfresh.job.admin.component.schedule;

import com.saucesubfresh.job.admin.entity.OpenJobDO;
import com.saucesubfresh.job.admin.mapper.OpenJobMapper;
import com.saucesubfresh.starter.schedule.domain.ScheduleTask;
import com.saucesubfresh.starter.schedule.service.ScheduleTaskLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李俊平
 * @Date: 2022-07-07 07:44
 */
@Component
public class OpenJobScheduleTaskLoader implements ScheduleTaskLoader {

    private final OpenJobMapper openJobMapper;

    public OpenJobScheduleTaskLoader(OpenJobMapper openJobMapper) {
        this.openJobMapper = openJobMapper;
    }

    @Override
    public List<ScheduleTask> loadScheduleTask() {
        List<OpenJobDO> scheduleTasks = openJobMapper.queryStartJob();
        if (CollectionUtils.isEmpty(scheduleTasks)){
            return Collections.emptyList();
        }
        return scheduleTasks.stream().map(openJobDO->{
            ScheduleTask scheduleTask = new ScheduleTask();
            scheduleTask.setTaskId(openJobDO.getId());
            scheduleTask.setCronExpression(openJobDO.getCronExpression());
            return scheduleTask;
        }).collect(Collectors.toList());
    }
}
