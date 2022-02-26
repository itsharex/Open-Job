package org.open.job.admin.service;

import org.open.job.admin.dto.create.OpenJobUserCreateDTO;
import org.open.job.admin.dto.req.OpenJobUserReqDTO;
import org.open.job.admin.dto.resp.OpenJobUserRespDTO;
import org.open.job.admin.dto.update.OpenJobUserUpdateDTO;
import org.open.job.common.vo.PageResult;

/**
 * 用户表
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-06-22 15:20:30
 */
public interface OpenJobUserService {

    PageResult<OpenJobUserRespDTO> selectPage(OpenJobUserReqDTO openJobUserReqDTO);

    OpenJobUserRespDTO getById(Long id);

    boolean save(OpenJobUserCreateDTO openJobUserCreateDTO);

    boolean updateById(OpenJobUserUpdateDTO openJobUserUpdateDTO);

    boolean deleteById(Long id);

    OpenJobUserRespDTO loadUserByUserId(Long userId);
}
