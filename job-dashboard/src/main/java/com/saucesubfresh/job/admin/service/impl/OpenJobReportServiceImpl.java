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
package com.saucesubfresh.job.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.saucesubfresh.job.admin.entity.OpenJobAppDO;
import com.saucesubfresh.job.admin.entity.OpenJobLogDO;
import com.saucesubfresh.job.admin.mapper.OpenJobAppMapper;
import com.saucesubfresh.job.api.dto.resp.OpenJobChartRespDTO;
import com.saucesubfresh.job.admin.entity.OpenJobReportDO;
import com.saucesubfresh.job.admin.mapper.OpenJobLogMapper;
import com.saucesubfresh.job.admin.mapper.OpenJobReportMapper;
import com.saucesubfresh.job.admin.service.OpenJobReportService;
import com.saucesubfresh.job.common.enums.CommonStatusEnum;
import com.saucesubfresh.job.common.time.LocalDateTimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lijunping on 2022/4/11
 */
@Service
public class OpenJobReportServiceImpl implements OpenJobReportService {
    
    private final OpenJobLogMapper openJobLogMapper;
    private final OpenJobAppMapper openJobAppMapper;
    private final OpenJobReportMapper openJobReportMapper;

    public OpenJobReportServiceImpl(OpenJobLogMapper openJobLogMapper,
                                    OpenJobAppMapper openJobAppMapper,
                                    OpenJobReportMapper openJobReportMapper) {
        this.openJobLogMapper = openJobLogMapper;
        this.openJobAppMapper = openJobAppMapper;
        this.openJobReportMapper = openJobReportMapper;
    }

    @Override
    public void generateReport(LocalDateTime now) {
        LocalDateTime startTime = LocalDateTimeUtil.getDayStart(now);
        LocalDateTime endTime = LocalDateTimeUtil.getDayEnd(now);

        List<OpenJobLogDO> openJobLogDOS = openJobLogMapper.groupByAppId(startTime, endTime);
        if (CollectionUtils.isEmpty(openJobLogDOS)){
            return;
        }
        for (OpenJobLogDO openJobLogDO : openJobLogDOS) {
            List<OpenJobLogDO> openJobLogDOS1 = openJobLogMapper.groupByJobId(openJobLogDO.getAppId(), startTime, endTime);
            for (OpenJobLogDO jobLogDO : openJobLogDOS1) {
                List<OpenJobLogDO> openJobLogDOS2 = openJobLogMapper.groupByServerId(openJobLogDO.getAppId(), jobLogDO.getJobId(), startTime, endTime);
                for (OpenJobLogDO logDO : openJobLogDOS2) {
                    int scheduleTotalCount = openJobLogMapper.getScheduleTotalCount(openJobLogDO.getId(), jobLogDO.getJobId(),logDO.getServerId(), null, startTime, endTime);
                    int scheduleSucceedCount = openJobLogMapper.getScheduleTotalCount(openJobLogDO.getId(), jobLogDO.getJobId(),logDO.getServerId(), CommonStatusEnum.YES, startTime, endTime);
                    OpenJobReportDO openJobReportDO = new OpenJobReportDO();
                    openJobReportDO.setAppId(openJobLogDO.getId());
                    openJobReportDO.setJobId(jobLogDO.getJobId());
                    openJobReportDO.setServerId(logDO.getServerId());
                    openJobReportDO.setTaskExecTotalCount(scheduleTotalCount);
                    openJobReportDO.setTaskExecSuccessCount(scheduleSucceedCount);
                    openJobReportDO.setCreateTime(now);
                    openJobReportMapper.insert(openJobReportDO);
                }
            }
        }
    }

    @Override
    public List<OpenJobChartRespDTO> getChart(Long appId, Integer count) {
        List<OpenJobReportDO> openJobReportDOS = openJobReportMapper.queryList(appId, count);
        if (CollectionUtils.isEmpty(openJobReportDOS)){
            return Collections.emptyList();
        }
        return openJobReportDOS.stream().map(e->{
            OpenJobChartRespDTO openJobChartRespDTO = new OpenJobChartRespDTO();
            openJobChartRespDTO.setDate(e.getCreateTime().toLocalDate());
            openJobChartRespDTO.setTotalCount(e.getTaskExecTotalCount());
            openJobChartRespDTO.setSuccessCount(e.getTaskExecSuccessCount());
            return openJobChartRespDTO;
        }).collect(Collectors.toList());
    }

}
