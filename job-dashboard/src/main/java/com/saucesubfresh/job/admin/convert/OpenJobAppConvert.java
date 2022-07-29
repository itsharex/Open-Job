package com.saucesubfresh.job.admin.convert;

import com.saucesubfresh.job.admin.dto.create.OpenJobAppCreateDTO;
import com.saucesubfresh.job.admin.dto.resp.OpenJobAppRespDTO;
import com.saucesubfresh.job.admin.dto.update.OpenJobAppUpdateDTO;
import com.saucesubfresh.job.admin.entity.OpenJobAppDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 任务运行日志
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Mapper
public interface OpenJobAppConvert {

    OpenJobAppConvert INSTANCE = Mappers.getMapper(OpenJobAppConvert.class);

    OpenJobAppRespDTO convert(OpenJobAppDO OpenJobAppDO);

    OpenJobAppDO convert(OpenJobAppCreateDTO OpenJobAppCreateDTO);

    OpenJobAppDO convert(OpenJobAppUpdateDTO OpenJobAppUpdateDTO);

    List<OpenJobAppRespDTO> convertList(List<OpenJobAppDO> openJobAppDOS);
}


