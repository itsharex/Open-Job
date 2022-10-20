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

import com.saucesubfresh.job.api.dto.resp.OpenJobStatisticNumberRespDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobStatisticReportRespDTO;
import com.saucesubfresh.job.admin.service.OpenJobStatisticService;
import com.saucesubfresh.job.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lijunping on 2022/4/11
 */
@Validated
@RestController
@RequestMapping("/statistic")
public class OpenJobStatisticController {

    @Autowired
    private OpenJobStatisticService crawlerStatisticService;

    @GetMapping("/number")
    public Result<OpenJobStatisticNumberRespDTO> getStatisticNumber() {
        return Result.succeed(crawlerStatisticService.getStatisticNumber());
    }

    @GetMapping("/report")
    public Result<List<OpenJobStatisticReportRespDTO>> getStatisticReport() {
        return Result.succeed(crawlerStatisticService.getStatisticReport());
    }
}
