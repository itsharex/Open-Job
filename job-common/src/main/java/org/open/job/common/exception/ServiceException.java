package org.open.job.common.exception;

import org.open.job.common.vo.ResultEnum;

/**
 * 业务逻辑异常 Exception
 *
 * @author lijunping
 */
public class ServiceException extends BaseException {
  private static final long serialVersionUID = 3853665521565241541L;

  public ServiceException(String msg) {
    super(msg);
  }

  public ServiceException(ResultEnum resultEnum) {
    super(resultEnum.getCode(), resultEnum.getMsg());
  }

  public ServiceException(Integer code, String msg) {
    super(code, msg);
  }

  public ServiceException(Exception e) {
    super(e);
  }

}
