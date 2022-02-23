package org.open.job.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.open.job.admin.convert.UserConvert;
import org.open.job.admin.dto.create.UserCreateDTO;
import org.open.job.admin.dto.req.UserReqDTO;
import org.open.job.admin.dto.resp.UserRespDTO;
import org.open.job.admin.dto.update.UserUpdateDTO;
import org.open.job.admin.entity.UserDO;
import org.open.job.admin.mapper.UserMapper;
import org.open.job.admin.service.UserService;
import org.open.job.common.vo.PageResult;
import org.open.job.starter.security.service.UserDetailService;
import org.open.job.starter.security.service.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService, UserDetailService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageResult<UserRespDTO> selectPage(UserReqDTO userReqDTO) {
        Page<UserDO> page = userMapper.queryPage(userReqDTO);
        IPage<UserRespDTO> convert = page.convert(UserConvert.INSTANCE::convert);
        return PageResult.build(convert);
    }

    @Override
    public UserRespDTO getById(Long id) {
        UserDO userDO = userMapper.selectById(id);
        return UserConvert.INSTANCE.convert(userDO);
    }

    @Override
    public boolean save(UserCreateDTO userCreateDTO) {
        userMapper.insert(UserConvert.INSTANCE.convert(userCreateDTO));
        return true;
    }

    @Override
    public boolean updateById(UserUpdateDTO userUpdateDTO) {
        userMapper.updateById(UserConvert.INSTANCE.convert(userUpdateDTO));
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        userMapper.deleteById(id);
        return true;
    }

    @Override
    public UserRespDTO loadUserByUserId(Long userId) {
        UserDO userDO = this.userMapper.selectById(userId);
        return UserConvert.INSTANCE.convert(userDO);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDO userDO = this.userMapper.loadUserByUsername(username);
        return UserConvert.INSTANCE.convertDetails(userDO);
    }

    @Override
    public UserDetails loadUserByMobile(String mobile) {
        UserDO userDO = this.userMapper.loadUserByMobile(mobile);
        return UserConvert.INSTANCE.convertDetails(userDO);
    }
}