package org.open.job.starter.security.token;


import lombok.Data;

import java.io.Serializable;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description: token 实体类
 */
@Data
public class Token implements Serializable {

  private static final long serialVersionUID = 2756099126284764479L;

  private String accessToken;

  public Token(){}

  public Token(String accessToken) {
    this.accessToken = accessToken;
  }

}
