package org.open.job.admin.service;

import org.open.job.admin.dto.req.OpenJobInstanceReqDTO;
import org.open.job.admin.dto.resp.OpenJobInstanceRespDTO;
import org.open.job.common.vo.PageResult;

/**
 * @author: 李俊平
 * @Date: 2022-02-26 15:05
 */
public interface OpenJobInstanceService {
    
    PageResult<OpenJobInstanceRespDTO> selectPage(OpenJobInstanceReqDTO instanceReqDTO);

    Boolean offlineClient(String clientId);

    Boolean onlineClient(String clientId);
}
