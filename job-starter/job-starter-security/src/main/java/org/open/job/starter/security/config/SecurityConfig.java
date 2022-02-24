package org.open.job.starter.security.config;

import lombok.AllArgsConstructor;
import org.open.job.starter.security.component.AuthenticationFailureHandler;
import org.open.job.starter.security.component.AuthenticationSuccessHandler;
import org.open.job.starter.security.component.DefaultAuthenticationFailureHandler;
import org.open.job.starter.security.component.DefaultAuthenticationSuccessHandler;
import org.open.job.starter.security.properties.UserSecurityProperties;
import org.open.job.starter.security.store.support.JdbcTokenStore;
import org.open.job.starter.security.store.support.JwtTokenStore;
import org.open.job.starter.security.store.support.RedisTokenStore;
import org.open.job.starter.security.store.TokenStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description: 安全相关配置
 */
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(UserSecurityProperties.class)
public class SecurityConfig {

    private final UserSecurityProperties userSecurityProperties;
    private final RedisTemplate<String, Object> redisTemplate;

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new DefaultAuthenticationSuccessHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new DefaultAuthenticationFailureHandler();
    }

    @Bean
    @ConditionalOnExpression("'jwt'.equals('${org.open.job.security.store:jwt}')")
    public TokenStore jwtTokenStore(){
        return new JwtTokenStore(userSecurityProperties);
    }

    @Bean
    @ConditionalOnExpression("'redis'.equals('${org.open.job.security.store:redis}')")
    public TokenStore redisTokenStore(){
        return new RedisTokenStore(redisTemplate, userSecurityProperties);
    }

    @Bean
    @ConditionalOnExpression("'jdbc'.equals('${org.open.job.security.store:jdbc}')")
    public TokenStore jdbcTokenStore(){
        return new JdbcTokenStore();
    }

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
