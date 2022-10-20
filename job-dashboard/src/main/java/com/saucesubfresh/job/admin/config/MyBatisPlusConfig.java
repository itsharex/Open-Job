/*
 * Copyright © 2022 organization SauceSubFresh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.saucesubfresh.job.admin.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import com.saucesubfresh.job.admin.mybatis.PrintSqlInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis plus自动配置类
 *
 * @author lijunping
 */
@MapperScan(basePackages = {"com.saucesubfresh.job.admin.**.mapper"})
@EnableTransactionManagement
@Configuration
public class MyBatisPlusConfig {

  @Bean
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    final MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
    // 分页插件
    mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
    // 乐观锁插件
    mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
    // sql性能规范插件
    //mybatisPlusInterceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
    // 防止全表更新与删除插件
    mybatisPlusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
    return mybatisPlusInterceptor;
  }

  /**
   * SQL打印插件
   */
  @Bean
  @ConditionalOnClass(PrintSqlInterceptor.class)
  @ConditionalOnMissingBean(PrintSqlInterceptor.class)
  public PrintSqlInterceptor printSqlInterceptor() {
    return new PrintSqlInterceptor();
  }

}
