package org.open.job.starter.captcha.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description: 验证码生成请求实体类
 */
@Data
public class CaptchaGenerateRequest implements Serializable {
    private static final long serialVersionUID = -3596061866163459943L;

    @NotBlank(message = "验证码类型不能为空, 验证码类型，可选值：sms，image，scan")
    private String type;

    @NotBlank(message = "设备唯一 id 不能为空")
    private String deviceId;

    private String mobile;
}
