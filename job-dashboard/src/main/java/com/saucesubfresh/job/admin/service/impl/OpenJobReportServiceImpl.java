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

import com.saucesubfresh.job.admin.entity.OpenJobLogDO;
import com.saucesubfresh.job.api.dto.resp.OpenJobChartRespDTO;
import com.saucesubfresh.job.admin.entity.OpenJobReportDO;
import com.saucesubfresh.job.admin.mapper.OpenJobLogMapper;
import com.saucesubfresh.job.admin.mapper.OpenJobReportMapper;
import com.saucesubfresh.job.admin.service.OpenJobReportService;
import com.saucesubfresh.job.api.dto.resp.OpenTopKRespDTO;
import com.saucesubfresh.job.common.time.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lijunping on 2022/4/11
 */
@Slf4j
@Service
public class OpenJobReportServiceImpl implements OpenJobReportService {
    
    private final OpenJobLogMapper openJobLogMapper;
    private final OpenJobReportMapper openJobReportMapper;

    public OpenJobReportServiceImpl(OpenJobLogMapper openJobLogMapper,
                                    OpenJobReportMapper openJobReportMapper) {
        this.openJobLogMapper = openJobLogMapper;
        this.openJobReportMapper = openJobReportMapper;
    }

    @Override
    public void generateReport(LocalDateTime now) {
        LocalDateTime startTime = LocalDateTimeUtil.getDayStart(now);
        LocalDateTime endTime = LocalDateTimeUtil.getDayEnd(now);

        List<OpenJobLogDO> jobLogsByAppId = openJobLogMapper.groupByAppId(startTime, endTime);
        if (CollectionUtils.isEmpty(jobLogsByAppId)){
            return;
        }

        for (OpenJobLogDO jobLogByAppId : jobLogsByAppId) {
            List<OpenJobLogDO> jobLogsByJobId = openJobLogMapper
                    .groupByJobId(
                            jobLogByAppId.getAppId(),
                            startTime,
                            endTime
                    );
            for (OpenJobLogDO jobLogByJobId : jobLogsByJobId) {
                List<OpenJobLogDO> jobLogsByServerId = openJobLogMapper
                        .groupByServerId(
                                jobLogByAppId.getAppId(),
                                jobLogByJobId.getJobId(),
                                startTime,
                                endTime
                        );
                for (OpenJobLogDO jobLogByServerId : jobLogsByServerId) {
                    int scheduleTotalCount = openJobLogMapper.getScheduleTotalCount(
                            jobLogByAppId.getAppId(),
                            jobLogByJobId.getJobId(),
                            jobLogByServerId.getServerId(),
                            startTime,
                            endTime
                    );
                    int scheduleSucceedCount = openJobLogMapper.getScheduleSuccessTotalCount(
                            jobLogByAppId.getAppId(),
                            jobLogByJobId.getJobId(),
                            jobLogByServerId.getServerId(),
                            startTime,
                            endTime);

                    OpenJobReportDO openJobReportDO = new OpenJobReportDO();
                    openJobReportDO.setAppId(jobLogByAppId.getAppId());
                    openJobReportDO.setJobId(jobLogByJobId.getJobId());
                    openJobReportDO.setServerId(jobLogByServerId.getServerId());
                    openJobReportDO.setTaskExecTotalCount(scheduleTotalCount);
                    openJobReportDO.setTaskExecSuccessCount(scheduleSucceedCount);
                    openJobReportDO.setCreateTime(endTime);
                    openJobReportMapper.insert(openJobReportDO);
                }
            }
        }
    }

