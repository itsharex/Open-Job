package org.open.job.admin.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author lijunping on 2022/3/29
 */
@Data
public class OpenJobCaptchaRequest implements Serializable {

    private static final long serialVersionUID = 6874892463317732673L;

    @NotBlank(message = "验证码类型不能为空, 验证码类型，可选值：sms，image，scan")
    private String type;

    @NotBlank(message = "请求唯一 id 不能为空")
    private String requestId;

    private String mobile;
}
