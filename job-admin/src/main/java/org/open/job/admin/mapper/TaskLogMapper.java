package org.open.job.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.open.job.admin.dto.req.TaskLogReqDTO;
import org.open.job.admin.entity.TaskLogDO;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * 爬虫任务运行日志
 * 
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Repository
public interface TaskLogMapper extends BaseMapper<TaskLogDO> {

    default Page<TaskLogDO> queryPage(TaskLogReqDTO TaskLogReqDTO){
        return selectPage(TaskLogReqDTO.page(), Wrappers.<TaskLogDO>lambdaQuery()
            .eq(Objects.nonNull(TaskLogReqDTO.getTaskId()), TaskLogDO::getTaskId, TaskLogReqDTO.getTaskId())
            .between(Objects.nonNull(TaskLogReqDTO.getBeginTime()), TaskLogDO::getCreateTime, TaskLogReqDTO.getBeginTime(), TaskLogReqDTO.getEndTime()));
    }
}
