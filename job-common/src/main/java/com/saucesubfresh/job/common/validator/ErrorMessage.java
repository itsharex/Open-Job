/*
 * Copyright © 2022 organization SauceSubFresh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.saucesubfresh.job.common.validator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import com.saucesubfresh.job.common.json.JSON;

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
