package org.open.job.admin.service;


import org.open.job.admin.dto.create.OpenJobLogCreateDTO;
import org.open.job.admin.dto.req.OpenJobLogReqDTO;
import org.open.job.admin.dto.resp.OpenJobLogRespDTO;
import org.open.job.admin.event.JobLogEvent;
import org.open.job.common.vo.PageResult;

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

