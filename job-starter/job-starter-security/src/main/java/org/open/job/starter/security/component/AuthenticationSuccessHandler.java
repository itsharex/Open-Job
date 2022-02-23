package org.open.job.starter.security.component;

import com.pro.crawler.starter.security.authentication.Authentication;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description: 认证成功处理器
 */
public interface AuthenticationSuccessHandler {

    /**
     * 登录成功处理
     */
    void onAuthenticationSuccess(Authentication authentication);
}
