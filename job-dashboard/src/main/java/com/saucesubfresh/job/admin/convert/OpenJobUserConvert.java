package com.saucesubfresh.job.admin.convert;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.saucesubfresh.job.api.dto.create.OpenJobUserCreateDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobUserRespDTO;
import com.saucesubfresh.job.api.dto.update.OpenJobUserUpdateDTO;
import com.saucesubfresh.job.admin.entity.OpenJobUserDO;

/**
 * 用户表
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-06-22 15:20:30
 */
@Mapper
public interface OpenJobUserConvert {

    OpenJobUserConvert INSTANCE = Mappers.getMapper(OpenJobUserConvert.class);

    OpenJobUserRespDTO convert(OpenJobUserDO openJobUserDO);

    OpenJobUserDO convert(OpenJobUserCreateDTO openJobUserCreateDTO);

    OpenJobUserDO convert(OpenJobUserUpdateDTO openJobUserUpdateDTO);

}


