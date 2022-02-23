package org.open.job.admin.convert;



import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.open.job.admin.dto.create.UserCreateDTO;
import org.open.job.admin.dto.resp.UserRespDTO;
import org.open.job.admin.dto.update.UserUpdateDTO;
import org.open.job.admin.entity.UserDO;
import org.open.job.starter.security.service.UserDetails;

/**
 * 用户表
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-06-22 15:20:30
 */
@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserRespDTO convert(UserDO userDO);

    @Mappings({@Mapping(source = "phone", target = "mobile")})
    UserDetails convertDetails(UserDO userDO);

    UserDO convert(UserCreateDTO userCreateDTO);

    UserDO convert(UserUpdateDTO userUpdateDTO);

}


