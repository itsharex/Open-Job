package com.saucesubfresh.job.admin.controller;

import com.saucesubfresh.job.admin.dto.req.OpenJobAppReqDTO;
import com.saucesubfresh.job.admin.dto.resp.OpenJobAppRespDTO;
import com.saucesubfresh.job.admin.dto.update.OpenJobAppUpdateDTO;
import com.saucesubfresh.job.admin.service.OpenJobAppService;
import com.saucesubfresh.job.common.vo.PageResult;
import com.saucesubfresh.job.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


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

  @GetMapping("/info/{id}")
  public Result<OpenJobAppRespDTO> info(@PathVariable("id") Long id) {
    return Result.succeed(openJobAppService.getById(id));
  }

  @PutMapping("/update")
  public Result<Boolean> update(@RequestBody @Valid OpenJobAppUpdateDTO openJobAppUpdateDTO) {
    return Result.succeed(openJobAppService.updateById(openJobAppUpdateDTO));
  }

  @PutMapping("/delete/{id}")
  public Result<Boolean> delete(@PathVariable("id") Long id) {
    return Result.succeed(openJobAppService.deleteById(id));
  }

}
