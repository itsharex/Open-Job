package org.open.job.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.open.job.admin.convert.OpenJobUserConvert;
import org.open.job.admin.dto.create.OpenJobUserCreateDTO;
import org.open.job.admin.dto.req.OpenJobUserReqDTO;
import org.open.job.admin.dto.resp.OpenJobUserRespDTO;
import org.open.job.admin.dto.update.OpenJobUserUpdateDTO;
import org.open.job.admin.entity.OpenJobUserDO;
import org.open.job.admin.mapper.OpenJobUserMapper;
import org.open.job.admin.service.OpenJobUserService;
import org.open.job.common.vo.PageResult;
import org.open.job.starter.security.service.UserDetailService;
import org.open.job.starter.security.service.UserDetails;
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
        return OpenJobUserConvert.INSTANCE.convertDetails(openJobUserDO);
    }

    @Override
    public UserDetails loadUserByMobile(String mobile) {
        OpenJobUserDO openJobUserDO = this.openJobUserMapper.loadUserByMobile(mobile);
        return OpenJobUserConvert.INSTANCE.convertDetails(openJobUserDO);
    }
}