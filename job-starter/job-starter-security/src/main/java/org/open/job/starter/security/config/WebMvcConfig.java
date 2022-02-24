package org.open.job.starter.security.config;


import org.open.job.starter.security.interceptor.UserSecurityInterceptor;
import org.open.job.starter.security.properties.UserSecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description: 拦截器相关配置，在 WebAutoConfiguration 之后自动配置，保证过滤器的顺序
 */
@Configuration
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties(UserSecurityProperties.class)
public class WebMvcConfig implements WebMvcConfigurer {
  private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

  private final UserSecurityProperties properties;
  private final UserSecurityInterceptor userSecurityInterceptor;

  public WebMvcConfig(UserSecurityProperties properties, UserSecurityInterceptor userSecurityInterceptor) {
    this.properties = properties;
    this.userSecurityInterceptor = userSecurityInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(userSecurityInterceptor)
      .excludePathPatterns(properties.getIgnorePaths())
      .excludePathPatterns(properties.getDefaultIgnorePaths());
    logger.info("[addInterceptors][加载 UserSecurityInterceptor 拦截器完成]");
  }

}
