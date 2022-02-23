package org.open.job.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.open.job.admin.convert.TaskLogConvert;
import org.open.job.admin.dto.create.TaskLogCreateDTO;
import org.open.job.admin.dto.req.TaskLogReqDTO;
import org.open.job.admin.dto.resp.TaskLogRespDTO;
import org.open.job.admin.dto.update.TaskLogUpdateDTO;
import org.open.job.admin.entity.TaskLogDO;
import org.open.job.admin.mapper.TaskLogMapper;
import org.open.job.admin.service.TaskLogService;
import org.open.job.common.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TaskLogServiceImpl extends ServiceImpl<org.open.job.admin.mapper.TaskLogMapper, TaskLogDO> implements TaskLogService {

    @Autowired
    private TaskLogMapper TaskLogMapper;

    @Override
    public PageResult<TaskLogRespDTO> selectPage(TaskLogReqDTO TaskLogReqDTO) {
        Page<TaskLogDO> page = TaskLogMapper.queryPage(TaskLogReqDTO);
        IPage<TaskLogRespDTO> convert = page.convert(TaskLogConvert.INSTANCE::convert);
        return PageResult.build(convert);
    }

    @Override
    public TaskLogRespDTO getById(Long id) {
        TaskLogDO TaskLogDO = TaskLogMapper.selectById(id);
        return TaskLogConvert.INSTANCE.convert(TaskLogDO);
    }

    @Override
    public boolean save(TaskLogCreateDTO TaskLogCreateDTO) {
        TaskLogMapper.insert(TaskLogConvert.INSTANCE.convert(TaskLogCreateDTO));
        return true;
    }

    @Override
    public boolean updateById(TaskLogUpdateDTO TaskLogUpdateDTO) {
        TaskLogMapper.updateById(TaskLogConvert.INSTANCE.convert(TaskLogUpdateDTO));
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        TaskLogMapper.deleteById(id);
        return true;
    }

}