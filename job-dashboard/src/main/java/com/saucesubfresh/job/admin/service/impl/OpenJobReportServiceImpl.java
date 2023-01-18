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
import com.saucesubfresh.job.admin.mapper.OpenJobAppMapper;
import com.saucesubfresh.job.api.dto.resp.OpenJobChartRespDTO;
import com.saucesubfresh.job.admin.entity.OpenJobReportDO;
import com.saucesubfresh.job.admin.mapper.OpenJobLogMapper;
import com.saucesubfresh.job.admin.mapper.OpenJobReportMapper;
import com.saucesubfresh.job.admin.service.OpenJobReportService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
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
        List<OpenJobAppDO> openJobAppDOS = openJobAppMapper.selectList(Wrappers.lambdaQuery());
        if (CollectionUtils.isEmpty(openJobAppDOS)){
            return;
        }
        for (OpenJobAppDO appDO : openJobAppDOS) {
            int scheduleTotalCount = openJobLogMapper.getScheduleTotalCount(appDO.getId(), now.plusDays(-1), now);
            int scheduleSucceedCount = openJobLogMapper.getScheduleSucceedCount(appDO.getId(), now.plusDays(-1), now);
            OpenJobReportDO openJobReportDO = new OpenJobReportDO();
            openJobReportDO.setAppId(appDO.getId());
            openJobReportDO.setTaskExecTotalCount(scheduleTotalCount);
            openJobReportDO.setTaskExecSuccessCount(scheduleSucceedCount);
            openJobReportDO.setCreateTime(now);
            openJobReportMapper.insert(openJobReportDO);
        }
    }

    @Override
    public OpenJobChartRespDTO getChart(Long appId) {
        List<OpenJobReportDO> openJobReportDOS = openJobReportMapper.queryList(appId,30);
        if (CollectionUtils.isEmpty(openJobReportDOS)){
            return null;
        }
        OpenJobChartRespDTO openJobChartRespDTO = new OpenJobChartRespDTO();
        openJobChartRespDTO.setDate(openJobReportDOS.stream().map(e->e.getCreateTime().toLocalDate()).collect(Collectors.toList()));
        openJobChartRespDTO.setTotalCount(openJobReportDOS.stream().map(OpenJobReportDO::getTaskExecTotalCount).collect(Collectors.toList()));
        openJobChartRespDTO.setSuccessCount(openJobReportDOS.stream().map(OpenJobReportDO::getTaskExecSuccessCount).collect(Collectors.toList()));
        return openJobChartRespDTO;
    }

}
