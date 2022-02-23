package org.open.job.starter.captcha.core.sms;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.open.job.starter.captcha.core.domain.ValidateCode;
import org.open.job.starter.captcha.enums.ValidateCodeType;
import org.open.job.starter.captcha.processor.AbstractValidateCodeProcessor;
import org.open.job.starter.captcha.request.CaptchaGenerateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description:
 */
@Component
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

  /**
   * 短信验证码发送器
   */
  @Autowired
  private SmsCodeSender smsCodeSender;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public boolean support(ValidateCodeType validateCodeType) {
    return validateCodeType == ValidateCodeType.SMS;
  }

  @Override
  protected void send(HttpServletResponse response, CaptchaGenerateRequest captchaGenerateRequest, ValidateCode smsCode) throws Exception {
    String mobile = captchaGenerateRequest.getMobile();
    smsCodeSender.send(mobile, smsCode.getCode());
    assert response != null;
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(objectMapper.writeValueAsString("success"));
  }
}
