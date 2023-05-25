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

import com.saucesubfresh.job.admin.service.OpenJobAlarmRecordService;
import com.saucesubfresh.job.api.dto.batch.BatchDTO;
import com.saucesubfresh.job.api.dto.req.OpenJobAlarmRecordReqDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobAlarmRecordRespDTO;
import com.saucesubfresh.job.common.vo.PageResult;
import com.saucesubfresh.job.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author: 李俊平
 * @Date: 2023-05-25 07:44
 */
@Validated
@RestController
@RequestMapping("/alarm")
public class OpenJobAlarmRecordController {

    @Autowired
    private OpenJobAlarmRecordService alarmRecordService;

    @GetMapping("/page")
    public Result<PageResult<OpenJobAlarmRecordRespDTO>> page(OpenJobAlarmRecordReqDTO openJobAlarmRecordReqDTO) {
        return Result.succeed(alarmRecordService.selectPage(openJobAlarmRecordReqDTO));
    }

    @GetMapping("/info/{id}")
    public Result<OpenJobAlarmRecordRespDTO> info(@PathVariable("id") Long id) {
        return Result.succeed(alarmRecordService.getById(id));
    }

    @DeleteMapping("/delete")
    public Result<Boolean> delete(@RequestBody @Valid BatchDTO batchDTO) {
        return Result.succeed(alarmRecordService.deleteBatchIds(batchDTO));
    }
}
