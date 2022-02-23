package org.open.job.admin.convert;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.open.job.admin.dto.create.TaskCreateDTO;
import org.open.job.admin.dto.resp.TaskRespDTO;
import org.open.job.admin.dto.update.TaskUpdateDTO;
import org.open.job.admin.entity.TaskDO;

/**
 * 爬虫任务表
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Mapper
public interface TaskConvert {

    TaskConvert INSTANCE = Mappers.getMapper(TaskConvert.class);

    TaskRespDTO convert(TaskDO TaskDO);

    TaskDO convert(TaskCreateDTO TaskCreateDTO);

    TaskDO convert(TaskUpdateDTO TaskUpdateDTO);

}
