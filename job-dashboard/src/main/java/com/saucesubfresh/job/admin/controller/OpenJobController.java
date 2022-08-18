package com.saucesubfresh.job.admin.controller;

import com.saucesubfresh.job.api.dto.req.OpenJobReqDTO;
import com.saucesubfresh.job.api.dto.create.OpenJobCreateDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobRespDTO;
import com.saucesubfresh.job.api.dto.update.OpenJobUpdateDTO;
import com.saucesubfresh.job.admin.service.OpenJobService;
import com.saucesubfresh.job.common.vo.PageResult;
import com.saucesubfresh.job.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 任务表
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Validated
@RestController
@RequestMapping("/task")
public class OpenJobController {

    @Autowired
    private OpenJobService openJobService;

    @GetMapping("/page")
    public Result<PageResult<OpenJobRespDTO>> page(OpenJobReqDTO OpenJobReqDTO) {
        return Result.succeed(openJobService.selectPage(OpenJobReqDTO));
    }

    @GetMapping("/info/{id}")
    public Result<OpenJobRespDTO> info(@PathVariable("id") Long id) {
        return Result.succeed(openJobService.getById(id));
    }

    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody @Valid OpenJobCreateDTO openJobCreateDTO) {
        return Result.succeed(openJobService.save(openJobCreateDTO));
    }

    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody @Valid OpenJobUpdateDTO OpenJobUpdateDTO) {
        return Result.succeed(openJobService.updateById(OpenJobUpdateDTO));
    }

    @PutMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        return Result.succeed(openJobService.deleteById(id));
    }

    @PutMapping("/start/{id}")
    public Result<Boolean> start(@PathVariable("id") Long id) {
        return Result.succeed(openJobService.start(id));
    }

    @PutMapping("/stop/{id}")
    public Result<Boolean> stop(@PathVariable("id") Long id) {
        return Result.succeed(openJobService.stop(id));
    }

    @PutMapping("/run/{id}")
    public Result<Boolean> run(@PathVariable("id") Long id) {
        return Result.succeed(openJobService.run(id));
    }

    @GetMapping("/nextTriggerTime")
    public Result<List<String>> nextTriggerTime(@RequestParam("cronExpress") String cronExpress){
        return Result.succeed(openJobService.nextTriggerTime(cronExpress));
    }

    @GetMapping("/validateCron")
    public Result<String> validateCron(@RequestParam("cronExpress") String cronExpress){
        return Result.succeed(openJobService.validateCron(cronExpress));
    }
}
