package org.open.job.starter.captcha.core.scan;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.open.job.starter.captcha.core.domain.ValidateCode;
import org.open.job.starter.captcha.generator.ValidateCodeGenerator;
import org.open.job.starter.captcha.properties.CaptchaProperties;
import org.open.job.starter.captcha.properties.ScanCodeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.UUID;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * @Description: 注意验证码+ 超时时间可以进行配置
 */
@Component("scanValidateCodeGenerator")
public class ScanCodeGenerator implements ValidateCodeGenerator {

  @Autowired
  private CaptchaProperties captchaProperties;

  @Override
  public ValidateCode generate() throws WriterException {
    String str = UUID.randomUUID().toString();
    String uuid = str.replaceAll("-", "");
    ScanCodeProperties scanCodeProperties = captchaProperties.getCode().getScan();

    Hashtable<EncodeHintType, Object> hintTypes = new Hashtable<>();

    hintTypes.put(EncodeHintType.CHARACTER_SET, CharacterSetECI.UTF8);//设置编码
    hintTypes.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//设置容错级别
    hintTypes.put(EncodeHintType.MARGIN, scanCodeProperties.getMargin());//设置外边距

    BitMatrix bitMatrix = new MultiFormatWriter().encode(uuid, BarcodeFormat.QR_CODE, scanCodeProperties.getWidth(), scanCodeProperties.getHeight(), hintTypes);

    //将二维码写入图片
    BufferedImage bufferedImage = new BufferedImage(scanCodeProperties.getWidth(), scanCodeProperties.getHeight(), BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < scanCodeProperties.getWidth(); i++) {
      for (int j = 0; j < scanCodeProperties.getHeight(); j++) {
        bufferedImage.setRGB(i, j, bitMatrix.get(i, j) ? Color.black.getRGB() : Color.WHITE.getRGB());
      }
    }

    return new ScanValidateCode(bufferedImage, uuid, scanCodeProperties.getExpireTime());
  }
}
