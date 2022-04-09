package com.saucesubfresh.job.common.vo;

/**
 * 结果枚举
 * 全局错误码，占用 [0, 999]
 * 业务异常错误码，占用 [1 000 000 000, +∞)
 *
 * @author lijunping
 */
public enum ResultEnum {

  // ========== 客户端错误段 ==========

  SUCCESS(200, "成功"),

  BAD_REQUEST(400, "请求参数不正确"),

  UNAUTHORIZED(401, "Token 已过期或账号未登录"),

  FORBIDDEN(403, "没有该操作权限"),

  NOT_FOUND(404, "请求未找到"),

  METHOD_NOT_ALLOWED(405, "请求方法不正确"),


  // ========== 服务端错误段 ==========

  ERROR(500, "系统异常"),

  UNKNOWN(999, "未知错误"),

  TOO_MANY_REQUEST(1000, "太多请求了，请稍后再试"),

  DEGRADE_EXCEPTION(1001, "服务降级了，请稍后再试"),

  TOO_MANY_HOT_REQUEST(1002, "热点太多请求了，请稍后再试"),

  SYSTEM_EXCEPTION(1003, "系统规则（负载）不满足规则，请稍后再试"),

  AUTHORITY_EXCEPTION(1004, "太多请求了，请稍后再试"),

  // ========== 业务错误段 ==========
  USERNAME_OR_PASSWORD_ERROR(1005, "用户名或密码错误"),

  CAPTCHA_ERROR(1006, "验证码输入错误"),
  ;

  private final Integer code;

  private final String msg;

  ResultEnum(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public Integer getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }

}
