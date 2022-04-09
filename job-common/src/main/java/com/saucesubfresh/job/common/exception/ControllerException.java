package com.saucesubfresh.job.common.exception;

/**
 * 控制器异常
 *
 * @author lijunping
 */
public class ControllerException extends BaseException {
  private static final long serialVersionUID = -1912299385804998816L;

  public ControllerException(String msg) {
    super(msg);
  }

  public ControllerException(Integer code, String msg) {
    super(code, msg);
  }

  public ControllerException(Exception e) {
    super(e);
  }
}
