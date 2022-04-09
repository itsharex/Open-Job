package com.saucesubfresh.job.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saucesubfresh.job.admin.convert.OpenJobUserConvert;
import com.saucesubfresh.job.admin.dto.req.OpenJobUserReqDTO;
import com.saucesubfresh.job.admin.entity.OpenJobUserDO;
import com.saucesubfresh.job.admin.mapper.OpenJobUserMapper;
import com.saucesubfresh.job.admin.service.OpenJobUserService;
import com.saucesubfresh.starter.oauth.domain.UserDetails;
import com.saucesubfresh.starter.oauth.service.UserDetailService;
import com.saucesubfresh.job.admin.dto.create.OpenJobUserCreateDTO;
import com.saucesubfresh.job.admin.dto.resp.OpenJobUserRespDTO;
import com.saucesubfresh.job.admin.dto.update.OpenJobUserUpdateDTO;
import com.saucesubfresh.job.common.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OpenJobUserServiceImpl extends ServiceImpl<OpenJobUserMapper, OpenJobUserDO> implements OpenJobUserService, UserDetailService {

    @Autowired
    private OpenJobUserMapper openJobUserMapper;

    @Override
    public PageResult<OpenJobUserRespDTO> selectPage(OpenJobUserReqDTO openJobUserReqDTO) {
        Page<OpenJobUserDO> page = openJobUserMapper.queryPage(openJobUserReqDTO);
        IPage<OpenJobUserRespDTO> convert = page.convert(OpenJobUserConvert.INSTANCE::convert);
        return PageResult.build(convert);
    }

    @Override
    public OpenJobUserRespDTO getById(Long id) {
        OpenJobUserDO openJobUserDO = openJobUserMapper.selectById(id);
        return OpenJobUserConvert.INSTANCE.convert(openJobUserDO);
    }

    @Override
    public boolean save(OpenJobUserCreateDTO openJobUserCreateDTO) {
        openJobUserMapper.insert(OpenJobUserConvert.INSTANCE.convert(openJobUserCreateDTO));
        return true;
    }

    @Override
    public boolean updateById(OpenJobUserUpdateDTO openJobUserUpdateDTO) {
        openJobUserMapper.updateById(OpenJobUserConvert.INSTANCE.convert(openJobUserUpdateDTO));
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        openJobUserMapper.deleteById(id);
        return true;
    }

    @Override
    public OpenJobUserRespDTO loadUserByUserId(Long userId) {
        OpenJobUserDO openJobUserDO = this.openJobUserMapper.selectById(userId);
        return OpenJobUserConvert.INSTANCE.convert(openJobUserDO);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        OpenJobUserDO openJobUserDO = this.openJobUserMapper.loadUserByUsername(username);
        return convert(openJobUserDO);
    }

    @Override
    public UserDetails loadUserByMobile(String mobile) {
        OpenJobUserDO openJobUserDO = this.openJobUserMapper.loadUserByMobile(mobile);
        return convert(openJobUserDO);
    }

    private UserDetails convert(OpenJobUserDO openJobUserDO){
        UserDetails userDetails = new UserDetails();
        userDetails.setId(openJobUserDO.getId());
        userDetails.setUsername(openJobUserDO.getUsername());
        userDetails.setPassword(openJobUserDO.getPassword());
        userDetails.setMobile(openJobUserDO.getPhone());
        userDetails.setAccountLocked(openJobUserDO.getStatus() == 1);
        return userDetails;
    }
}