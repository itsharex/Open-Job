package org.open.job.starter.captcha.core.scan;

import org.open.job.starter.captcha.enums.ValidateCodeType;
import org.open.job.starter.captcha.processor.AbstractValidateCodeProcessor;
import org.open.job.starter.captcha.request.CaptchaGenerateRequest;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description:
 */
@Component
public class ScanCodeProcessor extends AbstractValidateCodeProcessor<ScanValidateCode> {

  @Override
  public boolean support(ValidateCodeType validateCodeType) {
    return validateCodeType == ValidateCodeType.SCAN;
  }

  /**
   * 扫码发送器
   */
  @Override
  protected void send(HttpServletResponse response, CaptchaGenerateRequest captchaGenerateRequest, ScanValidateCode scanValidateCode) throws Exception {
    assert response != null;
    ImageIO.write(scanValidateCode.getImage(), "PNG", response.getOutputStream());
  }
}
