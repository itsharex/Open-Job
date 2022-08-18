package com.saucesubfresh.job.admin.convert;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.saucesubfresh.job.api.dto.create.OpenJobLogCreateDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobLogRespDTO;
import com.saucesubfresh.job.api.dto.update.OpenJobLogUpdateDTO;
import com.saucesubfresh.job.admin.entity.OpenJobLogDO;

/**
 * 任务运行日志
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


