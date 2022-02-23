package org.open.job.starter.captcha.core.image;


import com.google.code.kaptcha.Producer;
import org.open.job.starter.captcha.core.domain.ValidateCode;
import org.open.job.starter.captcha.generator.ValidateCodeGenerator;
import org.open.job.starter.captcha.properties.CaptchaProperties;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description:
 * (1)配置文件中可以配置高度、宽度、验证码的位数和过期时间
 */
@Component("imageValidateCodeGenerator")
public class ImageCodeGenerator implements ValidateCodeGenerator {

  private final CaptchaProperties captchaProperties;

  private final Producer producer;

  public ImageCodeGenerator(CaptchaProperties captchaProperties, Producer producer) {
    this.captchaProperties = captchaProperties;
    this.producer = producer;
  }

  @Override
  public ValidateCode generate() {

    String text = producer.createText();
    BufferedImage image = producer.createImage(text);

    return new ImageValidateCode(image, text, captchaProperties.getCode().getImage().getExpireTime());
  }
}
