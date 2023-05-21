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
import com.saucesubfresh.job.common.domain.MessageBody;
import com.saucesubfresh.job.common.domain.ResponseBody;
import com.saucesubfresh.job.common.enums.CommandEnum;
import com.saucesubfresh.job.common.exception.ServiceException;
import com.saucesubfresh.job.common.json.JSON;
import com.saucesubfresh.job.common.metrics.SystemMetricsInfo;
import com.saucesubfresh.job.common.serialize.SerializationUtils;
import com.saucesubfresh.job.common.time.LocalDateTimeUtil;
import com.saucesubfresh.job.common.vo.PageResult;
import com.saucesubfresh.rpc.client.manager.InstanceManager;
import com.saucesubfresh.rpc.client.remoting.RemotingInvoker;
import com.saucesubfresh.rpc.client.store.InstanceStore;
import com.saucesubfresh.rpc.core.Message;
import com.saucesubfresh.rpc.core.constants.CommonConstant;
import com.saucesubfresh.rpc.core.enums.ResponseStatus;
import com.saucesubfresh.rpc.core.enums.Status;
import com.saucesubfresh.rpc.core.exception.RpcException;
import com.saucesubfresh.rpc.core.information.ServerInformation;
import com.saucesubfresh.rpc.core.transport.MessageResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: 李俊平
 * @Date: 2022-02-26 15:06
 */
@Slf4j
@Service
public class OpenJobInstanceServiceImpl implements OpenJobInstanceService {

    /**
     *  12.3%(4 cores)
     */
    private static final String CPU_FORMAT = "%s / %s cores";
    /**
     *  27.7%(2.9/8.0 GB)
     */
    private static final String MEMORY_FORMAT = "%s%%（%s / %s GB）";

    private static final DecimalFormat df = new DecimalFormat("#.#");
    private final InstanceStore instanceStore;
    private final InstanceManager instanceManager;
    private final OpenJobAppService openJobAppService;
    private final RemotingInvoker remotingInvoker;

    public OpenJobInstanceServiceImpl(InstanceStore instanceStore,
                                      InstanceManager instanceManager,
                                      OpenJobAppService openJobAppService,
                                      RemotingInvoker remotingInvoker) {
        this.instanceStore = instanceStore;
        this.instanceManager = instanceManager;
        this.openJobAppService = openJobAppService;
        this.remotingInvoker = remotingInvoker;
    }

    @Override
    public PageResult<OpenJobInstanceRespDTO> selectPage(OpenJobInstanceReqDTO instanceReqDTO) {
        Long appId = instanceReqDTO.getAppId();
        OpenJobAppRespDTO openJobApp = openJobAppService.getById(appId);
        List<ServerInformation> instances = instanceStore.getByNamespace(openJobApp.getAppName());
        List<OpenJobInstanceRespDTO> jobInstance = convertList(instances);
        if (CollectionUtils.isEmpty(jobInstance)){
            return PageResult.<OpenJobInstanceRespDTO>newBuilder().build();
        }

        jobInstance.sort(Comparator.comparing(OpenJobInstanceRespDTO::getOnlineTime).reversed());

        int totalSize = jobInstance.size();
        Integer pageNum = instanceReqDTO.getCurrent();
        Integer pageSize = instanceReqDTO.getPageSize();

        //进行分页处理
        if ((pageNum -1) * pageSize >= totalSize){
            return PageResult.<OpenJobInstanceRespDTO>newBuilder().build();
        }
        int endIndex = Math.min(totalSize, pageNum * pageSize);

        List<OpenJobInstanceRespDTO> respDTOS = new ArrayList<>();
        for (int i = (pageNum -1) * pageSize; i < endIndex; i++) {
            respDTOS.add(jobInstance.get(i));
        }

        for (OpenJobInstanceRespDTO instance : respDTOS) {
            doWrapper(instance);
        }

        return PageResult.build(jobInstance, jobInstance.size(), instanceReqDTO.getCurrent(), instanceReqDTO.getPageSize());
    }

    @Override
    public OpenJobInstanceRespDTO getInstanceById(Long appId, String serverId) {
        OpenJobAppRespDTO openJobApp = openJobAppService.getById(appId);
        List<ServerInformation> instances = instanceStore.getByNamespace(openJobApp.getAppName());
        if (CollectionUtils.isEmpty(instances)){
            return null;
        }

        ServerInformation serverInformation = instances.stream()
                .filter(e -> StringUtils.equals(e.getServerId(), serverId))
                .findFirst()
                .orElse(null);

        if (Objects.isNull(serverInformation)){
            return null;
        }

        List<OpenJobInstanceRespDTO> openJobInstanceRespDTOS = convertList(Collections.singletonList(serverInformation));
        OpenJobInstanceRespDTO openJobInstanceRespDTO = openJobInstanceRespDTOS.get(0);
        doWrapper(openJobInstanceRespDTO);
        return openJobInstanceRespDTO;
    }

