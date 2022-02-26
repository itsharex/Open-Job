package org.open.job.admin.controller;

import org.open.job.admin.dto.req.OpenJobLogReqDTO;
import org.open.job.admin.dto.resp.OpenJobLogRespDTO;
import org.open.job.admin.service.OpenJobLogService;
import org.open.job.common.vo.PageResult;
import org.open.job.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 任务运行日志
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Validated
@RestController
@RequestMapping("/openJobLog")
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

  @PutMapping("/delete/{id}")
  public Result<Boolean> delete(@PathVariable("id") Long id) {
    return Result.succeed(openJobLogService.deleteById(id));
  }

}