package org.open.job.admin.service;

import org.open.job.admin.dto.create.OpenJobCreateDTO;
import org.open.job.admin.dto.req.OpenJobReqDTO;
import org.open.job.admin.dto.resp.OpenJobRespDTO;
import org.open.job.admin.dto.update.OpenJobUpdateDTO;
import org.open.job.common.vo.PageResult;

import java.util.List;

/**
 * 爬虫任务表
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
public interface OpenJobService {

    PageResult<OpenJobRespDTO> selectPage(OpenJobReqDTO OpenJobReqDTO);

    OpenJobRespDTO getById(Long id);

    List<OpenJobRespDTO> selectList(List<Long> taskList);

    boolean save(OpenJobCreateDTO OpenJobCreateDTO);

    boolean updateById(OpenJobUpdateDTO OpenJobUpdateDTO);

    boolean start(Long id);

    boolean stop(Long id);

    boolean deleteById(Long id);
}
