package org.open.job.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.open.job.admin.dto.req.OpenJobUserReqDTO;
import org.open.job.admin.entity.OpenJobUserDO;
import org.springframework.stereotype.Repository;

/**
 * 用户表
 * 
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-06-22 15:20:30
 */
@Repository
public interface OpenJobUserMapper extends BaseMapper<OpenJobUserDO> {

    default OpenJobUserDO loadUserByUsername(String username){
        return selectOne(Wrappers.<OpenJobUserDO>lambdaQuery()
                .eq(OpenJobUserDO::getUsername, username)
                .last("limit 1")
        );
    }

    default OpenJobUserDO loadUserByMobile(String mobile){
        return selectOne(Wrappers.<OpenJobUserDO>lambdaQuery()
                .eq(OpenJobUserDO::getPhone, mobile)
                .last("limit 1")
        );
    }

    default Page<OpenJobUserDO> queryPage(OpenJobUserReqDTO openJobUserReqDTO){
        return selectPage(openJobUserReqDTO.page(), Wrappers.<OpenJobUserDO>lambdaQuery()
                .like(StringUtils.isNotBlank(openJobUserReqDTO.getUsername()), OpenJobUserDO::getUsername, openJobUserReqDTO.getUsername())
                .eq(StringUtils.isNotBlank(openJobUserReqDTO.getPhone()), OpenJobUserDO::getPhone, openJobUserReqDTO.getPhone())
        );
    }
}
