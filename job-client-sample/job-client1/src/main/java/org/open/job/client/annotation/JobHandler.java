package org.open.job.client.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author lijunping on 2022/3/28
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface JobHandler {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";
}
