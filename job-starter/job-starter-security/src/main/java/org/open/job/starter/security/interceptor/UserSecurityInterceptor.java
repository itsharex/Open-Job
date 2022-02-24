package org.open.job.starter.security.interceptor;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.open.job.common.exception.ServiceException;
import org.open.job.common.json.JSON;
import org.open.job.common.vo.ResultEnum;
import org.open.job.starter.security.authentication.Authentication;
import org.open.job.starter.security.context.UserSecurityContext;
import org.open.job.starter.security.context.UserSecurityContextHolder;
import org.open.job.starter.security.store.TokenStore;
import org.open.job.starter.security.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description: 认证拦截器
 */
@Slf4j
@Data
@Component
public class UserSecurityInterceptor implements HandlerInterceptor {

  @Autowired
  private TokenStore tokenStore;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    try {
      String accessToken = RequestUtils.extractTokenFromHeader(request);
      Authentication authentication = tokenStore.readAuthentication(accessToken);
      UserSecurityContext user = JSON.parse(authentication.getUserDetails(), UserSecurityContext.class);
      UserSecurityContextHolder.setContext(user);
      return true;
    }catch (Exception e){
      throw new ServiceException(ResultEnum.UNAUTHORIZED);
    }
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    UserSecurityContextHolder.clear();
  }
}
