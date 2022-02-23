package org.open.job.starter.captcha.repository.redis;

import org.apache.commons.lang3.StringUtils;
import org.open.job.starter.captcha.core.domain.ValidateCode;
import org.open.job.starter.captcha.enums.CaptchaConstants;
import org.open.job.starter.captcha.enums.ValidateCodeType;
import org.open.job.starter.captcha.exception.ValidateCodeException;
import org.open.job.starter.captcha.repository.ValidateCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 *
 * Description：使用redis+deviceId的方式进行验证码的存、取、删
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {
  
  @Autowired
  private RedisTemplate<String, Object> redisTemplate;


  @Override
  public void save(String deviceId, ValidateCode code, ValidateCodeType type) {
    redisTemplate.opsForValue().set(buildKey(deviceId, type), code, 30, TimeUnit.MINUTES);
  }

  @Override
  public ValidateCode get(String deviceId, ValidateCodeType type) {
    Object value = redisTemplate.opsForValue().get(buildKey(deviceId, type));
    if (value == null) {
      return null;
    }
    return (ValidateCode) value;
  }

  @Override
  public void remove(String deviceId, ValidateCodeType type) {
    redisTemplate.delete(buildKey(deviceId, type));
  }

  /**
   * 构建验证码在redis中的key ---- 该key类似与cookie
   *
   * @param deviceId
   * @param type
   * @return
   */
  private String buildKey(String deviceId, ValidateCodeType type) {
    if (StringUtils.isBlank(deviceId)) {
      throw new ValidateCodeException("请在请求头中携带deviceId参数");
    }
    return CaptchaConstants.VALIDATE_CODE_KEY_FOR_REDIS_PREFIX + type.toString().toUpperCase() + "::" + deviceId;
  }
}
