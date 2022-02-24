package org.open.job.starter.server.annotation;


import org.open.job.core.enums.RegistryServiceType;
import org.open.job.starter.server.selector.RegistryServiceSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RegistryServiceSelector.class})
public @interface EnableJobServer {

    /**
     * Configure the way to pull the client
     *
     * @return {@link RegistryServiceType} instance
     */
    RegistryServiceType registryType() default RegistryServiceType.NACOS;
}
