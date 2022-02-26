package org.open.job.admin.service.impl;

import org.open.job.admin.dto.req.OpenJobInstanceReqDTO;
import org.open.job.admin.dto.resp.OpenJobInstanceRespDTO;
import org.open.job.admin.service.OpenJobInstanceService;
import org.open.job.common.time.LocalDateTimeUtil;
import org.open.job.common.vo.PageResult;
import org.open.job.core.information.ClientInformation;
import org.open.job.starter.server.discovery.ServiceDiscovery;
import org.open.job.starter.server.store.InstanceStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李俊平
 * @Date: 2022-02-26 15:06
 */
@Service
public class OpenJobInstanceServiceImpl implements OpenJobInstanceService {

    @Autowired(required = false)
    private ServiceDiscovery serviceDiscovery;

    @Autowired
    private InstanceStore instanceStore;

    @Override
    public PageResult<OpenJobInstanceRespDTO> selectPage(OpenJobInstanceReqDTO instanceReqDTO) {
        List<ClientInformation> instances = instanceStore.getAll();
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
        return serviceDiscovery.offlineClient(clientId);
    }

    @Override
    public Boolean onlineClient(String clientId) {
        return serviceDiscovery.onlineClient(clientId);
    }

    private List<OpenJobInstanceRespDTO> convertList(List<ClientInformation> instances) {
        if (CollectionUtils.isEmpty(instances)){
            return new ArrayList<>();
        }

        return instances.stream().map(e->{
            OpenJobInstanceRespDTO instance = new OpenJobInstanceRespDTO();
            instance.setClientId(e.getClientId());
            LocalDateTime localDateTime = LocalDateTimeUtil.toLocalDateTime(e.getOnlineTime());
            instance.setOnlineTime(localDateTime);
            instance.setStatus(e.getStatus());
            instance.setWeight(e.getWeight());
            return instance;
        }).collect(Collectors.toList());
    }
}
