package com.saucesubfresh.job.admin.common.enums;


import com.saucesubfresh.starter.captcha.exception.ValidateCodeException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 */
@Getter
@AllArgsConstructor
public enum ValidateCodeType {

  SMS("sms","短信验证码"),

  IMAGE("image","图片验证码"),

  SCAN("scan","二维码");

  private final String value;
  private final String name;

  public static ValidateCodeType getValidateCodeType(String type) {
    return Arrays.stream(ValidateCodeType.values())
            .filter(validateCodeType -> validateCodeType.getValue().equals(type))
            .findFirst().orElseThrow(() -> new ValidateCodeException("验证码处理器" + type + "不存在"));
  }
}
