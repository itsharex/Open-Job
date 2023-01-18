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

import com.saucesubfresh.job.admin.mapper.OpenJobLogMapper;
import com.saucesubfresh.job.admin.service.OpenJobInstanceService;
import com.saucesubfresh.job.api.dto.resp.OpenJobInstanceRespDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobStatisticRespDTO;
import com.saucesubfresh.job.admin.mapper.OpenJobMapper;
import com.saucesubfresh.job.admin.service.OpenJobStatisticService;
import com.saucesubfresh.rpc.core.enums.Status;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @author lijunping on 2022/4/11
 */
@Service
public class OpenJobStatisticServiceImpl implements OpenJobStatisticService {

    private final OpenJobMapper openJobMapper;
    private final OpenJobLogMapper openJobLogMapper;
    private final OpenJobInstanceService openJobInstanceService;

    public OpenJobStatisticServiceImpl(OpenJobMapper openJobMapper,
                                       OpenJobLogMapper openJobLogMapper,
                                       OpenJobInstanceService openJobInstanceService) {
        this.openJobMapper = openJobMapper;
        this.openJobLogMapper = openJobLogMapper;
        this.openJobInstanceService = openJobInstanceService;
    }

    @Override
    public OpenJobStatisticRespDTO getStatistic(Long appId) {
        int taskTotalCount = openJobMapper.getTotalCount();
        int taskRunningCount = openJobMapper.getRunningCount();
        List<OpenJobInstanceRespDTO> instanceList = openJobInstanceService.getInstanceList(appId);
        int instanceTotalCount = instanceList.size();
        long instanceOnlineCount = instanceList.stream().filter(e-> StringUtils.equals(e.getStatus(), Status.ON_LINE.name())).count();
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime of = LocalDateTime.of(now.toLocalDate(), LocalTime.MIN);//当天的零点
        int scheduleTotalCount = openJobLogMapper.getScheduleTotalCount(appId, of, now);
        int scheduleSucceedCount = openJobLogMapper.getScheduleSucceedCount(appId, of, now);
        return OpenJobStatisticRespDTO.builder()
                .taskTotalNum(taskTotalCount)
                .taskRunningNum(taskRunningCount)
                .executorTotalNum(instanceTotalCount)
                .executorOnlineNum((int) instanceOnlineCount)
                .scheduleTotalNum(scheduleTotalCount)
                .scheduleSucceedNum(scheduleSucceedCount)
                .build();
    }
}
