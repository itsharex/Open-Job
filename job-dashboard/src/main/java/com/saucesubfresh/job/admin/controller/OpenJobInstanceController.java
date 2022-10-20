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

import com.saucesubfresh.job.api.dto.req.OpenJobInstanceReqDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobInstanceRespDTO;
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
