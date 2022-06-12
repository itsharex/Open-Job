package com.saucesubfresh.job.core.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author: 李俊平
 * @Date: 2022-06-12 17:05
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JobHandlerForMethod {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";
}
