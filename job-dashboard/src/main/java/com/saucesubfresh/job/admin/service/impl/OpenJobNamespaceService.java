package com.saucesubfresh.job.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.saucesubfresh.job.admin.entity.OpenJobAppDO;
import com.saucesubfresh.job.admin.mapper.OpenJobAppMapper;
import com.saucesubfresh.rpc.client.namespace.NamespaceService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lijunping on 2022/8/18
 */
@Component
public class OpenJobNamespaceService implements NamespaceService {

    private final OpenJobAppMapper openJobAppMapper;

    public OpenJobNamespaceService(OpenJobAppMapper openJobAppMapper) {
        this.openJobAppMapper = openJobAppMapper;
    }

    @Override
    public List<String> loadNamespace() {
        List<OpenJobAppDO> openJobAppDOS = openJobAppMapper.selectList(Wrappers.lambdaQuery());
        if (CollectionUtils.isEmpty(openJobAppDOS)){
            return Collections.emptyList();
        }
        return openJobAppDOS.stream().map(OpenJobAppDO::getAppName).collect(Collectors.toList());
    }
}
