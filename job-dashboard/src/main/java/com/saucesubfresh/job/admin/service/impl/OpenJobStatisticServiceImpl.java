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

import com.saucesubfresh.job.admin.entity.OpenJobAppDO;
import com.saucesubfresh.job.admin.entity.OpenJobDO;
import com.saucesubfresh.job.admin.entity.OpenJobLogDO;
import com.saucesubfresh.job.admin.mapper.OpenJobAlarmRecordMapper;
import com.saucesubfresh.job.admin.mapper.OpenJobAppMapper;
import com.saucesubfresh.job.admin.mapper.OpenJobLogMapper;
import com.saucesubfresh.job.admin.mapper.OpenJobMapper;
import com.saucesubfresh.job.admin.service.OpenJobInstanceService;
import com.saucesubfresh.job.admin.service.OpenJobStatisticService;
import com.saucesubfresh.job.api.dto.resp.OpenJobInstanceRespDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobStatisticRespDTO;
import com.saucesubfresh.job.common.time.LocalDateTimeUtil;
import com.saucesubfresh.rpc.core.enums.Status;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author lijunping on 2022/4/11
 */
@Service
public class OpenJobStatisticServiceImpl implements OpenJobStatisticService {

    private final OpenJobAppMapper appMapper;
    private final OpenJobMapper openJobMapper;
    private final OpenJobLogMapper openJobLogMapper;
    private final OpenJobAlarmRecordMapper alarmRecordMapper;
    private final OpenJobInstanceService openJobInstanceService;

    public OpenJobStatisticServiceImpl(OpenJobAppMapper appMapper,
                                       OpenJobMapper openJobMapper,
                                       OpenJobLogMapper openJobLogMapper,
                                       OpenJobAlarmRecordMapper alarmRecordMapper,
                                       OpenJobInstanceService openJobInstanceService) {
        this.appMapper = appMapper;
        this.openJobMapper = openJobMapper;
        this.openJobLogMapper = openJobLogMapper;
        this.alarmRecordMapper = alarmRecordMapper;
        this.openJobInstanceService = openJobInstanceService;
    }

    @Override
    public OpenJobStatisticRespDTO getStatistic() {
        List<OpenJobAppDO> openJobAppDOS = appMapper.queryList(null);
        if (CollectionUtils.isEmpty(openJobAppDOS)){
            return null;
        }

        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTimeUtil.getDayStart(endTime);
        int alarmCount = alarmRecordMapper.queryCount(null, null, startTime, endTime);

        int taskTotalNum = 0;
        int taskRunningNum = 0;
        int executorTotalNum = 0;
        int executorOnlineNum = 0;
        for (OpenJobAppDO openJobAppDO : openJobAppDOS) {
            OpenJobStatisticRespDTO appStatistic = this.getAppStatistic(openJobAppDO.getId());
            taskTotalNum += appStatistic.getTaskTotalNum();
            taskRunningNum += appStatistic.getTaskRunningNum();
            executorTotalNum += appStatistic.getExecutorTotalNum();
            executorOnlineNum += appStatistic.getExecutorOnlineNum();
        }

        return OpenJobStatisticRespDTO.builder()
                .appNum(openJobAppDOS.size())
                .alarmNum(alarmCount)
                .taskTotalNum(taskTotalNum)
                .taskRunningNum(taskRunningNum)
                .executorTotalNum(executorTotalNum)
                .executorOnlineNum(executorOnlineNum)
                .build();
    }

    @Override
    public OpenJobStatisticRespDTO getAppStatistic(Long appId) {
        int taskTotalCount = openJobMapper.getTotalCount(appId);
        int taskRunningCount = openJobMapper.getRunningCount(appId);
        List<OpenJobInstanceRespDTO> instanceList = openJobInstanceService.getInstanceList(appId);
        int instanceTotalCount = instanceList.size();
        long instanceOnlineCount = instanceList.stream().filter(e-> StringUtils.equals(e.getStatus(), Status.ON_LINE.name())).count();
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTimeUtil.getDayStart(endTime);
        int alarmCount = alarmRecordMapper.queryCount(appId, null, startTime, endTime);
        return OpenJobStatisticRespDTO.builder()
                .alarmNum(alarmCount)
                .taskTotalNum(taskTotalCount)
                .taskRunningNum(taskRunningCount)
                .executorTotalNum(instanceTotalCount)
                .executorOnlineNum((int) instanceOnlineCount)
                .build();
    }

    @Override
    public OpenJobStatisticRespDTO getJobStatistic(Long appId, Long jobId) {
        OpenJobDO openJobDO = openJobMapper.selectById(jobId);
        OpenJobLogDO openJobLogDO = openJobLogMapper.getLastOne(appId, jobId);
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTimeUtil.getDayStart(endTime);
        int alarmCount = alarmRecordMapper.queryCount(appId, jobId, startTime, endTime);
        return OpenJobStatisticRespDTO.builder()
                .alarmNum(alarmCount)
                .status(openJobDO.getStatus().toString())
                .lastRunTime(Objects.isNull(openJobLogDO) ? null : openJobLogDO.getCreateTime())
                .stateChangeTime(openJobDO.getUpdateTime())
                .build();
    }

    @Override
    public OpenJobStatisticRespDTO getInstanceStatistic(Long appId, String serverId) {
        OpenJobInstanceRespDTO instance = openJobInstanceService.getInstanceById(appId, serverId);
        if (Objects.isNull(instance)){
            return null;
        }

        return OpenJobStatisticRespDTO.builder()
                .status(instance.getStatus())
                .liveTime(instance.getLiveTime())
                .cpuInfo(instance.getCpuInfo())
                .memoryInfo(instance.getMemoryInfo())
                .diskInfo(instance.getDiskInfo())
                .build();
    }
}
