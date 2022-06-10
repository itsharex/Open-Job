package com.saucesubfresh.job.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saucesubfresh.job.admin.dto.req.OpenJobAppReqDTO;
import com.saucesubfresh.job.admin.entity.OpenJobAppDO;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * 任务表
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Repository
public interface OpenJobAppMapper extends BaseMapper<OpenJobAppDO> {

    default Page<OpenJobAppDO> queryPage(OpenJobAppReqDTO openJobAppReqDTO){
        return selectPage(openJobAppReqDTO.page(), Wrappers.<OpenJobAppDO>lambdaQuery()
                .like(Objects.nonNull(openJobAppReqDTO.getAppDesc()), OpenJobAppDO::getAppDesc, openJobAppReqDTO.getAppDesc())
                .like(Objects.nonNull(openJobAppReqDTO.getAppName()), OpenJobAppDO::getAppName, openJobAppReqDTO.getAppName())
                .between(Objects.nonNull(openJobAppReqDTO.getBeginTime()), OpenJobAppDO::getCreateTime, openJobAppReqDTO.getBeginTime(), openJobAppReqDTO.getEndTime()));
    }
}
