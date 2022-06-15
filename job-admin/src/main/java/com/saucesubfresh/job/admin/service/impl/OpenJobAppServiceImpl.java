package com.saucesubfresh.job.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saucesubfresh.job.admin.convert.OpenJobAppConvert;
import com.saucesubfresh.job.admin.dto.create.OpenJobAppCreateDTO;
import com.saucesubfresh.job.admin.dto.req.OpenJobAppReqDTO;
import com.saucesubfresh.job.admin.dto.resp.OpenJobAppRespDTO;
import com.saucesubfresh.job.admin.dto.update.OpenJobAppUpdateDTO;
import com.saucesubfresh.job.admin.entity.OpenJobAppDO;
import com.saucesubfresh.job.admin.mapper.OpenJobAppMapper;
import com.saucesubfresh.job.admin.service.OpenJobAppService;
import com.saucesubfresh.job.common.vo.PageResult;
import com.saucesubfresh.rpc.server.discovery.ServiceDiscovery;
import com.saucesubfresh.starter.security.context.UserSecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
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
        return true;
    }

    @Override
    public boolean updateById(OpenJobAppUpdateDTO openJobAppUpdateDTO) {
        openJobAppMapper.updateById(OpenJobAppConvert.INSTANCE.convert(openJobAppUpdateDTO));
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        openJobAppMapper.deleteById(id);
        return true;
    }

    @Override
    public List<OpenJobAppRespDTO> queryList(String appName) {
        List<OpenJobAppDO> openJobAppDOS = openJobAppMapper.queryList(appName);
        return OpenJobAppConvert.INSTANCE.convertList(openJobAppDOS);
    }

    @PostConstruct
    public void startSubscribe() {
        List<OpenJobAppDO> openJobAppDOS = openJobAppMapper.selectList(Wrappers.lambdaQuery());
        if (CollectionUtils.isEmpty(openJobAppDOS)){
            return;
        }
        List<String> namespaces = openJobAppDOS.stream().map(OpenJobAppDO::getAppName).collect(Collectors.toList());
        serviceDiscovery.subscribe(namespaces);
    }
}