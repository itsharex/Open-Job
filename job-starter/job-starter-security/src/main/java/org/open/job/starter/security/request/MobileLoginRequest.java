package org.open.job.starter.security.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description: 手机号验证码登录
 */
@Data
public class MobileLoginRequest implements Serializable {

    private static final long serialVersionUID = 2512743958725643646L;

    @NotBlank(message = "手机号不能为空")
    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("图片验证码")
    @NotBlank(message = "验证码不能为空")
    private String captcha;

    @NotBlank(message = "设备唯一Id不能为空")
    @ApiModelProperty("设备唯一Id")
    private String deviceId;
}
