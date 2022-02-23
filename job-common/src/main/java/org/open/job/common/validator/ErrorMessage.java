package org.open.job.common.validator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.open.job.common.json.JSON;

import java.util.Set;

@Data
@Accessors(chain = true)
public class ErrorMessage {
  @JsonProperty("错误Bean")
  private Class<?> beanClass;
  @JsonProperty("错误详细信息列表")
  private Set<ErrorMessageDetail> detailList;

  @Override
  public String toString() {
    return JSON.toJSON(this);
  }
}
