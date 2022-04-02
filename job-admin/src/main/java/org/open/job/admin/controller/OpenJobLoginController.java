package org.open.job.admin.controller;

import com.lightcode.starter.captcha.core.image.ImageValidateCode;
import com.lightcode.starter.captcha.exception.ValidateCodeException;
import com.lightcode.starter.captcha.processor.CaptchaProcessor;
import com.lightcode.starter.captcha.request.CaptchaGenerateRequest;
import com.lightcode.starter.captcha.request.CaptchaVerifyRequest;
import com.lightcode.starter.oauth.core.password.PasswordAuthenticationProcessor;
import com.lightcode.starter.oauth.core.sms.SmsMobileAuthenticationProcessor;
import com.lightcode.starter.oauth.exception.AuthenticationException;
import com.lightcode.starter.oauth.request.MobileLoginRequest;
import com.lightcode.starter.oauth.request.PasswordLoginRequest;
import com.lightcode.starter.oauth.token.AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.open.job.admin.common.enums.ValidateCodeType;
import org.open.job.admin.dto.req.OpenJobCaptchaRequest;
import org.open.job.admin.dto.req.OpenJobMobileLoginRequest;
import org.open.job.admin.dto.req.OpenJobPasswordLoginRequest;
import org.open.job.common.exception.ControllerException;
import org.open.job.common.vo.Result;
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
@RequestMapping("/openJobLogin")
public class OpenJobLoginController {

    @Autowired
    private CaptchaProcessor captchaProcessor;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private PasswordAuthenticationProcessor passwordAuthentication;

    @Autowired
    private SmsMobileAuthenticationProcessor smsMobileAuthentication;

    /**
     * 创建验证码
     */
    @PostMapping("/validate/code")
    public void createCode(@RequestBody @Valid OpenJobCaptchaRequest request) throws Exception {
        CaptchaGenerateRequest captchaGenerateRequest = new CaptchaGenerateRequest();
        captchaGenerateRequest.setRequestId(request.getRequestId());
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

    /**
     * 用户名密码登录
     * @param request
     * @return
     */
    @PostMapping("/account")
    public Result<AccessToken> loginByUsername(@RequestBody @Valid OpenJobPasswordLoginRequest request){
        CaptchaVerifyRequest captchaVerifyRequest = new CaptchaVerifyRequest()
                .setRequestId(request.getDeviceId())
                .setCode(request.getCaptcha());
        try {
            captchaProcessor.validate(captchaVerifyRequest);
        } catch (ValidateCodeException e){
            throw new ControllerException(e.getMessage());
        }

        PasswordLoginRequest passwordLoginRequest = new PasswordLoginRequest()
                .setUsername(request.getUsername())
                .setPassword(request.getPassword());
        try {
            final AccessToken accessToken = passwordAuthentication.authentication(passwordLoginRequest);
            return Result.succeed(accessToken);
        } catch (AuthenticationException e){
            throw new ControllerException(e.getMessage());
        }
    }

    /**
     * 手机号短信验证码登录
     * @param request
     * @return
     */
    @PostMapping("/mobile")
    public Result<AccessToken> loginByMobile(@RequestBody @Valid OpenJobMobileLoginRequest request){
        CaptchaVerifyRequest captchaVerifyRequest = new CaptchaVerifyRequest()
                .setRequestId(request.getDeviceId())
                .setCode(request.getCaptcha());
        try {
            captchaProcessor.validate(captchaVerifyRequest);
        } catch (ValidateCodeException e){
            throw new ControllerException(e.getMessage());
        }

        MobileLoginRequest mobileLoginRequest = new MobileLoginRequest().setMobile(request.getMobile());
        try {
            final AccessToken accessToken = smsMobileAuthentication.authentication(mobileLoginRequest);
            return Result.succeed(accessToken);
        } catch (AuthenticationException e){
            throw new ControllerException(e.getMessage());
        }
    }
}
