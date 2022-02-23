package org.open.job.starter.security.context;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description: 上下文对象
 */
@Data
@Accessors(chain = true)
public class UserSecurityContext implements Serializable {
  private static final long serialVersionUID = 3069248878500028106L;
  private Long id;

  private String mobile;

  private String password;

  private String username;

}
