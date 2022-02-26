package org.open.job.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.open.job.admin.dto.req.OpenJobLogReqDTO;
import org.open.job.admin.entity.OpenJobLogDO;
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
public interface OpenJobLogMapper extends BaseMapper<OpenJobLogDO> {

    default Page<OpenJobLogDO> queryPage(OpenJobLogReqDTO OpenJobLogReqDTO){
        return selectPage(OpenJobLogReqDTO.page(), Wrappers.<OpenJobLogDO>lambdaQuery()
            .eq(Objects.nonNull(OpenJobLogReqDTO.getJobId()), OpenJobLogDO::getJobId, OpenJobLogReqDTO.getJobId())
            .between(Objects.nonNull(OpenJobLogReqDTO.getBeginTime()), OpenJobLogDO::getCreateTime, OpenJobLogReqDTO.getBeginTime(), OpenJobLogReqDTO.getEndTime()));
    }
}
