package com.saucesubfresh.job.core.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author lijunping on 2022/3/28
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JobHandler {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";
}
