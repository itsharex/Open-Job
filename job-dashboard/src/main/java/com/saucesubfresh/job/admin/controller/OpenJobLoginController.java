/*
 * Copyright © 2022 organization SauceSubFresh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.saucesubfresh.job.admin.controller;

import com.saucesubfresh.starter.captcha.exception.ValidateCodeException;
import com.saucesubfresh.starter.captcha.processor.CaptchaVerifyProcessor;
import com.saucesubfresh.starter.captcha.request.CaptchaVerifyRequest;
import com.saucesubfresh.starter.oauth.core.password.PasswordAuthenticationProcessor;
import com.saucesubfresh.starter.oauth.core.sms.SmsMobileAuthenticationProcessor;
import com.saucesubfresh.starter.oauth.exception.AuthenticationException;
import com.saucesubfresh.starter.oauth.request.MobileLoginRequest;
import com.saucesubfresh.starter.oauth.request.PasswordLoginRequest;
import com.saucesubfresh.starter.oauth.token.AccessToken;
import lombok.extern.slf4j.Slf4j;
import com.saucesubfresh.job.api.dto.req.OpenJobMobileLoginRequest;
import com.saucesubfresh.job.api.dto.req.OpenJobPasswordLoginRequest;
import com.saucesubfresh.job.common.exception.ControllerException;
import com.saucesubfresh.job.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author lijunping on 2022/3/29
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/login")
public class OpenJobLoginController {

    @Autowired
    private CaptchaVerifyProcessor captchaVerifyProcessor;

    @Autowired
    private PasswordAuthenticationProcessor passwordAuthentication;

    @Autowired
    private SmsMobileAuthenticationProcessor smsMobileAuthentication;

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
            captchaVerifyProcessor.validate(captchaVerifyRequest);
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
            captchaVerifyProcessor.validate(captchaVerifyRequest);
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
