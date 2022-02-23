package org.open.job.starter.security.store;

import com.pro.crawler.starter.security.authentication.Authentication;
import com.pro.crawler.starter.security.token.Token;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description: JDBC token store，把 token 及 token 的映射保存到数据库中
 */
public class JdbcTokenStore implements TokenStore{
    @Override
    public Token generateToken(Authentication authentication) {
        return null;
    }

    @Override
    public Boolean removeToken(String accessToken) {
        return false;
    }

    @Override
    public Authentication readAuthentication(String accessToken) {
        return null;
    }
}
