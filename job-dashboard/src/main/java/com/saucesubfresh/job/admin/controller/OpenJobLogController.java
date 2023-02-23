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

import com.saucesubfresh.job.api.dto.batch.BatchDTO;
import com.saucesubfresh.job.api.dto.req.OpenJobLogReqDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobLogRespDTO;
import com.saucesubfresh.job.admin.service.OpenJobLogService;
import com.saucesubfresh.job.common.vo.PageResult;
import com.saucesubfresh.job.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * 任务运行日志
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Validated
@RestController
@RequestMapping("/logger")
public class OpenJobLogController {

  @Autowired
  private OpenJobLogService openJobLogService;

  @GetMapping("/page")
  public Result<PageResult<OpenJobLogRespDTO>> page(OpenJobLogReqDTO openJobLogReqDTO) {
    return Result.succeed(openJobLogService.selectPage(openJobLogReqDTO));
  }

  @GetMapping("/info/{id}")
  public Result<OpenJobLogRespDTO> info(@PathVariable("id") Long id) {
    return Result.succeed(openJobLogService.getById(id));
  }

  @DeleteMapping("/delete")
  public Result<Boolean> delete(@RequestBody @Valid BatchDTO batchDTO) {
    return Result.succeed(openJobLogService.deleteBatchIds(batchDTO));
  }

}
