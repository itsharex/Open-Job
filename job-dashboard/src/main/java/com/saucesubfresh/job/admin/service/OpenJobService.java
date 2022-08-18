package com.saucesubfresh.job.admin.service;

import com.saucesubfresh.job.api.dto.req.OpenJobReqDTO;
import com.saucesubfresh.job.api.dto.create.OpenJobCreateDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobRespDTO;
import com.saucesubfresh.job.api.dto.update.OpenJobUpdateDTO;
import com.saucesubfresh.job.common.vo.PageResult;

import java.util.List;

/**
 * 任务表
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
public interface OpenJobService {

    PageResult<OpenJobRespDTO> selectPage(OpenJobReqDTO OpenJobReqDTO);

    OpenJobRespDTO getById(Long id);

    boolean save(OpenJobCreateDTO OpenJobCreateDTO);

    boolean updateById(OpenJobUpdateDTO OpenJobUpdateDTO);

    boolean start(Long id);

    boolean stop(Long id);

    boolean deleteById(Long id);

    boolean run(Long id);

    List<String> nextTriggerTime(String cronExpress);

    String validateCron(String cronExpress);
}
