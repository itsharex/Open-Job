package org.open.job.admin.controller;

import com.saucesubfresh.starter.captcha.core.image.ImageValidateCode;
import com.saucesubfresh.starter.captcha.processor.CaptchaProcessor;
import com.saucesubfresh.starter.captcha.request.CaptchaGenerateRequest;
import lombok.extern.slf4j.Slf4j;
import org.open.job.admin.common.enums.ValidateCodeType;
import org.open.job.admin.dto.req.OpenJobCaptchaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @author lijunping on 2022/3/29
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/captcha")
public class OpenJobCaptchaController {

    @Autowired
    private CaptchaProcessor captchaProcessor;

    /**
     * 创建验证码
     */
    @PostMapping("/create")
    public void createCode(@RequestBody @Valid OpenJobCaptchaRequest request, HttpServletResponse response) throws Exception {
        CaptchaGenerateRequest captchaGenerateRequest = new CaptchaGenerateRequest();
        captchaGenerateRequest.setRequestId(request.getDeviceId());
        captchaGenerateRequest.setType(request.getType());

        captchaProcessor.create(captchaGenerateRequest, validateCode -> {
            final String type = request.getType();
            final ValidateCodeType codeType = ValidateCodeType.getValidateCodeType(type);
            switch (codeType){
                case IMAGE:
                    try {
                        ImageValidateCode imageValidateCode = (ImageValidateCode) validateCode;
                        ImageIO.write(imageValidateCode.getImage(), "JPEG", response.getOutputStream());
                    } catch (IOException e) {
                        log.error(e.getMessage(), e);
                    }
                    break;
                case SMS:
                    log.info("向手机号: {}发送短信验证码: {}", request.getMobile(), validateCode.getCode());
                    break;
            }
        });
    }

}
