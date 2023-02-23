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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saucesubfresh.job.admin.convert.OpenJobAppConvert;
import com.saucesubfresh.job.api.dto.batch.BatchDTO;
import com.saucesubfresh.job.api.dto.create.OpenJobAppCreateDTO;
import com.saucesubfresh.job.api.dto.req.OpenJobAppReqDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobAppRespDTO;
import com.saucesubfresh.job.api.dto.update.OpenJobAppUpdateDTO;
import com.saucesubfresh.job.admin.entity.OpenJobAppDO;
import com.saucesubfresh.job.admin.mapper.OpenJobAppMapper;
import com.saucesubfresh.job.admin.service.OpenJobAppService;
import com.saucesubfresh.job.common.vo.PageResult;
import com.saucesubfresh.rpc.client.discovery.ServiceDiscovery;
import com.saucesubfresh.starter.security.context.UserSecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OpenJobAppServiceImpl extends ServiceImpl<OpenJobAppMapper, OpenJobAppDO> implements OpenJobAppService{

    private final OpenJobAppMapper openJobAppMapper;
    private final ServiceDiscovery serviceDiscovery;

    public OpenJobAppServiceImpl(OpenJobAppMapper openJobAppMapper, ServiceDiscovery serviceDiscovery) {
        this.openJobAppMapper = openJobAppMapper;
        this.serviceDiscovery = serviceDiscovery;
    }

    @Override
    public PageResult<OpenJobAppRespDTO> selectPage(OpenJobAppReqDTO openJobAppReqDTO) {
        Page<OpenJobAppDO> page = openJobAppMapper.queryPage(openJobAppReqDTO);
        IPage<OpenJobAppRespDTO> convert = page.convert(OpenJobAppConvert.INSTANCE::convert);
        return PageResult.build(convert);
    }

    @Override
    public OpenJobAppRespDTO getById(Long id) {
        OpenJobAppDO openJobAppDO = openJobAppMapper.selectById(id);
        return OpenJobAppConvert.INSTANCE.convert(openJobAppDO);
    }

    @Override
    public boolean save(OpenJobAppCreateDTO openJobAppCreateDTO) {
        OpenJobAppDO openJobAppDO = OpenJobAppConvert.INSTANCE.convert(openJobAppCreateDTO);
        openJobAppDO.setCreateTime(LocalDateTime.now());
        openJobAppDO.setCreateUser(UserSecurityContextHolder.getUserId());
        openJobAppMapper.insert(openJobAppDO);
        this.reSubscribe();
        return true;
    }

    @Override
    public boolean updateById(OpenJobAppUpdateDTO openJobAppUpdateDTO) {
        openJobAppMapper.updateById(OpenJobAppConvert.INSTANCE.convert(openJobAppUpdateDTO));
        this.reSubscribe();
        return true;
    }

    @Override
    public boolean deleteBatchIds(BatchDTO batchDTO) {
        openJobAppMapper.deleteBatchIds(batchDTO.getIds());
        this.reSubscribe();
        return true;
    }

    @Override
    public List<OpenJobAppRespDTO> queryList(String appName) {
        List<OpenJobAppDO> openJobAppDOS = openJobAppMapper.queryList(appName);
        return OpenJobAppConvert.INSTANCE.convertList(openJobAppDOS);
    }

    /**
     * 当修改了服务名称之后重新监听
     */
    private void reSubscribe() {
        List<OpenJobAppDO> openJobAppDOS = openJobAppMapper.selectList(Wrappers.lambdaQuery());
        if (CollectionUtils.isEmpty(openJobAppDOS)){
            return;
        }
        List<String> namespaces = openJobAppDOS.stream().map(OpenJobAppDO::getAppName).collect(Collectors.toList());
        serviceDiscovery.subscribe(namespaces);
    }
}