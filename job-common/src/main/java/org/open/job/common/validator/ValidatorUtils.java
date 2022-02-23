package org.open.job.common.validator;


import org.open.job.common.exception.BaseException;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;


public interface ValidatorUtils {

  Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  /**
   * 对将要进行请求的 Bean 进行校验
   *
   * @param t      带校验的 bean
   * @param groups 校验组
   * @throws BaseException 校验失败抛出
   */
  static <T> void validate(T t, Class<?>... groups) throws BaseException {
    Set<ConstraintViolation<T>> validatedResult = validator.validate(t, groups);
    if (!validatedResult.isEmpty()) {
      ErrorMessage errorMessage = new ErrorMessage().setBeanClass(t.getClass());
      Set<ErrorMessageDetail> errorMessageDetailSet = validatedResult.stream().map(ValidatorUtils::buildErrorMessage).collect(Collectors.toSet());
      errorMessage.setDetailList(errorMessageDetailSet);
      throw new BaseException(errorMessage.toString());
    }
  }

  /**
   * 构造检验错误信息
   *
   * @param violation 校验结果
   * @return {@link ErrorMessageDetail} 错误信息
   */
  private static <T> ErrorMessageDetail buildErrorMessage(ConstraintViolation<T> violation) {
    Path propertyPath = violation.getPropertyPath();
    String message = violation.getMessage();
    Object invalidValue = violation.getInvalidValue();
    return new ErrorMessageDetail().setPropertyPath(propertyPath.toString()).setMessage(message).setInvalidValue(invalidValue);
  }

}