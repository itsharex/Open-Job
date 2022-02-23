package org.open.job.admin.service;

import org.open.job.admin.dto.create.TaskCreateDTO;
import org.open.job.admin.dto.req.TaskReqDTO;
import org.open.job.admin.dto.resp.TaskRespDTO;
import org.open.job.admin.dto.update.TaskUpdateDTO;
import org.open.job.common.vo.PageResult;

import java.util.List;

/**
 * 爬虫任务表
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
public interface TaskService {

    PageResult<TaskRespDTO> selectPage(TaskReqDTO TaskReqDTO);

    TaskRespDTO getById(Long id);

    List<Long> getSpiderIdList(List<Long> taskList);

    boolean save(TaskCreateDTO TaskCreateDTO);

    boolean updateById(TaskUpdateDTO TaskUpdateDTO);

    boolean start(Long id);

    boolean stop(Long id);

    boolean deleteById(Long id);
}
