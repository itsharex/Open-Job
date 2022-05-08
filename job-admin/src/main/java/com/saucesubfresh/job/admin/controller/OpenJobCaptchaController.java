package com.saucesubfresh.job.admin.controller;

import com.saucesubfresh.job.admin.dto.req.OpenJobCaptchaRequest;
import com.saucesubfresh.job.admin.dto.resp.OpenJobCaptchaRespDTO;
import com.saucesubfresh.job.common.exception.ControllerException;
import com.saucesubfresh.job.common.vo.Result;
import com.saucesubfresh.starter.captcha.core.image.ImageCodeGenerator;
import com.saucesubfresh.starter.captcha.core.image.ImageValidateCode;
import com.saucesubfresh.starter.captcha.core.sms.SmsCodeGenerator;
import com.saucesubfresh.starter.captcha.core.sms.ValidateCode;
import com.saucesubfresh.starter.captcha.exception.ValidateCodeException;
import com.saucesubfresh.starter.captcha.request.CaptchaGenerateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author lijunping on 2022/3/29
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/captcha")
public class OpenJobCaptchaController {

    private final ImageCodeGenerator imageCodeGenerator;
    private final SmsCodeGenerator smsCodeGenerator;

    public OpenJobCaptchaController(ImageCodeGenerator imageCodeGenerator, SmsCodeGenerator smsCodeGenerator) {
        this.imageCodeGenerator = imageCodeGenerator;
        this.smsCodeGenerator = smsCodeGenerator;
    }

    @PostMapping("/create/image")
    public Result<OpenJobCaptchaRespDTO> createImageCode(@RequestBody @Valid OpenJobCaptchaRequest request) {
        OpenJobCaptchaRespDTO openJobCaptchaRespDTO = new OpenJobCaptchaRespDTO();
        CaptchaGenerateRequest captchaGenerateRequest = new CaptchaGenerateRequest();
        captchaGenerateRequest.setRequestId(request.getDeviceId());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageValidateCode imageValidateCode = imageCodeGenerator.create(captchaGenerateRequest);
            ImageIO.write(imageValidateCode.getImage(), "JPEG", byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            String base64ImgCode = Base64Utils.encodeToString(bytes);
            openJobCaptchaRespDTO.setImageCode(base64ImgCode);
            openJobCaptchaRespDTO.setSuccess(true);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new ControllerException(e.getMessage());
        }
        return Result.succeed(openJobCaptchaRespDTO);
    }

    @PostMapping("/create/sms")
    public Result<OpenJobCaptchaRespDTO> createSmsCode(@RequestBody @Valid OpenJobCaptchaRequest request) {
        OpenJobCaptchaRespDTO openJobCaptchaRespDTO = new OpenJobCaptchaRespDTO();
        CaptchaGenerateRequest captchaGenerateRequest = new CaptchaGenerateRequest();
        captchaGenerateRequest.setRequestId(request.getDeviceId());
        try {
            ValidateCode validateCode = smsCodeGenerator.create(captchaGenerateRequest);
            openJobCaptchaRespDTO.setSuccess(true);
            log.info("向手机号: {}发送短信验证码: {}", request.getMobile(), validateCode.getCode());
        } catch (ValidateCodeException e) {
            log.error(e.getMessage(), e);
            throw new ControllerException(e.getMessage());
        }
        return Result.succeed(openJobCaptchaRespDTO);
    }

}
