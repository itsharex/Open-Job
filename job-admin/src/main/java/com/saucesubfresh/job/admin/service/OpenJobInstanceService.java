package com.saucesubfresh.job.admin.service;

import com.saucesubfresh.job.admin.dto.req.OpenJobInstanceReqDTO;
import com.saucesubfresh.job.admin.dto.resp.OpenJobInstanceRespDTO;
import com.saucesubfresh.job.common.vo.PageResult;

/**
 * @author: 李俊平
 * @Date: 2022-02-26 15:05
 */
public interface OpenJobInstanceService {
    
    PageResult<OpenJobInstanceRespDTO> selectPage(OpenJobInstanceReqDTO instanceReqDTO);

    Boolean offlineClient(String clientId);

    Boolean onlineClient(String clientId);
}
