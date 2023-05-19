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
package com.saucesubfresh.job.admin.controller;

import com.saucesubfresh.job.admin.service.OpenJobReportService;
import com.saucesubfresh.job.api.dto.resp.OpenJobStatisticRespDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobChartRespDTO;
import com.saucesubfresh.job.admin.service.OpenJobStatisticService;
import com.saucesubfresh.job.api.dto.resp.OpenTopKRespDTO;
import com.saucesubfresh.job.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lijunping on 2022/4/11
 */
@Validated
@RestController
@RequestMapping("/analysis")
public class OpenJobReportController {

    @Autowired
    private OpenJobReportService openJobReportService;

    @Autowired
    private OpenJobStatisticService crawlerStatisticService;

    @GetMapping("/statistic")
    public Result<OpenJobStatisticRespDTO> getStatistic(@RequestParam("appId") Long appId) {
        return Result.succeed(crawlerStatisticService.getStatistic(appId));
    }

    @GetMapping("/chart")
    public Result<List<OpenJobChartRespDTO>> getChart(@RequestParam(value = "appId") Long appId,
                                                      @RequestParam(value = "jobId", required = false) Long jobId,
                                                      @RequestParam(value = "serverId", required = false) String serverId,
                                                      @RequestParam(value = "count", required = false, defaultValue = "30") Integer count) {
        return Result.succeed(openJobReportService.getChart(appId, jobId, serverId, count));
    }

    @GetMapping("/jobTok")
    public Result<List<OpenTopKRespDTO>> getJobTopK(@RequestParam(value = "appId") Long appId,
                                                    @RequestParam(value = "serverId", required = false) String serverId,
                                                    @RequestParam(value = "count", required = false, defaultValue = "30") Integer count,
                                                    @RequestParam(value = "top", required = false, defaultValue = "10") Integer top) {
        return Result.succeed(openJobReportService.getJobTopK(appId, serverId, count, top));
    }

    @GetMapping("/instanceTok")
    public Result<List<OpenTopKRespDTO>> getInstanceTopK(@RequestParam(value = "appId") Long appId,
                                                         @RequestParam(value = "jobId", required = false) Long jobId,
                                                         @RequestParam(value = "count", required = false, defaultValue = "30") Integer count,
                                                         @RequestParam(value = "top", required = false, defaultValue = "10") Integer top) {
        return Result.succeed(openJobReportService.getInstanceTopK(appId, jobId, count, top));
    }
}
