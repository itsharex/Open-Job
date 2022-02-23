package org.open.job.admin.controller;

import org.open.job.common.vo.PageResult;
import org.open.job.common.vo.Result;
import org.open.job.core.information.ClientInformation;
import org.open.job.starter.server.discovery.ServiceDiscovery;
import org.open.job.starter.server.store.InstanceStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 集群节点管理端点
 *
 * @author lijunping on 2022/2/16
 */
@Validated
@RestController
@RequestMapping("/instanceManager")
public class InstanceController {

    @Autowired(required = false)
    private ServiceDiscovery serviceDiscovery;

    @Autowired
    private InstanceStore instanceStore;

    /**
     * 查询全部客户端示例
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult<ClientInformation>> testGetAllInstance(){
        final List<ClientInformation> instances = instanceStore.getAll();
        PageResult<ClientInformation> pageResult = PageResult.<ClientInformation>newBuilder()
                .records(CollectionUtils.isEmpty(instances) ? null : instances)
                .total((long) instances.size())
                .current(1L)
                .pages((long) (instances.size() / 10))
                .build();
        return Result.succeed(pageResult);
    }

    /**
     * 客户端下线
     * @return
     */
    @PutMapping("/offline/{clientId}")
    public Result<Boolean> testOfflineInstance(@PathVariable("clientId") String clientId){
        final boolean result = serviceDiscovery.offlineClient(clientId);
        return Result.succeed(result);
    }

    /**
     * 客户端上线
     * @return
     */
    @PutMapping("/online/{clientId}")
    public Result<Boolean> testOnlineInstance(@PathVariable("clientId") String clientId){
        final boolean result = serviceDiscovery.onlineClient(clientId);
        return Result.succeed(result);
    }
}
