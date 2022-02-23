package org.open.job.starter.captcha.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description:校验码处理请求实体类
 */
@Data
@NoArgsConstructor
public class CaptchaVerifyRequest implements Serializable {
    private static final long serialVersionUID = -3596061866163459943L;

    @NotBlank(message = "设备唯一 id 不能为空")
    private String deviceId;

    private String code;

    private String mobile;

    public CaptchaVerifyRequest(String deviceId, String code) {
        this.deviceId = deviceId;
        this.code = code;
    }

    public CaptchaVerifyRequest(String deviceId, String code, String mobile) {
        this.deviceId = deviceId;
        this.code = code;
        this.mobile = mobile;
    }
}
