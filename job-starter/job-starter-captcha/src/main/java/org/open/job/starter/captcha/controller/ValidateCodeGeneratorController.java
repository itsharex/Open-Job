package org.open.job.starter.captcha.controller;

import org.open.job.starter.captcha.enums.CaptchaConstants;
import org.open.job.starter.captcha.enums.ValidateCodeType;
import org.open.job.starter.captcha.processor.ValidateCodeProcessor;
import org.open.job.starter.captcha.processor.ValidateCodeProcessorHolder;
import org.open.job.starter.captcha.request.CaptchaGenerateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 */
@Validated
@RestController
public class ValidateCodeGeneratorController {


  @Autowired
  private ValidateCodeProcessorHolder validateCodeProcessorHolder;

  /**
   * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
   *
   * @param captchaGenerateRequest
   * @throws Exception
   */
  @PostMapping(CaptchaConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX)
  public void createCode(@RequestBody @Valid CaptchaGenerateRequest captchaGenerateRequest) throws Exception {
    validateCodeProcessorHolder.findValidateCodeProcessor(ValidateCodeType.getValidateCodeType(captchaGenerateRequest.getType())).create(captchaGenerateRequest);
  }

}
