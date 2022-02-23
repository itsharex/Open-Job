package org.open.job.admin.controller;

import org.open.job.admin.dto.create.UserCreateDTO;
import org.open.job.admin.dto.req.UserReqDTO;
import org.open.job.admin.dto.resp.UserRespDTO;
import org.open.job.admin.dto.update.UserUpdateDTO;
import org.open.job.admin.service.UserService;
import org.open.job.common.vo.PageResult;
import org.open.job.common.vo.Result;
import org.open.job.starter.security.context.UserSecurityContextHolder;
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
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/currentUser")
  public Result<UserRespDTO> getCurrentUser() {
    Long userId = UserSecurityContextHolder.getUserId();
    return Result.succeed(userService.loadUserByUserId(userId));
  }

  @GetMapping("/page")
  public Result<PageResult<UserRespDTO>> page(UserReqDTO userReqDTO) {
    return Result.succeed(userService.selectPage(userReqDTO));
  }

  @GetMapping("/info/{id}")
  public Result<UserRespDTO> info(@PathVariable("id") Long id) {
    return Result.succeed(userService.getById(id));
  }

  @PostMapping("/save")
  public Result<Boolean> save(@RequestBody @Valid UserCreateDTO userCreateDTO) {
    return Result.succeed(userService.save(userCreateDTO));
  }

  @PutMapping("/update")
  public Result<Boolean> update(@RequestBody @Valid UserUpdateDTO userUpdateDTO) {
    return Result.succeed(userService.updateById(userUpdateDTO));
  }

  @PutMapping("/delete/{id}")
  public Result<Boolean> delete(@PathVariable("id") Long id) {
    return Result.succeed(userService.deleteById(id));
  }

}
