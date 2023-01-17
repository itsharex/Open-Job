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
package com.saucesubfresh.job.admin.service.impl;

import com.saucesubfresh.job.api.dto.req.OpenJobInstanceReqDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobAppRespDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobInstanceRespDTO;
import com.saucesubfresh.job.admin.service.OpenJobAppService;
import com.saucesubfresh.job.admin.service.OpenJobInstanceService;
import com.saucesubfresh.job.common.time.LocalDateTimeUtil;
import com.saucesubfresh.job.common.vo.PageResult;
import com.saucesubfresh.rpc.client.manager.InstanceManager;
import com.saucesubfresh.rpc.client.store.InstanceStore;
import com.saucesubfresh.rpc.core.information.ServerInformation;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李俊平
 * @Date: 2022-02-26 15:06
 */
@Service
public class OpenJobInstanceServiceImpl implements OpenJobInstanceService {

    private final InstanceStore instanceStore;
    private final InstanceManager instanceManager;
    private final OpenJobAppService openJobAppService;

    public OpenJobInstanceServiceImpl(InstanceStore instanceStore, InstanceManager instanceManager, OpenJobAppService openJobAppService) {
        this.instanceStore = instanceStore;
        this.instanceManager = instanceManager;
        this.openJobAppService = openJobAppService;
    }

    @Override
    public PageResult<OpenJobInstanceRespDTO> selectPage(OpenJobInstanceReqDTO instanceReqDTO) {
        final OpenJobAppRespDTO openJobApp = openJobAppService.getById(instanceReqDTO.getAppId());
        List<ServerInformation> instances = instanceStore.getByNamespace(openJobApp.getAppName());
        List<OpenJobInstanceRespDTO> jobInstance = convertList(instances);
        return PageResult.<OpenJobInstanceRespDTO>newBuilder()
                .records(jobInstance)
                .total((long) jobInstance.size())
                .current(1L)
                .pages((long) (jobInstance.size() / 10))
                .build();
    }

    @Override
    public Boolean offlineClient(String clientId) {
        return instanceManager.offlineServer(clientId);
    }

    @Override
    public Boolean onlineClient(String clientId) {
        return instanceManager.offlineServer(clientId);
    }

    @Override
    public List<OpenJobInstanceRespDTO> getInstanceList(Long appId) {
        final OpenJobAppRespDTO openJobApp = openJobAppService.getById(appId);
        List<ServerInformation> instances = instanceStore.getByNamespace(openJobApp.getAppName());
        return convertList(instances);
    }

    private List<OpenJobInstanceRespDTO> convertList(List<ServerInformation> instances) {
        if (CollectionUtils.isEmpty(instances)){
            return new ArrayList<>();
        }

        return instances.stream().map(e->{
            OpenJobInstanceRespDTO instance = new OpenJobInstanceRespDTO();
            instance.setClientId(e.getServerId());
            LocalDateTime localDateTime = LocalDateTimeUtil.toLocalDateTime(e.getOnlineTime());
            instance.setOnlineTime(localDateTime);
            instance.setStatus(e.getStatus().name());
            instance.setWeight(e.getWeight());
            return instance;
        }).collect(Collectors.toList());
    }
}
