package org.open.job.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.open.job.admin.convert.OpenJobLogConvert;
import org.open.job.admin.dto.create.OpenJobLogCreateDTO;
import org.open.job.admin.dto.req.OpenJobLogReqDTO;
import org.open.job.admin.dto.resp.OpenJobLogRespDTO;
import org.open.job.admin.entity.OpenJobLogDO;
import org.open.job.admin.mapper.OpenJobLogMapper;
import org.open.job.admin.event.JobLogEvent;
import org.open.job.admin.service.OpenJobLogService;
import org.open.job.common.enums.CommonStatusEnum;
import org.open.job.common.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


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

    @Override
    public JobLogEvent createLog(Long jobId, String cause) {
        OpenJobLogCreateDTO openJobLogCreateDTO = new OpenJobLogCreateDTO();
        openJobLogCreateDTO.setJobId(jobId);
        openJobLogCreateDTO.setStatus(StringUtils.isBlank(cause) ? CommonStatusEnum.YES.getValue() : CommonStatusEnum.NO.getValue());
        openJobLogCreateDTO.setCause(cause);
        openJobLogCreateDTO.setCreateTime(LocalDateTime.now());
        return new JobLogEvent(this, openJobLogCreateDTO);
    }

}