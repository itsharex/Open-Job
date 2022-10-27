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

import com.saucesubfresh.job.api.enums.OpenJobReportEnum;
import com.saucesubfresh.job.api.dto.resp.OpenJobReportRespDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobStatisticNumberRespDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobStatisticReportRespDTO;
import com.saucesubfresh.job.admin.mapper.OpenJobLogMapper;
import com.saucesubfresh.job.admin.mapper.OpenJobMapper;
import com.saucesubfresh.job.admin.service.OpenJobReportService;
import com.saucesubfresh.job.admin.service.OpenJobStatisticService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lijunping on 2022/4/11
 */
@Service
public class OpenJobStatisticServiceImpl implements OpenJobStatisticService {

    private final OpenJobMapper openJobMapper;
    private final OpenJobLogMapper openJobLogMapper;
    private final OpenJobReportService openJobReportService;

    public OpenJobStatisticServiceImpl(OpenJobMapper openJobMapper,
                                       OpenJobLogMapper openJobLogMapper,
                                       OpenJobReportService openJobReportService) {
        this.openJobMapper = openJobMapper;
        this.openJobLogMapper = openJobLogMapper;
        this.openJobReportService = openJobReportService;
    }

    @Override
    public OpenJobStatisticNumberRespDTO getStatisticNumber() {
        int taskTotalCount = openJobMapper.getTotalCount();
        int taskRunningCount = openJobMapper.getRunningCount();
        int scheduleTotalCount = openJobLogMapper.getScheduleTotalCount();
        int scheduleSucceedCount = openJobLogMapper.getScheduleSucceedCount();

        return OpenJobStatisticNumberRespDTO.builder()
                .taskTotalNum(taskTotalCount)
                .taskRunningNum(taskRunningCount)
                .scheduleTotalNum(scheduleTotalCount)
                .scheduleSucceedNum(scheduleSucceedCount)
                .build();
    }

    @Override
    public List<OpenJobStatisticReportRespDTO> getStatisticReport() {
        List<OpenJobReportRespDTO> reportRespDTOS = openJobReportService.getOpenJobReportList();
        if (CollectionUtils.isEmpty(reportRespDTOS)){
            return Collections.emptyList();
        }

        List<OpenJobStatisticReportRespDTO> execTotal = build(reportRespDTOS, OpenJobReportEnum.TASK_EXEC_TOTAL_COUNT);
        List<OpenJobStatisticReportRespDTO> execSuccess = build(reportRespDTOS, OpenJobReportEnum.TASK_EXEC_SUCCESS_COUNT);
        execTotal.addAll(execSuccess);
        return execTotal;
    }

    private List<OpenJobStatisticReportRespDTO> build(List<OpenJobReportRespDTO> reportRespDTOS, OpenJobReportEnum reportEnum){
        return reportRespDTOS.stream().map(e -> {
            OpenJobStatisticReportRespDTO openJobStatisticReportRespDTO = new OpenJobStatisticReportRespDTO();
            openJobStatisticReportRespDTO.setDate(e.getCreateTime());
            Integer value = null;
            switch (reportEnum){
                case TASK_EXEC_TOTAL_COUNT:
                    value = e.getTaskExecTotalCount();
                    break;
                case TASK_EXEC_SUCCESS_COUNT:
                    value = e.getTaskExecSuccessCount();
                    break;
            }
            openJobStatisticReportRespDTO.setName(reportEnum.getName());
            openJobStatisticReportRespDTO.setValue(value);
            return openJobStatisticReportRespDTO;
        }).collect(Collectors.toList());
    }
}
