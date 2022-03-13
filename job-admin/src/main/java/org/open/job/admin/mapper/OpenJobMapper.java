package org.open.job.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.open.job.admin.dto.req.OpenJobReqDTO;
import org.open.job.admin.entity.OpenJobDO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * 任务表
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Repository
public interface OpenJobMapper extends BaseMapper<OpenJobDO> {

    default Page<OpenJobDO> queryPage(OpenJobReqDTO openJobReqDTO){
        return selectPage(openJobReqDTO.page(), Wrappers.<OpenJobDO>lambdaQuery()
                .like(Objects.nonNull(openJobReqDTO.getJobName()), OpenJobDO::getJobName, openJobReqDTO.getJobName())
                .like(Objects.nonNull(openJobReqDTO.getHandlerName()), OpenJobDO::getHandlerName, openJobReqDTO.getHandlerName())
                .eq(Objects.nonNull(openJobReqDTO.getStatus()), OpenJobDO::getStatus, openJobReqDTO.getStatus())
                .between(Objects.nonNull(openJobReqDTO.getBeginTime()), OpenJobDO::getCreateTime, openJobReqDTO.getBeginTime(), openJobReqDTO.getEndTime()));
    }

    default List<OpenJobDO> queryList(List<Long> taskList){
        return selectList(Wrappers.<OpenJobDO>lambdaQuery()
                .in(OpenJobDO::getId, taskList));
    }
}
