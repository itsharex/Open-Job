package org.open.job.starter.captcha.processor;

import org.open.job.starter.captcha.enums.ValidateCodeType;
import org.open.job.starter.captcha.exception.ValidateCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * 验证码处理器容器
 *
 * @Description：
 */
@Component
public class ValidateCodeProcessorHolder{

  @Autowired
  private List<ValidateCodeProcessor> validateCodeProcessors;

  public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType validateCodeType) {
    return validateCodeProcessors.stream().filter(e->e.support(validateCodeType)).findFirst().orElseThrow(()-> new ValidateCodeException(String.format("未匹配到验证码处理器 %s", validateCodeType.getName() )));
  }
}
