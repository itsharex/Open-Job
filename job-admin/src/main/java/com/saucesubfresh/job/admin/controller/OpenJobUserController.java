package com.saucesubfresh.job.admin.controller;

import com.saucesubfresh.job.admin.dto.req.OpenJobUserReqDTO;
import com.saucesubfresh.starter.security.context.UserSecurityContextHolder;
import com.saucesubfresh.job.admin.dto.create.OpenJobUserCreateDTO;
import com.saucesubfresh.job.admin.dto.resp.OpenJobUserRespDTO;
import com.saucesubfresh.job.admin.dto.update.OpenJobUserUpdateDTO;
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

  @PutMapping("/delete/{id}")
  public Result<Boolean> delete(@PathVariable("id") Long id) {
    return Result.succeed(openJobUserService.deleteById(id));
  }

}
