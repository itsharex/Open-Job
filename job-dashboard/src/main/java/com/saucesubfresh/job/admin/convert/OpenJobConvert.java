package com.saucesubfresh.job.admin.convert;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.saucesubfresh.job.api.dto.create.OpenJobCreateDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobRespDTO;
import com.saucesubfresh.job.api.dto.update.OpenJobUpdateDTO;
import com.saucesubfresh.job.admin.entity.OpenJobDO;

import java.util.List;

/**
 * 任务表
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
