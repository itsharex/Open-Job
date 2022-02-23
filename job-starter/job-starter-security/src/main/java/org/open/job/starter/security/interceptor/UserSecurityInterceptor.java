package org.open.job.starter.security.interceptor;


import com.pro.crawler.common.exception.ServiceException;
import com.pro.crawler.common.json.JSON;
import com.pro.crawler.common.vo.ResultEnum;
import com.pro.crawler.starter.security.authentication.Authentication;
import com.pro.crawler.starter.security.context.UserSecurityContext;
import com.pro.crawler.starter.security.context.UserSecurityContextHolder;
import com.pro.crawler.starter.security.store.TokenStore;
import com.pro.crawler.starter.security.utils.RequestUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
