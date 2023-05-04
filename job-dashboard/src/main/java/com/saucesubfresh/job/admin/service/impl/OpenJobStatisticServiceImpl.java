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

import com.saucesubfresh.job.admin.service.OpenJobInstanceService;
import com.saucesubfresh.job.api.dto.resp.OpenJobInstanceRespDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobStatisticRespDTO;
import com.saucesubfresh.job.admin.mapper.OpenJobMapper;
import com.saucesubfresh.job.admin.service.OpenJobStatisticService;
import com.saucesubfresh.rpc.core.enums.Status;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lijunping on 2022/4/11
 */
@Service
public class OpenJobStatisticServiceImpl implements OpenJobStatisticService {

    private final OpenJobMapper openJobMapper;
    private final OpenJobInstanceService openJobInstanceService;

    public OpenJobStatisticServiceImpl(OpenJobMapper openJobMapper,
                                       OpenJobInstanceService openJobInstanceService) {
        this.openJobMapper = openJobMapper;
        this.openJobInstanceService = openJobInstanceService;
    }

    @Override
    public OpenJobStatisticRespDTO getStatistic(Long appId) {
        int taskTotalCount = openJobMapper.getTotalCount(appId);
        int taskRunningCount = openJobMapper.getRunningCount(appId);
        List<OpenJobInstanceRespDTO> instanceList = openJobInstanceService.getInstanceList(appId);
        int instanceTotalCount = instanceList.size();
        long instanceOnlineCount = instanceList.stream().filter(e-> StringUtils.equals(e.getStatus(), Status.ON_LINE.name())).count();
        return OpenJobStatisticRespDTO.builder()
               .taskTotalNum(taskTotalCount)
               .taskRunningNum(taskRunningCount)
               .executorTotalNum(instanceTotalCount)
               .executorOnlineNum((int) instanceOnlineCount)
               .build();
    }
}
