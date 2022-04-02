package org.open.job.admin.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description: 手机号验证码登录
 */
@Data
public class OpenJobMobileLoginRequest implements Serializable {

    private static final long serialVersionUID = 2512743958725643646L;

    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @NotBlank(message = "验证码不能为空")
    private String captcha;

    @NotBlank(message = "设备唯一Id不能为空")
    private String deviceId;
}
