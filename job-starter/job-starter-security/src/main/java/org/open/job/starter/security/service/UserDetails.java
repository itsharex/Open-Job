package org.open.job.starter.security.service;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description: 用户信息
 */
@Data
public class UserDetails implements Serializable {
    private static final long serialVersionUID = 7779447586729787146L;

    @ApiModelProperty("用户 id")
    private Long id;

    /**
     * 获取用户名称
     * @return
     */
    @ApiModelProperty("用户名称")
    private String username;

    /**
     * 获取密码
     * @return
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 获取用户手机号
     * @return
     */
    @ApiModelProperty("用户手机号")
    private String mobile;

}
