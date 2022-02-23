package org.open.job.admin.convert;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.open.job.admin.dto.create.TaskLogCreateDTO;
import org.open.job.admin.dto.resp.TaskLogRespDTO;
import org.open.job.admin.dto.update.TaskLogUpdateDTO;
import org.open.job.admin.entity.TaskLogDO;

/**
 * 爬虫任务运行日志
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Mapper
public interface TaskLogConvert {

    TaskLogConvert INSTANCE = Mappers.getMapper(TaskLogConvert.class);

    TaskLogRespDTO convert(TaskLogDO TaskLogDO);

    TaskLogDO convert(TaskLogCreateDTO TaskLogCreateDTO);

    TaskLogDO convert(TaskLogUpdateDTO TaskLogUpdateDTO);

}


