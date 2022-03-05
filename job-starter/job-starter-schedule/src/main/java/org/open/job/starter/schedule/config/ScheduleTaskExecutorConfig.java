package org.open.job.starter.schedule.config;

import org.open.job.starter.schedule.core.support.RedisScheduleTaskManage;
import org.open.job.starter.schedule.core.ScheduleTaskManage;
import org.open.job.starter.schedule.executor.DefaultScheduleTaskExecutor;
import org.open.job.starter.schedule.executor.ScheduleTaskExecutor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description: 定时任务执行器配置类
 */
@Configuration
public class ScheduleTaskExecutorConfig {

  @Bean
  @ConditionalOnMissingBean(ScheduleTaskManage.class)
  @ConditionalOnBean(RedisTemplate.class)
  public ScheduleTaskManage scheduleTaskManage(RedisTemplate<String, Object> redisTemplate){
    return new RedisScheduleTaskManage(redisTemplate);
  }

  @Bean
  @ConditionalOnMissingBean
  public ScheduleTaskExecutor scheduleTaskExecutor(){
    return new DefaultScheduleTaskExecutor();
  }
}
