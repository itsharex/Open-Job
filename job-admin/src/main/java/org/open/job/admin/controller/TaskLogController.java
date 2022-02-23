package org.open.job.admin.controller;

import org.open.job.admin.dto.req.TaskLogReqDTO;
import org.open.job.admin.dto.resp.TaskLogRespDTO;
import org.open.job.admin.service.TaskLogService;
import org.open.job.common.vo.PageResult;
import org.open.job.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * 爬虫任务运行日志
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Validated
@RestController
@RequestMapping("/taskLog")
public class TaskLogController {

  @Autowired
  private TaskLogService taskLogService;

  @GetMapping("/page")
  public Result<PageResult<TaskLogRespDTO>> page(TaskLogReqDTO taskLogReqDTO) {
    return Result.succeed(taskLogService.selectPage(taskLogReqDTO));
  }

  @GetMapping("/info/{id}")
  public Result<TaskLogRespDTO> info(@PathVariable("id") Long id) {
    return Result.succeed(taskLogService.getById(id));
  }

  @PutMapping("/delete/{id}")
  public Result<Boolean> delete(@PathVariable("id") Long id) {
    return Result.succeed(taskLogService.deleteById(id));
  }

}
