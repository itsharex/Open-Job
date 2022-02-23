package org.open.job.starter.captcha.core.sms;

import org.apache.commons.lang3.RandomStringUtils;
import org.open.job.starter.captcha.core.domain.ValidateCode;
import org.open.job.starter.captcha.generator.ValidateCodeGenerator;
import org.open.job.starter.captcha.properties.CaptchaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * @Description: 注意验证码+ 超时时间可以进行配置
 */
@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private CaptchaProperties captchaProperties;

    @Override
    public ValidateCode generate() {
        String code = RandomStringUtils.randomNumeric(captchaProperties.getCode().getSms().getLength());
        return new ValidateCode(code, captchaProperties.getCode().getSms().getExpireTime());
    }
}
