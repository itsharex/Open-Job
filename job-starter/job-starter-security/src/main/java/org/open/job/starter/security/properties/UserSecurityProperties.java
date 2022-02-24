package org.open.job.starter.security.properties;

import io.jsonwebtoken.io.Decoders;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description: 安全相关配置
 */
@Data
@ConfigurationProperties(prefix = "org.open.job.security")
public class UserSecurityProperties {

  private static final String[] DEFAULT_IGNORE_PATHS = new String[]{
      // Swagger 相关
      "/doc.html", "/swagger-resources", "/swagger-resources/**", "/webjars/**", "v2/**", "/favicon.ico",
      // Actuator 相关

      // 登录相关
      "/login/**", "/validate/code/**"
  };

  /**
   * 自定义忽略 Path
   */
  private String[] ignorePaths = new String[0];
  /**
   * 默认忽略 Path
   */
  private String[] defaultIgnorePaths = DEFAULT_IGNORE_PATHS;

  /***
   * 指定使用哪个TokenStore
   */
  private String store;

  /**
   * accessToken 有效期，单位秒，默认 2 小时
   */
  private Integer accessTokenExpiresIn = 604800;

  /**
   * jwt 加密密钥 key
   */
  private String secretKey = "ThisIsKeyThisIsKeyThisIsKeyThisIsKeyThisIsKeyThisIsKey";

  /**
   * secret key byte array.
   */
  private byte[] secretKeyBytes;

  public String[] getIgnorePaths() {
    return ignorePaths;
  }

  public UserSecurityProperties setIgnorePaths(String[] ignorePaths) {
    this.ignorePaths = ignorePaths;
    return this;
  }

  public String[] getDefaultIgnorePaths() {
    return defaultIgnorePaths;
  }

  public UserSecurityProperties setDefaultIgnorePaths(String[] defaultIgnorePaths) {
    this.defaultIgnorePaths = defaultIgnorePaths;
    return this;
  }

  public byte[] getSecretKeyBytes() {
    if (secretKeyBytes == null && secretKey != null) {
      secretKeyBytes = Decoders.BASE64.decode(secretKey);
    }
    return secretKeyBytes;
  }

}
