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
package com.saucesubfresh.job.admin.mybatis;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * sql 执行控制台打印
 *
 * @author lijunping
 */
@Slf4j
@Intercepts({
  @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
  @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
    RowBounds.class, ResultHandler.class})})
public class PrintSqlInterceptor implements Interceptor {

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
    Object parameter = null;
    if (invocation.getArgs().length > 1) {
      parameter = invocation.getArgs()[1];
    }
    String sqlId = mappedStatement.getId();
    BoundSql boundSql = mappedStatement.getBoundSql(parameter);
    Configuration configuration = mappedStatement.getConfiguration();
    long start = System.currentTimeMillis();
    Object returnValue = invocation.proceed();
    long time = System.currentTimeMillis() - start;
    showSql(configuration, boundSql, time, sqlId);
    return returnValue;
  }

  private static void showSql(Configuration configuration, BoundSql boundSql, long time, String sqlId) {
    Object parameterObject = boundSql.getParameterObject();
    List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
    //替换空格、换行、tab缩进等
    String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
    if (!parameterMappings.isEmpty() && parameterObject != null) {
      TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
      if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
        sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
      } else {
        MetaObject metaObject = configuration.newMetaObject(parameterObject);
        for (ParameterMapping parameterMapping : parameterMappings) {
          String propertyName = parameterMapping.getProperty();
          if (metaObject.hasGetter(propertyName)) {
            Object obj = metaObject.getValue(propertyName);
            sql = sql.replaceFirst("\\?", getParameterValue(obj));
          } else if (boundSql.hasAdditionalParameter(propertyName)) {
            Object obj = boundSql.getAdditionalParameter(propertyName);
            sql = sql.replaceFirst("\\?", getParameterValue(obj));
          }
        }
      }
    }
    logs(time, sql, sqlId);
  }

  private static String getParameterValue(Object obj) {
    String value;
    if (obj instanceof String) {
      value = "'" + obj.toString() + "'";
    } else if (obj instanceof Date) {
      DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
      value = "'" + formatter.format(new Date()) + "'";
    } else {
      if (obj != null) {
        value = obj.toString();
      } else {
        value = "";
      }
    }
    return value.replace("$", "\\$");
  }

  private static void logs(long time, String sql, String sqlId) {
    StringBuilder sb = new StringBuilder()
      .append(StringPool.NEWLINE)
      .append(" Time：").append(time)
      .append(" ms - ID：").append(sqlId)
      .append(StringPool.NEWLINE)
      .append("Execute SQL：")
      .append(StringPool.NEWLINE)
      .append(sql)
      .append(StringPool.NEWLINE);
    log.info(sb.toString());
  }

  @Override
  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  @Override
  public void setProperties(Properties properties0) {
  }
}
