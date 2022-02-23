package org.open.job.starter.captcha.config;

import org.open.job.starter.captcha.core.sms.DefaultSmsCodeSender;
import org.open.job.starter.captcha.core.sms.SmsCodeSender;
import org.open.job.starter.captcha.properties.CaptchaProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CaptchaProperties.class)
public class ValidateCodeBeanConfig {

  @Bean
  @ConditionalOnMissingBean(SmsCodeSender.class)
  public SmsCodeSender smsCodeSender() {
    return new DefaultSmsCodeSender();
  }

}
