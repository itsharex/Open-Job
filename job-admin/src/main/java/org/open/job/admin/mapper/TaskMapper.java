package org.open.job.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.open.job.admin.dto.req.TaskReqDTO;
import org.open.job.admin.entity.TaskDO;
import org.open.job.common.enums.CommonStatusEnum;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * 爬虫任务表
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Repository
public interface TaskMapper extends BaseMapper<TaskDO> {

    default Page<TaskDO> queryPage(TaskReqDTO TaskReqDTO){
        return selectPage(TaskReqDTO.page(), Wrappers.<TaskDO>lambdaQuery()
                .eq(Objects.nonNull(TaskReqDTO.getStatus()), TaskDO::getStatus, TaskReqDTO.getStatus())
                .between(Objects.nonNull(TaskReqDTO.getBeginTime()), TaskDO::getCreateTime, TaskReqDTO.getBeginTime(), TaskReqDTO.getEndTime()));
    }

    default List<TaskDO> queryActiveTaskList(){
        return selectList(Wrappers.<TaskDO>lambdaQuery()
                .eq(TaskDO::getStatus, CommonStatusEnum.YES.getValue())
        );
    }

    default List<TaskDO> getSpiderIdList(List<Long> taskList){
        return selectList(Wrappers.<TaskDO>lambdaQuery()
                .select(TaskDO::getSpiderId)
                .in(TaskDO::getId, taskList));
    }
}
