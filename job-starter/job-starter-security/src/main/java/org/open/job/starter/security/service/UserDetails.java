package org.open.job.starter.security.service;

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

    /**
     * 用户 id
     */
    private Long id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户手机号
     */
    private String mobile;

}
