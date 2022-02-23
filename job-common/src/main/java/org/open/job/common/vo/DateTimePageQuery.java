package org.open.job.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日期时间分页查询
 *
 * @author lijunping
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DateTimePageQuery extends PageQuery implements Serializable {
  private static final long serialVersionUID = 2290788518820680025L;

  /**
   * 开始时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime beginTime;

  /**
   * 结束时间
   */
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime endTime = LocalDateTime.now();

}
