package com.saucesubfresh.job.admin.service.impl;

import com.saucesubfresh.job.admin.dto.req.OpenJobInstanceReqDTO;
import com.saucesubfresh.job.admin.dto.resp.OpenJobInstanceRespDTO;
import com.saucesubfresh.job.admin.service.OpenJobInstanceService;
import com.saucesubfresh.rpc.core.information.ClientInformation;
import com.saucesubfresh.rpc.server.discovery.ServiceDiscovery;
import com.saucesubfresh.rpc.server.store.InstanceStore;
import com.saucesubfresh.job.common.time.LocalDateTimeUtil;
import com.saucesubfresh.job.common.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
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
