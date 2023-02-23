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
import com.saucesubfresh.job.api.dto.create.OpenJobAppCreateDTO;
import com.saucesubfresh.job.api.dto.req.OpenJobAppReqDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobAppRespDTO;
import com.saucesubfresh.job.api.dto.update.OpenJobAppUpdateDTO;
import com.saucesubfresh.job.admin.service.OpenJobAppService;
import com.saucesubfresh.job.common.vo.PageResult;
import com.saucesubfresh.job.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * app
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Validated
@RestController
@RequestMapping("/app")
public class OpenJobAppController {

  @Autowired
  private OpenJobAppService openJobAppService;

  @GetMapping("/page")
  public Result<PageResult<OpenJobAppRespDTO>> page(OpenJobAppReqDTO openJobAppReqDTO) {
    return Result.succeed(openJobAppService.selectPage(openJobAppReqDTO));
  }

  @GetMapping("/list")
  public Result<List<OpenJobAppRespDTO>> list(@RequestParam(required = false) String appName) {
    return Result.succeed(openJobAppService.queryList(appName));
  }

  @GetMapping("/info/{id}")
  public Result<OpenJobAppRespDTO> info(@PathVariable("id") Long id) {
    return Result.succeed(openJobAppService.getById(id));
  }

  @PostMapping("/save")
  public Result<Boolean> save(@RequestBody @Valid OpenJobAppCreateDTO openJobAppCreateDTO) {
    return Result.succeed(openJobAppService.save(openJobAppCreateDTO));
  }

  @PutMapping("/update")
  public Result<Boolean> update(@RequestBody @Valid OpenJobAppUpdateDTO openJobAppUpdateDTO) {
    return Result.succeed(openJobAppService.updateById(openJobAppUpdateDTO));
  }

  @DeleteMapping("/delete")
  public Result<Boolean> delete(@RequestBody @Valid BatchDTO batchDTO) {
    return Result.succeed(openJobAppService.deleteBatchIds(batchDTO));
  }

}
