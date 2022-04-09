package com.saucesubfresh.job.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saucesubfresh.job.admin.convert.OpenJobLogConvert;
import com.saucesubfresh.job.admin.dto.req.OpenJobLogReqDTO;
import com.saucesubfresh.job.admin.entity.OpenJobLogDO;
import com.saucesubfresh.job.admin.mapper.OpenJobLogMapper;
import com.saucesubfresh.job.admin.service.OpenJobLogService;
import com.saucesubfresh.job.admin.dto.create.OpenJobLogCreateDTO;
import com.saucesubfresh.job.admin.dto.resp.OpenJobLogRespDTO;
import com.saucesubfresh.job.common.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OpenJobLogServiceImpl extends ServiceImpl<OpenJobLogMapper, OpenJobLogDO> implements OpenJobLogService {

    @Autowired
    private OpenJobLogMapper openJobLogMapper;

    @Override
    public PageResult<OpenJobLogRespDTO> selectPage(OpenJobLogReqDTO OpenJobLogReqDTO) {
        Page<OpenJobLogDO> page = openJobLogMapper.queryPage(OpenJobLogReqDTO);
        IPage<OpenJobLogRespDTO> convert = page.convert(OpenJobLogConvert.INSTANCE::convert);
        return PageResult.build(convert);
    }

    @Override
    public OpenJobLogRespDTO getById(Long id) {
        OpenJobLogDO OpenJobLogDO = openJobLogMapper.selectById(id);
        return OpenJobLogConvert.INSTANCE.convert(OpenJobLogDO);
    }

    @Override
    public void save(OpenJobLogCreateDTO OpenJobLogCreateDTO) {
        openJobLogMapper.insert(OpenJobLogConvert.INSTANCE.convert(OpenJobLogCreateDTO));
    }

    @Override
    public boolean deleteById(Long id) {
        openJobLogMapper.deleteById(id);
        return true;
    }
}