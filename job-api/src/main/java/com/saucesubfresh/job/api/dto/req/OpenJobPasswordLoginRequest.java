package com.saucesubfresh.job.api.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description: 用户名密码登录
 */
@Data
public class OpenJobPasswordLoginRequest implements Serializable {

    private static final long serialVersionUID = 2512743958725643646L;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String captcha;

    @NotBlank(message = "设备唯一Id不能为空")
    private String deviceId;
}
