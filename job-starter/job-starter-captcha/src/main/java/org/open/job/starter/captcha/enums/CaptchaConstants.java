package org.open.job.starter.captcha.enums;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * @Description： 利用interface封装常量的好处是不用写public static final三个关键字
 * 因为interface中常量会默认加上这三个关键字
 */
public interface CaptchaConstants {
  /**
   * 默认的处理验证码的url前缀
   */
  String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/validate/code";
  /**
   * 生成图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
   */
  String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCaptcha";
  /**
   * 生成短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
   */
  String DEFAULT_PARAMETER_NAME_CODE_SMS = "captcha";
  /**
   * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
   */
  String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";
  /**
   * 设备 id
   */
  String DEFAULT_PARAMETER_NAME_DEVICEID = "deviceId";
  /**
   * 生成二维码 或 验证二维码时参数的名称
   */
  String DEFAULT_PARAMETER_NAME_SCAN = "scanCode";
  /**
   * 验证码保存到 redis 时的 Key
   */
  String VALIDATE_CODE_KEY_FOR_REDIS_PREFIX = "CODE::REDIS::";

}
