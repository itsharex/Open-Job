package org.open.job.starter.captcha.core.image;

import org.open.job.starter.captcha.enums.ValidateCodeType;
import org.open.job.starter.captcha.processor.AbstractValidateCodeProcessor;
import org.open.job.starter.captcha.request.CaptchaGenerateRequest;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * @date 2019/7/10 20:22
 * Description:  图片验证码处理器
 */
@Component
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageValidateCode> {

  @Override
  public boolean support(ValidateCodeType validateCodeType) {
    return validateCodeType == ValidateCodeType.IMAGE;
  }

    /**
     * 发送图形验证码，将其写到响应中
     *
     * @param response
     * @param captchaGenerateRequest
     * @param imageValidateCode
     * @throws Exception
     */
    @Override
    protected void send(HttpServletResponse response, CaptchaGenerateRequest captchaGenerateRequest, ImageValidateCode imageValidateCode) throws Exception {
      assert response != null;
      ImageIO.write(imageValidateCode.getImage(), "JPEG", response.getOutputStream());
    }
}
