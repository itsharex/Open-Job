package com.saucesubfresh.job.admin.service;

import com.saucesubfresh.job.admin.dto.create.OpenJobAppCreateDTO;
import com.saucesubfresh.job.admin.dto.req.OpenJobAppReqDTO;
import com.saucesubfresh.job.admin.dto.resp.OpenJobAppRespDTO;
import com.saucesubfresh.job.admin.dto.update.OpenJobAppUpdateDTO;
import com.saucesubfresh.job.common.vo.PageResult;

import java.util.List;

/**
 * 任务运行日志
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
public interface OpenJobAppService {

    PageResult<OpenJobAppRespDTO> selectPage(OpenJobAppReqDTO openJobAppReqDTO);

    OpenJobAppRespDTO getById(Long id);

    boolean save(OpenJobAppCreateDTO openJobAppCreateDTO);

    boolean updateById(OpenJobAppUpdateDTO openJobAppUpdateDTO);

    boolean deleteById(Long id);

    List<OpenJobAppRespDTO> queryList();
}

