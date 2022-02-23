package org.open.job.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.open.job.admin.dto.req.UserReqDTO;
import org.open.job.admin.entity.UserDO;
import org.springframework.stereotype.Repository;

/**
 * 用户表
 * 
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-06-22 15:20:30
 */
@Repository
public interface UserMapper extends BaseMapper<UserDO> {

    default UserDO loadUserByUsername(String username){
        return selectOne(Wrappers.<UserDO>lambdaQuery()
                .eq(UserDO::getUsername, username)
                .last("limit 1")
        );
    }

    default UserDO loadUserByMobile(String mobile){
        return selectOne(Wrappers.<UserDO>lambdaQuery()
                .eq(UserDO::getPhone, mobile)
                .last("limit 1")
        );
    }

    default Page<UserDO> queryPage(UserReqDTO userReqDTO){
        return selectPage(userReqDTO.page(), Wrappers.<UserDO>lambdaQuery()
                .like(StringUtils.isNotBlank(userReqDTO.getUsername()), UserDO::getUsername, userReqDTO.getUsername())
                .eq(StringUtils.isNotBlank(userReqDTO.getPhone()), UserDO::getPhone, userReqDTO.getPhone())
        );
    }
}
