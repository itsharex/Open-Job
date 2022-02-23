package org.open.job.starter.captcha.core.sms;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description: 短信发送接口 ----  不同供应商可能不同
 */
public interface SmsCodeSender {

    /**
     * 向手机发送短信验证码
     *
     * @param mobile
     * @param code
     */
    void send(String mobile, String code);
}
