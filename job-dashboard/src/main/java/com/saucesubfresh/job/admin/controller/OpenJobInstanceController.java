package com.saucesubfresh.job.admin.controller;

import com.saucesubfresh.job.admin.dto.req.OpenJobInstanceReqDTO;
import com.saucesubfresh.job.admin.dto.resp.OpenJobInstanceRespDTO;
import com.saucesubfresh.job.admin.service.OpenJobInstanceService;
import com.saucesubfresh.job.common.vo.PageResult;
import com.saucesubfresh.job.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 集群节点管理端点
 *
 * @author lijunping on 2022/2/16
 */
@Validated
@RestController
@RequestMapping("/instance")
public class OpenJobInstanceController {

    @Autowired
    private OpenJobInstanceService instanceService;

    /**
     * 查询全部客户端示例
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult<OpenJobInstanceRespDTO>> getAllInstance(OpenJobInstanceReqDTO instanceReqDTO){
        return Result.succeed(instanceService.selectPage(instanceReqDTO));
    }

    /**
     * 客户端下线
     * @return
     */
    @PutMapping("/offline/{clientId}")
    public Result<Boolean> offlineInstance(@PathVariable("clientId") String clientId){
        return Result.succeed(instanceService.offlineClient(clientId));
    }

    /**
     * 客户端上线
     * @return
     */
    @PutMapping("/online/{clientId}")
    public Result<Boolean> onlineInstance(@PathVariable("clientId") String clientId){
        return Result.succeed(instanceService.onlineClient(clientId));
    }
}
