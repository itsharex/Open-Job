package org.open.job.starter.captcha.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.open.job.starter.captcha.core.domain.ValidateCode;
import org.open.job.starter.captcha.enums.ValidateCodeType;
import org.open.job.starter.captcha.exception.ValidateCodeException;
import org.open.job.starter.captcha.generator.ValidateCodeGenerator;
import org.open.job.starter.captcha.repository.ValidateCodeRepository;
import org.open.job.starter.captcha.request.CaptchaGenerateRequest;
import org.open.job.starter.captcha.request.CaptchaVerifyRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 *
 * Description: 验证码抽象处理器，包含验证码的生成处理，保存处理，发送处理，验证处理
 */
@Slf4j
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

  /**
   * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
   */
  @Autowired
  private Map<String, ValidateCodeGenerator> validateCodeGenerators;

  @Autowired
  private ValidateCodeRepository validateCodeRepository;

  @Autowired
  private HttpServletResponse response;


  /**
   * send 方法是一个抽象方法，所以可以不用实现，而是由子类实现
   * abstract void send
   */
  @Override
  public void create(CaptchaGenerateRequest captchaGenerateRequest) throws Exception {
    C validateCode = generate();
    save(captchaGenerateRequest.getDeviceId(), validateCode);
    send(response, captchaGenerateRequest, validateCode);
  }

  /**
   * 生成校验码
   */
  @SuppressWarnings("unchecked")
  private C generate() throws Exception {
    String type = getValidateCodeType().toString().toLowerCase();
    String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
    ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
    if (validateCodeGenerator == null) {
      throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
    }
    return (C) validateCodeGenerator.generate();
  }

  /**
   * 保存校验码
   *
   * @param deviceId
   * @param validateCode ValidateCodeRepository 接口在系统中有两个实现类 SessionValidateCodeRepository implements ValidateCodeRepository 和 RedisValidateCodeRepository implements ValidateCodeRepository
   *                     这两个类都是通过 @Component 的方式注入进来的
   */
  private void save(String deviceId, C validateCode) {

    ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
    //使用validateCodeRepository接口方法保存验证码
    validateCodeRepository.save(deviceId, code, getValidateCodeType());
  }

  /**
   * 发送校验码，由子类实现
   *
   * @param response
   * @param captchaGenerateRequest
   * @param validateCode
   * @throws Exception
   */
  protected abstract void send(HttpServletResponse response, CaptchaGenerateRequest captchaGenerateRequest, C validateCode) throws Exception;


  @SuppressWarnings("unchecked")
  @Override
  public void validate(CaptchaVerifyRequest request) {

    ValidateCodeType codeType = getValidateCodeType();
    //获取验证码
    C validateCode = (C) validateCodeRepository.get(request.getDeviceId(), codeType);

    String codeInRequest = request.getCode();

    if (StringUtils.isBlank(codeInRequest)) {
      throw new ValidateCodeException(codeType + "验证码的值不能为空");
    }

    if (validateCode == null) {
      throw new ValidateCodeException(codeType + "验证码不存在");
    }

    if (validateCode.isExpired()) {
      //验证码过期将验证码移出
      validateCodeRepository.remove(request.getDeviceId(), codeType);
      throw new ValidateCodeException(codeType + "验证码已过期");
    }

    if (!StringUtils.equals(validateCode.getCode(), codeInRequest)) {
      throw new ValidateCodeException(codeType + "验证码不匹配");
    }
    //校验成功后将验证码移出
    validateCodeRepository.remove(request.getDeviceId(), codeType);

  }

  /**
   * 根据请求的url获取校验码的类型
   * 因为 AbstractValidateCodeProcessor 被继承的类有两个，分别是 SmsCodeProcessor 和 ImageCodeProcessor
   * 根据上面的 type getClass().getSimpleName() 得到的是 SmsCodeProcessor，截去 CodeProcessor 剩 Sms
   */
  private ValidateCodeType getValidateCodeType() {
    String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
    return ValidateCodeType.valueOf(type.toUpperCase());
  }


}
