package org.open.job.admin.convert;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.open.job.admin.dto.create.OpenJobCreateDTO;
import org.open.job.admin.dto.resp.OpenJobRespDTO;
import org.open.job.admin.dto.update.OpenJobUpdateDTO;
import org.open.job.admin.entity.OpenJobDO;

import java.util.List;

/**
 * 爬虫任务表
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Mapper
public interface OpenJobConvert {

    OpenJobConvert INSTANCE = Mappers.getMapper(OpenJobConvert.class);

    OpenJobRespDTO convert(OpenJobDO OpenJobDO);

    OpenJobDO convert(OpenJobCreateDTO OpenJobCreateDTO);

    OpenJobDO convert(OpenJobUpdateDTO OpenJobUpdateDTO);

    List<OpenJobRespDTO> convertList(List<OpenJobDO> jobDOS);
}
