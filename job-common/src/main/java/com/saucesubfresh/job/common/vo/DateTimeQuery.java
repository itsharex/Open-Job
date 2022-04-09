package com.saucesubfresh.job.common.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lijunping
 */
@Data
public class DateTimeQuery implements Serializable {
  private static final long serialVersionUID = -5260392216936380499L;

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