    @Override
    public Boolean offlineServer(String serverId) {
        try {
            return instanceManager.offlineServer(serverId);
        }catch (Exception e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Boolean onlineServer(String serverId) {
        try {
            return instanceManager.offlineServer(serverId);
        }catch (Exception e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<OpenJobInstanceRespDTO> getInstanceList(Long appId) {
        final OpenJobAppRespDTO openJobApp = openJobAppService.getById(appId);
        List<ServerInformation> instances = instanceStore.getByNamespace(openJobApp.getAppName());
        return convertList(instances);
    }

    private MessageResponseBody doInvoke(Message message, ServerInformation serverInformation){
        MessageResponseBody response;
        try {
            response = remotingInvoker.invoke(message, serverInformation);
        }catch (RpcException e){
            throw new RpcException(e.getMessage());
        }
        if (Objects.nonNull(response) && response.getStatus() != ResponseStatus.SUCCESS){
            throw new RpcException("处理失败");
        }
        return response;
    }

    private List<OpenJobInstanceRespDTO> convertList(List<ServerInformation> instances) {
        if (CollectionUtils.isEmpty(instances)){
            return new ArrayList<>();
        }

        return instances.stream().map(e->{
            OpenJobInstanceRespDTO instance = new OpenJobInstanceRespDTO();
            instance.setServerId(e.getServerId());
            LocalDateTime localDateTime = LocalDateTimeUtil.toLocalDateTime(e.getOnlineTime());
            instance.setOnlineTime(localDateTime);
            if (e.getStatus() == Status.ON_LINE){
                LocalDateTime now = LocalDateTime.now();
                instance.setLiveTime(LocalDateTimeUtil.getTimeBetween(localDateTime, now));
            }
            instance.setStatus(e.getStatus().name());
            instance.setWeight(e.getWeight());
            return instance;
        }).collect(Collectors.toList());
    }

    private void doWrapper(OpenJobInstanceRespDTO instance){
        Message message = new Message();
        MessageBody messageBody = new MessageBody();
        messageBody.setCommand(CommandEnum.METRICS.getValue());
        message.setBody(SerializationUtils.serialize(messageBody));
        String[] serverIdArray = instance.getServerId().split(CommonConstant.Symbol.MH);
        ServerInformation serverInformation = new ServerInformation(serverIdArray[0], Integer.parseInt(serverIdArray[1]));

        MessageResponseBody messageResponseBody;
        try {
            messageResponseBody = doInvoke(message, serverInformation);
        }catch (RpcException ex){
            log.error(ex.getMessage(), ex);
            return;
        }

        byte[] body = messageResponseBody.getBody();
        ResponseBody response = SerializationUtils.deserialize(body, ResponseBody.class);
        if (StringUtils.isBlank(response.getData())){
            return;
        }

        SystemMetricsInfo metricsInfo = JSON.parse(response.getData(), SystemMetricsInfo.class);
        wrapperMetricsInfo(instance, metricsInfo);
    }

    private void wrapperMetricsInfo(OpenJobInstanceRespDTO instance, SystemMetricsInfo metricsInfo){
        if (Objects.isNull(metricsInfo)){
            return;
        }
        // CPU 指标
        String cpuInfo = String.format(CPU_FORMAT, df.format(metricsInfo.getCpuLoad()), metricsInfo.getCpuProcessors());
        // 内存指标
        String menU = df.format(metricsInfo.getJvmMemoryUsage() * 100);
        String menUsed = df.format(metricsInfo.getJvmUsedMemory());
        String menMax = df.format(metricsInfo.getJvmMaxMemory());
        String memoryInfo = String.format(MEMORY_FORMAT, menU, menUsed, menMax);
        // 磁盘指标
        String diskU = df.format(metricsInfo.getDiskUsage() * 100);
        String diskUsed = df.format(metricsInfo.getDiskUsed());
        String diskMax = df.format(metricsInfo.getDiskTotal());
        String diskInfo = String.format(MEMORY_FORMAT, diskU, diskUsed, diskMax);

        instance.setCpuInfo(cpuInfo);
        instance.setMemoryInfo(memoryInfo);
        instance.setDiskInfo(diskInfo);
    }

}
