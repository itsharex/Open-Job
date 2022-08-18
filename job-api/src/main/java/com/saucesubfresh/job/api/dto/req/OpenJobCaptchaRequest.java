package com.saucesubfresh.job.api.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author lijunping on 2022/3/29
 */
@Data
public class OpenJobCaptchaRequest implements Serializable {

    private static final long serialVersionUID = 6874892463317732673L;

    @NotBlank(message = "请求唯一 id 不能为空")
    private String deviceId;

    private String mobile;
}
