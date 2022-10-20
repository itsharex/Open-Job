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
package com.saucesubfresh.job.admin.task;

import com.saucesubfresh.job.admin.mapper.OpenJobLogMapper;
import com.saucesubfresh.job.admin.service.OpenJobReportService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author lijunping on 2022/4/22
 */
@Component
public class SystemTask {

    @Value("${clear-interval}")
    private Integer clearInterval;

    private final OpenJobLogMapper openJobLogMapper;
    private final OpenJobReportService openJobReportService;

    public SystemTask(OpenJobLogMapper openJobLogMapper,
                      OpenJobReportService openJobReportService) {
        this.openJobLogMapper = openJobLogMapper;
        this.openJobReportService = openJobReportService;
    }


    /**
     * 定时插入报表任务
     */
    @Scheduled(cron = "0 59 23 * * ?")
    public void addReportTask(){
        openJobReportService.insertReport();
    }

    /**
     * 定时清除任务日志任务
     */
    @Scheduled(cron = "0 0 12 ? * 6")
    public void clearTaskLogTask(){
        openJobLogMapper.clearLog(clearInterval);
    }
}
