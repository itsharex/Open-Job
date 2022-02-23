package org.open.job.starter.client.annotation;

import org.open.job.core.enums.RegistryServiceType;
import org.open.job.starter.client.selector.RegistrarServiceSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lijunping on 2022/1/20
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RegistrarServiceSelector.class})
public @interface EnableJobClient {

    RegistryServiceType registryType() default RegistryServiceType.GRPC;
}
