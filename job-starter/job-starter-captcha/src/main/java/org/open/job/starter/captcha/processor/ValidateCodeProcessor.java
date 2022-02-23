package org.open.job.starter.captcha.processor;

import org.open.job.starter.captcha.enums.ValidateCodeType;
import org.open.job.starter.captcha.request.CaptchaGenerateRequest;
import org.open.job.starter.captcha.request.CaptchaVerifyRequest;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description:校验码处理器，封装不同校验码的处理逻辑
 */
public interface ValidateCodeProcessor {


    boolean support(ValidateCodeType validateCodeType);
    /**
     * 创建校验码
     * @param captchaGenerateRequest
     * @throws Exception
     */
    void create(CaptchaGenerateRequest captchaGenerateRequest) throws Exception;

    /**
     * 校验验证码
     *
     * @param captchaVerifyRequest
     * @throws Exception
     */
    void validate(CaptchaVerifyRequest captchaVerifyRequest);
}
