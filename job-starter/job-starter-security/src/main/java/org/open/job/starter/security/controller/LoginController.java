package org.open.job.starter.security.controller;

import com.pro.crawler.common.exception.ControllerException;
import com.pro.crawler.common.vo.Result;
import com.pro.crawler.common.vo.ResultEnum;
import com.pro.crawler.starter.captcha.enums.ValidateCodeType;
import com.pro.crawler.starter.captcha.exception.ValidateCodeException;
import com.pro.crawler.starter.captcha.processor.ValidateCodeProcessorHolder;
import com.pro.crawler.starter.captcha.request.CaptchaVerifyRequest;
import com.pro.crawler.starter.security.authentication.Authentication;
import com.pro.crawler.starter.security.component.AuthenticationFailureHandler;
import com.pro.crawler.starter.security.component.AuthenticationSuccessHandler;
import com.pro.crawler.starter.security.exception.AuthenticationException;
import com.pro.crawler.starter.security.request.MobileLoginRequest;
import com.pro.crawler.starter.security.request.PasswordLoginRequest;
import com.pro.crawler.starter.security.service.UserDetailService;
import com.pro.crawler.starter.security.service.UserDetails;
import com.pro.crawler.starter.security.store.TokenStore;
import com.pro.crawler.starter.security.token.Token;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description: 登录接口
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenStore tokenStore;

    @ApiOperation("退出登录")
    @GetMapping("/outLogin")
    public Result<Boolean> logout(@RequestParam("accessToken") String accessToken){
        Boolean success = tokenStore.removeToken(accessToken);
        return Result.succeed(success);
    }

    @ApiOperation("用户名密码登录")
    @PostMapping("/account")
    public Result<Token> loginByUsername(@RequestBody @Valid PasswordLoginRequest request){
        String username = request.getUsername();
        String password = request.getPassword();
        String captcha = request.getCaptcha();
        String deviceId = request.getDeviceId();

        try {
            try {
                CaptchaVerifyRequest captchaVerifyRequest = new CaptchaVerifyRequest(deviceId, captcha);
                validateCodeProcessorHolder.findValidateCodeProcessor(ValidateCodeType.IMAGE).validate(captchaVerifyRequest);
            } catch (ValidateCodeException e){
                throw new AuthenticationException(1007, e.getMessage());
            }

            UserDetails userDetails = userDetailService.loadUserByUsername(username);

            if (Objects.isNull(userDetails)){
                throw new AuthenticationException(ResultEnum.USERNAME_OR_PASSWORD_ERROR);
            }

            boolean matches = passwordEncoder.matches(password, userDetails.getPassword());
            if (!matches){
                throw new AuthenticationException(ResultEnum.CAPTCHA_ERROR);
            }

            Authentication authentication = new Authentication(userDetails);
            Token token = tokenStore.generateToken(authentication);
            authenticationSuccessHandler.onAuthenticationSuccess(authentication);

            return Result.succeed(token);
        } catch (AuthenticationException e){
            authenticationFailureHandler.onAuthenticationFailureHandler(e);
            throw new ControllerException(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("手机号短信验证码登录")
    @PostMapping("/mobile")
    public Result<Token> loginByMobile(@RequestBody @Valid MobileLoginRequest request){
        String mobile = request.getMobile();
        String captcha = request.getCaptcha();
        String deviceId = request.getDeviceId();

        try {
            try {
                CaptchaVerifyRequest captchaVerifyRequest = new CaptchaVerifyRequest(deviceId, captcha, mobile);
                validateCodeProcessorHolder.findValidateCodeProcessor(ValidateCodeType.SMS).validate(captchaVerifyRequest);
            } catch (ValidateCodeException e){
                throw new AuthenticationException(ResultEnum.CAPTCHA_ERROR);
            }

            UserDetails userDetails = userDetailService.loadUserByMobile(mobile);

            if (Objects.isNull(userDetails)){
                throw new AuthenticationException(ResultEnum.USERNAME_OR_PASSWORD_ERROR);
            }

            Authentication authentication = new Authentication(userDetails);
            Token token = tokenStore.generateToken(authentication);
            authenticationSuccessHandler.onAuthenticationSuccess(authentication);

            return Result.succeed(token);
        } catch (AuthenticationException e){
            authenticationFailureHandler.onAuthenticationFailureHandler(e);
            throw new ControllerException(e.getCode(), e.getMessage());
        }
    }
}
