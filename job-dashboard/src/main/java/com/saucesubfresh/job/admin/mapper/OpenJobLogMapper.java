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
package com.saucesubfresh.job.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saucesubfresh.job.api.dto.req.OpenJobLogReqDTO;
import com.saucesubfresh.job.admin.entity.OpenJobLogDO;
import com.saucesubfresh.job.common.enums.CommonStatusEnum;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 任务运行日志
 * 
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Repository
public interface OpenJobLogMapper extends BaseMapper<OpenJobLogDO> {

    default Page<OpenJobLogDO> queryPage(OpenJobLogReqDTO openJobLogReqDTO){
        return selectPage(openJobLogReqDTO.page(), Wrappers.<OpenJobLogDO>lambdaQuery()
            .eq(Objects.nonNull(openJobLogReqDTO.getJobId()), OpenJobLogDO::getJobId, openJobLogReqDTO.getJobId())
            .eq(Objects.nonNull(openJobLogReqDTO.getStatus()), OpenJobLogDO::getStatus, openJobLogReqDTO.getStatus())
            .between(Objects.nonNull(openJobLogReqDTO.getBeginTime()), OpenJobLogDO::getCreateTime, openJobLogReqDTO.getBeginTime(), openJobLogReqDTO.getEndTime())
            .orderByDesc(OpenJobLogDO::getCreateTime)
        );
    }

    default int getScheduleTotalCount(Long appId, Long jobId, String serverId, LocalDateTime beginTime, LocalDateTime endTime){
        return selectCount(Wrappers.<OpenJobLogDO>lambdaQuery()
                .eq(OpenJobLogDO::getAppId, appId)
                .eq(OpenJobLogDO::getJobId, jobId)
                .eq(OpenJobLogDO::getServerId, serverId)
                .between(OpenJobLogDO::getCreateTime, beginTime, endTime)
        );
    }

    default int getScheduleSuccessTotalCount(Long appId, Long jobId, String serverId, LocalDateTime beginTime, LocalDateTime endTime){
        return selectCount(Wrappers.<OpenJobLogDO>lambdaQuery()
                .eq(OpenJobLogDO::getAppId, appId)
                .eq(OpenJobLogDO::getJobId, jobId)
                .eq(OpenJobLogDO::getServerId, serverId)
                .eq(OpenJobLogDO::getStatus, CommonStatusEnum.YES.getValue())
                .between(OpenJobLogDO::getCreateTime, beginTime, endTime)
        );
    }

    default void clearLog(LocalDateTime now, Integer interval){
        delete(Wrappers.<OpenJobLogDO>lambdaQuery()
                .lt(OpenJobLogDO::getCreateTime, now.plusDays(-interval))
        );
    }

    List<OpenJobLogDO> groupByAppId(@Param("startTime") LocalDateTime startTime,
                                    @Param("endTime") LocalDateTime endTime);

    List<OpenJobLogDO> groupByJobId(@Param("appId") Long appId,
                                    @Param("startTime") LocalDateTime startTime,
                                    @Param("endTime") LocalDateTime endTime);

    List<OpenJobLogDO> groupByServerId(@Param("appId") Long appId,
                                       @Param("jobId") Long jobId,
                                       @Param("startTime") LocalDateTime startTime,
                                       @Param("endTime") LocalDateTime endTime);
}
