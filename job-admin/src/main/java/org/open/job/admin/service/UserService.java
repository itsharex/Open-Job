package org.open.job.admin.service;

import org.open.job.admin.dto.create.UserCreateDTO;
import org.open.job.admin.dto.req.UserReqDTO;
import org.open.job.admin.dto.resp.UserRespDTO;
import org.open.job.admin.dto.update.UserUpdateDTO;
import org.open.job.common.vo.PageResult;

/**
 * 用户表
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-06-22 15:20:30
 */
public interface UserService {

    PageResult<UserRespDTO> selectPage(UserReqDTO userReqDTO);

    UserRespDTO getById(Long id);

    boolean save(UserCreateDTO userCreateDTO);

    boolean updateById(UserUpdateDTO userUpdateDTO);

    boolean deleteById(Long id);

    UserRespDTO loadUserByUserId(Long userId);
}

