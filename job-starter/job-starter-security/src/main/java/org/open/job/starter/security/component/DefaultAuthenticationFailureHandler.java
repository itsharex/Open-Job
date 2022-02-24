package org.open.job.starter.security.component;

import lombok.extern.slf4j.Slf4j;
import org.open.job.starter.security.exception.AuthenticationException;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description: 默认的认证失败处理器
 */
@Slf4j
public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler{

    @Override
    public void onAuthenticationFailureHandler(AuthenticationException authenticationException) {
        log.error("[登录失败-失败原因]:{}",authenticationException.getMessage());
    }
}