    @Override
    public List<OpenJobChartRespDTO> getChart(Long appId, Long jobId, String serverId, Integer count) {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTimeUtil.getDayStart(startTime.plusDays(count));
        List<OpenJobReportDO> openJobReportDOS = openJobReportMapper.queryList(appId, jobId, serverId, startTime, endTime);
        if (CollectionUtils.isEmpty(openJobReportDOS)){
            return Collections.emptyList();
        }

        Map<LocalDateTime, List<OpenJobReportDO>> groupByDateMap = openJobReportDOS.stream().collect(Collectors.groupingBy(
                OpenJobReportDO::getCreateTime
        ));

        List<OpenJobChartRespDTO> chartRespDTOS = new ArrayList<>();
        groupByDateMap.forEach((k, v) ->{
            Integer totalCount = v.stream().map(OpenJobReportDO::getTaskExecTotalCount).reduce(Integer::sum).orElse(0);
            Integer successCount = v.stream().map(OpenJobReportDO::getTaskExecSuccessCount).reduce(Integer::sum).orElse(0);
            OpenJobChartRespDTO openJobChartRespDTO = new OpenJobChartRespDTO();
            openJobChartRespDTO.setDate(k.toLocalDate());
            openJobChartRespDTO.setTotalCount(totalCount);
            openJobChartRespDTO.setSuccessCount(successCount);
            chartRespDTOS.add(openJobChartRespDTO);
        });

        return chartRespDTOS;
    }

    @Override
    public List<OpenTopKRespDTO> getJobTopK(Long appId, String serverId, Integer count, Integer top) {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTimeUtil.getDayStart(startTime.plusDays(count));
        List<OpenJobReportDO> openJobReportDOS = openJobReportMapper.queryList(appId, null, serverId, startTime, endTime);
        if (CollectionUtils.isEmpty(openJobReportDOS)){
            return Collections.emptyList();
        }

        Map<Long, List<OpenJobReportDO>> groupMap = openJobReportDOS.stream().collect(Collectors.groupingBy(
                OpenJobReportDO::getJobId
        ));

        Map<String, List<OpenJobReportDO>> collectMap = groupMap.entrySet().stream().collect(
                Collectors.toMap(e-> String.valueOf(e.getKey()), Map.Entry::getValue));

        return getTopK(collectMap, top);
    }

    @Override
    public List<OpenTopKRespDTO> getInstanceTopK(Long appId, Long jobId, Integer count, Integer top) {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTimeUtil.getDayStart(startTime.plusDays(count));
        List<OpenJobReportDO> openJobReportDOS = openJobReportMapper.queryList(appId, jobId, null, startTime, endTime);
        if (CollectionUtils.isEmpty(openJobReportDOS)){
            return Collections.emptyList();
        }

        Map<String, List<OpenJobReportDO>> groupMap = openJobReportDOS.stream().collect(Collectors.groupingBy(
                OpenJobReportDO::getServerId
        ));

        return getTopK(groupMap, top);
    }

    private List<OpenTopKRespDTO> getTopK(Map<String, List<OpenJobReportDO>> groupMap, Integer top){
        List<OpenTopKRespDTO> topKRespDTOS = new ArrayList<>();
        for (Map.Entry<String, List<OpenJobReportDO>> entry : groupMap.entrySet()) {
            List<OpenJobReportDO> value = entry.getValue();
            Integer totalCount = value.stream().map(OpenJobReportDO::getTaskExecTotalCount).reduce(Integer::sum).orElse(0);
            Integer successCount = value.stream().map(OpenJobReportDO::getTaskExecSuccessCount).reduce(Integer::sum).orElse(0);
            OpenTopKRespDTO openTopKRespDTO = new OpenTopKRespDTO();
            openTopKRespDTO.setKey(entry.getKey());
            openTopKRespDTO.setTotalCount(totalCount);
            openTopKRespDTO.setSuccessCount(successCount);
            topKRespDTOS.add(openTopKRespDTO);
        }

        if (topKRespDTOS.size() > top){
            topKRespDTOS = topKRespDTOS.subList(0, top);
        }

        topKRespDTOS.sort((u1, u2) -> u2.getTotalCount().compareTo(u1.getTotalCount()));

        return topKRespDTOS;
    }

}
