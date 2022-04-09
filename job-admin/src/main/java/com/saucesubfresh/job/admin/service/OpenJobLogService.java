package com.saucesubfresh.job.admin.service;


import com.saucesubfresh.job.admin.dto.req.OpenJobLogReqDTO;
import com.saucesubfresh.job.admin.dto.create.OpenJobLogCreateDTO;
import com.saucesubfresh.job.admin.dto.resp.OpenJobLogRespDTO;
import com.saucesubfresh.job.common.vo.PageResult;

/**
 * 任务运行日志
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
public interface OpenJobLogService {

    PageResult<OpenJobLogRespDTO> selectPage(OpenJobLogReqDTO OpenJobLogReqDTO);

    OpenJobLogRespDTO getById(Long id);

    void save(OpenJobLogCreateDTO OpenJobLogCreateDTO);

    boolean deleteById(Long id);
}

