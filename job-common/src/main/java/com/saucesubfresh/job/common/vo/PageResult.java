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
package com.saucesubfresh.job.common.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author lijunping
 */
@ToString
@Getter
@EqualsAndHashCode
@Builder(builderMethodName = "newBuilder")
public class PageResult<T> implements Serializable {
  private static final long serialVersionUID = 1830968869656033585L;

  /**
   * 查询数据列表
   */
  private final List<T> records;

  /**
   * 总数
   */
  private final Long total;

  /**
   * 当前页
   */
  private final Long current;

  /**
   * 总页数
   */
  private final Long pages;

  /**
   * 是否存在上一页
   */
  private Boolean hasPrevious;

  /**
   * 是否存在下一页
   */
  private Boolean hasNext;

  /**
   * 构建分页结果对象
   *
   * @param iPage IPage<T>
   * @param <T>   分页对象
   * @return PageResult<T>
   */
  public static <T> PageResult<T> build(IPage<T> iPage) {
    PageResult<T> pageResult = PageResult.<T>newBuilder()
        .records(iPage.getRecords())
        .total(iPage.getTotal())
        .current(iPage.getCurrent())
        .pages(iPage.getPages())
        .build();
    pageResult.setHasPrevious(pageResult.hasPrevious()).setHasNext(pageResult.hasNext());
    return pageResult;
  }

  private PageResult<T> setHasNext(Boolean hasNext) {
    this.hasNext = hasNext;
    return this;
  }

  private PageResult<T> setHasPrevious(Boolean hasPrevious) {
    this.hasPrevious = hasPrevious;
    return this;
  }

  /**
   * 是否存在上一页
   *
   * @return true / false
   */
  private boolean hasPrevious() {
    return this.current > 1;
  }

  /**
   * 是否存在下一页
   *
   * @return true / false
   */
  private boolean hasNext() {
    return this.current < this.getPages();
  }
}
