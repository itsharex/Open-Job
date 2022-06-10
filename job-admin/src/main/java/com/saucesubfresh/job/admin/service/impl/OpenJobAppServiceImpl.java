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
import com.saucesubfresh.rpc.server.namespace.NamespaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OpenJobAppServiceImpl extends ServiceImpl<OpenJobAppMapper, OpenJobAppDO> implements OpenJobAppService, NamespaceService {

    @Autowired
    private OpenJobAppMapper openJobAppMapper;

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
    public void save(OpenJobAppCreateDTO openJobAppCreateDTO) {
        openJobAppMapper.insert(OpenJobAppConvert.INSTANCE.convert(openJobAppCreateDTO));
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
    public List<String> loadNamespace() {
        List<OpenJobAppDO> openJobAppDOS = openJobAppMapper.selectList(Wrappers.lambdaQuery());
        if (CollectionUtils.isEmpty(openJobAppDOS)){
            return Collections.emptyList();
        }
        return openJobAppDOS.stream().map(OpenJobAppDO::getAppName).collect(Collectors.toList());
    }
}