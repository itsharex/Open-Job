package org.open.job.common.exception;

/**
 * 基类检查异常
 *
 * @author lijunping
 */
public class BaseCheckedException extends Exception {
  private static final long serialVersionUID = 3470726438554407056L;

  /**
   * 错误码
   */
  private Integer code = 500;

  public BaseCheckedException(String message) {
    super(message);
  }

  public BaseCheckedException(Throwable cause) {
    super(cause.getMessage());
  }

  public BaseCheckedException(Integer code, String message) {
    this(message);
    this.code = code;
  }

  public Integer getCode() {
    return code;
  }

  /**
   * 重写此方法提升性能
   */
  @Override
  public Throwable fillInStackTrace() {
    return this;
  }
}
