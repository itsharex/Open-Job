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

import com.saucesubfresh.job.admin.convert.OpenJobReportConvert;
import com.saucesubfresh.job.api.dto.resp.OpenJobReportRespDTO;
import com.saucesubfresh.job.admin.entity.OpenJobReportDO;
import com.saucesubfresh.job.admin.mapper.OpenJobLogMapper;
import com.saucesubfresh.job.admin.mapper.OpenJobReportMapper;
import com.saucesubfresh.job.admin.service.OpenJobReportService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lijunping on 2022/4/11
 */
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
    public void insertReport() {
        int scheduleTotalCount = openJobLogMapper.getScheduleTotalCount();
        int scheduleSucceedCount = openJobLogMapper.getScheduleSucceedCount();
        OpenJobReportDO openJobReportDO = new OpenJobReportDO();
        openJobReportDO.setTaskExecTotalCount(scheduleTotalCount);
        openJobReportDO.setTaskExecSuccessCount(scheduleSucceedCount);
        openJobReportDO.setCreateTime(LocalDateTime.now());
        openJobReportMapper.insert(openJobReportDO);
    }

    @Override
    public List<OpenJobReportRespDTO> getOpenJobReportList() {
        List<OpenJobReportDO> openJobReportDOS = openJobReportMapper.queryList();
        return OpenJobReportConvert.INSTANCE.convertList(openJobReportDOS);
    }

}
