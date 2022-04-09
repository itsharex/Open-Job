package com.saucesubfresh.job.common.exception;


/**
 * 基类异常
 *
 * @author lijunping
 */
public class BaseException extends RuntimeException {
  private static final long serialVersionUID = 759363456812320490L;

  /**
   * 错误码
   */
  private Integer code = 500;

  public BaseException(String msg) {
    super(msg);
  }

  public BaseException(Integer code, String msg) {
    super(msg);
    this.code = code;
  }

  public BaseException(Exception e) {
    this(e.getMessage());
  }

  public Integer getCode() {
    return code;
  }

  /**
   * 重写此方法优化性能
   */
  @Override
  public Throwable fillInStackTrace() {
    return this;
  }

}
