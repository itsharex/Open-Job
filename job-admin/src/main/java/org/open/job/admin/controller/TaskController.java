package org.open.job.admin.controller;

import org.open.job.admin.dto.create.TaskCreateDTO;
import org.open.job.admin.dto.req.TaskReqDTO;
import org.open.job.admin.dto.resp.TaskRespDTO;
import org.open.job.admin.dto.update.TaskUpdateDTO;
import org.open.job.admin.service.TaskService;
import org.open.job.common.vo.PageResult;
import org.open.job.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 爬虫任务表
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Validated
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;


    @GetMapping("/page")
    public Result<PageResult<TaskRespDTO>> page(TaskReqDTO TaskReqDTO) {
        return Result.succeed(taskService.selectPage(TaskReqDTO));
    }

    @GetMapping("/info/{id}")
    public Result<TaskRespDTO> info(@PathVariable("id") Long id) {
        return Result.succeed(taskService.getById(id));
    }

    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody @Valid TaskCreateDTO taskCreateDTO) {
        return Result.succeed(taskService.save(taskCreateDTO));
    }

    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody @Valid TaskUpdateDTO TaskUpdateDTO) {
        return Result.succeed(taskService.updateById(TaskUpdateDTO));
    }

    @PutMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        return Result.succeed(taskService.deleteById(id));
    }

    @PutMapping("/start/{id}")
    public Result<Boolean> start(@PathVariable("id") Long id) {
        return Result.succeed(taskService.start(id));
    }

    @PutMapping("/stop/{id}")
    public Result<Boolean> stop(@PathVariable("id") Long id) {
        return Result.succeed(taskService.stop(id));
    }
}
