package org.open.job.admin.convert;


import com.lightcode.starter.oauth.domain.UserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.open.job.admin.dto.create.OpenJobUserCreateDTO;
import org.open.job.admin.dto.resp.OpenJobUserRespDTO;
import org.open.job.admin.dto.update.OpenJobUserUpdateDTO;
import org.open.job.admin.entity.OpenJobUserDO;

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


