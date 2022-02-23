package org.open.job.starter.security.store;

import cn.hutool.core.util.RandomUtil;
import com.pro.crawler.common.vo.ResultEnum;
import com.pro.crawler.starter.security.authentication.Authentication;
import com.pro.crawler.starter.security.exception.AuthenticationException;
import com.pro.crawler.starter.security.properties.UserSecurityProperties;
import com.pro.crawler.starter.security.service.UserDetails;
import com.pro.crawler.starter.security.token.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description: Redis token store，把 token 及其映射存储到 Redis 中
 */
@Slf4j
public class RedisTokenStore implements TokenStore {

    private static final String ACCESS_TOKEN_KEY = "accessToken::";

    private final RedisTemplate<String, Object> redisTemplate;
    private final UserSecurityProperties securityProperties;

    public RedisTokenStore(RedisTemplate<String, Object> redisTemplate, UserSecurityProperties securityProperties) {
        this.redisTemplate = redisTemplate;
        this.securityProperties = securityProperties;
    }

    @Override
    public Token generateToken(Authentication authentication) {
        UserDetails userDetails = authentication.getUserDetails();
        return createToken(userDetails);
    }

    @Override
    public Boolean removeToken(String accessToken) {
        redisTemplate.opsForValue().getOperations().delete(buildAccessTokenKey(accessToken));
        return true;
    }

    @Override
    public Authentication readAuthentication(String accessToken) throws AuthenticationException{
        Object o = redisTemplate.opsForValue().get(buildAccessTokenKey(accessToken));
        if (Objects.isNull(o)){
            throw new AuthenticationException(ResultEnum.UNAUTHORIZED);
        }
        UserDetails userDetails = (UserDetails) o;
        Authentication authentication = new Authentication();
        authentication.setUserDetails(userDetails);
        return authentication;
    }

    private Token createToken(UserDetails userDetails){
        Token token = new Token();
        String accessToken = RandomUtil.randomString(32);
        redisTemplate.opsForValue().set(buildAccessTokenKey(accessToken), userDetails, securityProperties.getAccessTokenExpiresIn(), TimeUnit.SECONDS);
        token.setAccessToken(accessToken);
        return token;
    }

    private String buildAccessTokenKey(String accessToken){
        return ACCESS_TOKEN_KEY + accessToken;
    }



}
