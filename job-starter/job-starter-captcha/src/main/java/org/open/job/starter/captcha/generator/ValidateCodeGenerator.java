package org.open.job.starter.captcha.generator;


import org.open.job.starter.captcha.core.domain.ValidateCode;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description : 以后还会有别的验证码生成逻辑，故将其统一定义出来
 */
public interface ValidateCodeGenerator {
    /**
     * 生成验证码
     *
     * @return
     */
    ValidateCode generate() throws Exception;
}
