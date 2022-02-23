package org.open.job.starter.security.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description: 用户名密码登录
 */
@Data
public class PasswordLoginRequest implements Serializable {

    private static final long serialVersionUID = 2512743958725643646L;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("图片验证码")
    @NotBlank(message = "验证码不能为空")
    private String captcha;

    @NotBlank(message = "设备唯一Id不能为空")
    @ApiModelProperty("设备唯一Id")
    private String deviceId;
}
