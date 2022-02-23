package org.open.job.starter.captcha.core.scan;

import lombok.Data;
import org.open.job.starter.captcha.core.domain.ValidateCode;

import java.awt.image.BufferedImage;


/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * 二维码
 */
@Data
public class ScanValidateCode extends ValidateCode {

  private static final long serialVersionUID = -8359430591111380080L;
  /**
   * 图片
   */
  private BufferedImage image;

    /**
     * 构造-----expireIn 超时时间（单位秒）
     * @param image
     * @param code
     * @param expireIn
     */
    public ScanValidateCode(BufferedImage image, String code, int expireIn) {
        super(code, expireIn);
        this.image = image;
    }

}
