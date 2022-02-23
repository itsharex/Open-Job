package org.open.job.admin.service;


import org.open.job.admin.dto.create.TaskLogCreateDTO;
import org.open.job.admin.dto.req.TaskLogReqDTO;
import org.open.job.admin.dto.resp.TaskLogRespDTO;
import org.open.job.admin.dto.update.TaskLogUpdateDTO;
import org.open.job.common.vo.PageResult;

/**
 * 爬虫任务运行日志
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
public interface TaskLogService {

    PageResult<TaskLogRespDTO> selectPage(TaskLogReqDTO TaskLogReqDTO);

    TaskLogRespDTO getById(Long id);

    boolean save(TaskLogCreateDTO TaskLogCreateDTO);

    boolean updateById(TaskLogUpdateDTO TaskLogUpdateDTO);

    boolean deleteById(Long id);

}

