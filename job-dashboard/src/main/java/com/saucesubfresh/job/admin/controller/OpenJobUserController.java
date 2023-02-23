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
import com.saucesubfresh.job.api.dto.req.OpenJobUserReqDTO;
import com.saucesubfresh.starter.security.context.UserSecurityContextHolder;
import com.saucesubfresh.job.api.dto.create.OpenJobUserCreateDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobUserRespDTO;
import com.saucesubfresh.job.api.dto.update.OpenJobUserUpdateDTO;
import com.saucesubfresh.job.admin.service.OpenJobUserService;
import com.saucesubfresh.job.common.vo.PageResult;
import com.saucesubfresh.job.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * 用户表
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-06-22 15:20:30
 */
@Validated
@RestController
@RequestMapping("/user")
public class OpenJobUserController {

  @Autowired
  private OpenJobUserService openJobUserService;

  @GetMapping("/currentUser")
  public Result<OpenJobUserRespDTO> getCurrentUser() {
    Long userId = UserSecurityContextHolder.getUserId();
    return Result.succeed(openJobUserService.loadUserByUserId(userId));
  }

  @GetMapping("/page")
  public Result<PageResult<OpenJobUserRespDTO>> page(OpenJobUserReqDTO openJobUserReqDTO) {
    return Result.succeed(openJobUserService.selectPage(openJobUserReqDTO));
  }

  @GetMapping("/info/{id}")
  public Result<OpenJobUserRespDTO> info(@PathVariable("id") Long id) {
    return Result.succeed(openJobUserService.getById(id));
  }

  @PostMapping("/save")
  public Result<Boolean> save(@RequestBody @Valid OpenJobUserCreateDTO openJobUserCreateDTO) {
    return Result.succeed(openJobUserService.save(openJobUserCreateDTO));
  }

  @PutMapping("/update")
  public Result<Boolean> update(@RequestBody @Valid OpenJobUserUpdateDTO openJobUserUpdateDTO) {
    return Result.succeed(openJobUserService.updateById(openJobUserUpdateDTO));
  }

  @DeleteMapping("/delete")
  public Result<Boolean> delete(@RequestBody @Valid BatchDTO batchDTO) {
    return Result.succeed(openJobUserService.deleteBatchIds(batchDTO));
  }

}
