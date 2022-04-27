package com.saucesubfresh.job.common.vo;

/**
 * 结果枚举
 *
 * @author lijunping
 */
public enum ResultEnum {

  SUCCESS(200, "成功"),

  ERROR(500, "系统异常"),

  UNKNOWN(999, "未知错误"),

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
