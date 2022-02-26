package org.open.job.admin.convert;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.open.job.admin.dto.create.OpenJobLogCreateDTO;
import org.open.job.admin.dto.resp.OpenJobLogRespDTO;
import org.open.job.admin.dto.update.OpenJobLogUpdateDTO;
import org.open.job.admin.entity.OpenJobLogDO;

/**
 * 爬虫任务运行日志
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Mapper
public interface OpenJobLogConvert {

    OpenJobLogConvert INSTANCE = Mappers.getMapper(OpenJobLogConvert.class);

    OpenJobLogRespDTO convert(OpenJobLogDO OpenJobLogDO);

    OpenJobLogDO convert(OpenJobLogCreateDTO OpenJobLogCreateDTO);

    OpenJobLogDO convert(OpenJobLogUpdateDTO OpenJobLogUpdateDTO);

}


