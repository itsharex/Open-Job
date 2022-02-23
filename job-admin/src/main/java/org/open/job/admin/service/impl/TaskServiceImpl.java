package org.open.job.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.open.job.admin.convert.TaskConvert;
import org.open.job.admin.dto.create.TaskCreateDTO;
import org.open.job.admin.dto.req.TaskReqDTO;
import org.open.job.admin.dto.resp.TaskRespDTO;
import org.open.job.admin.dto.update.TaskUpdateDTO;
import org.open.job.admin.entity.TaskDO;
import org.open.job.admin.mapper.TaskMapper;
import org.open.job.admin.service.TaskService;
import org.open.job.common.enums.CommonStatusEnum;
import org.open.job.common.exception.ServiceException;
import org.open.job.common.vo.PageResult;
import org.open.job.starter.schedule.core.ScheduleTaskManage;
import org.open.job.starter.schedule.cron.CronExpression;
import org.open.job.starter.schedule.domain.ScheduleTask;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author lijunping on 2022/2/17
 */
@Service
public class TaskServiceImpl extends ServiceImpl<org.open.job.admin.mapper.TaskMapper, TaskDO> implements TaskService {

    private final ScheduleTaskManage scheduleTaskManage;
    private final TaskMapper TaskMapper;

    public TaskServiceImpl(ScheduleTaskManage scheduleTaskManage, TaskMapper TaskMapper) {
        this.scheduleTaskManage = scheduleTaskManage;
        this.TaskMapper = TaskMapper;
    }

    @Override
    public PageResult<TaskRespDTO> selectPage(TaskReqDTO TaskReqDTO) {
        Page<TaskDO> page = TaskMapper.queryPage(TaskReqDTO);
        IPage<TaskRespDTO> convert = page.convert(TaskConvert.INSTANCE::convert);
        return PageResult.build(convert);
    }

    @Override
    public TaskRespDTO getById(Long id) {
        TaskDO TaskDO = TaskMapper.selectById(id);
        return TaskConvert.INSTANCE.convert(TaskDO);
    }

    @Override
    public List<Long> getSpiderIdList(List<Long> taskList) {
        List<TaskDO> spiderTaskDOS = TaskMapper.getSpiderIdList(taskList);
        if (CollectionUtils.isEmpty(spiderTaskDOS)){
            return Collections.emptyList();
        }
        return spiderTaskDOS.stream().map(TaskDO::getSpiderId).collect(Collectors.toList());
    }

    @Override
    public boolean save(TaskCreateDTO TaskCreateDTO) {
        String cronExpression = TaskCreateDTO.getCronExpression();
        if (!CronExpression.isValidExpression(cronExpression)){
            throw new ServiceException("Invalid cronExpression");
        }
        TaskMapper.insert(TaskConvert.INSTANCE.convert(TaskCreateDTO));
        return true;
    }

    @Override
    public boolean updateById(TaskUpdateDTO TaskUpdateDTO) {
        TaskMapper.updateById(TaskConvert.INSTANCE.convert(TaskUpdateDTO));
        return true;
    }

    @Override
    public boolean start(Long id) {
        TaskDO TaskDO = TaskMapper.selectById(id);
        TaskDO.setStatus(CommonStatusEnum.YES.getValue());
        TaskMapper.updateById(TaskDO);
        ScheduleTask scheduleTask = createScheduleTask(TaskDO);
        scheduleTaskManage.addOrUpdateScheduleTask(scheduleTask);
        return true;
    }

    @Override
    public boolean stop(Long id) {
        TaskDO TaskDO = TaskMapper.selectById(id);
        TaskDO.setStatus(CommonStatusEnum.NO.getValue());
        TaskMapper.updateById(TaskDO);
        scheduleTaskManage.removeScheduleTask(id);
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        TaskMapper.deleteById(id);
        return true;
    }

    private ScheduleTask createScheduleTask(TaskDO TaskDO){
        ScheduleTask scheduleTask = new ScheduleTask();
        scheduleTask.setTaskId(TaskDO.getId());
        scheduleTask.setCronExpression(TaskDO.getCronExpression());
        return scheduleTask;
    }

}
