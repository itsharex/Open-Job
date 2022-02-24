package org.open.job.starter.client.selector;

import lombok.extern.slf4j.Slf4j;
import org.open.job.core.enums.RegistryServiceType;
import org.open.job.core.exception.RpcException;
import org.open.job.starter.client.annotation.EnableJobClient;
import org.open.job.starter.client.registry.support.NacosRegistryService;
import org.open.job.starter.client.registry.support.ZookeeperRegistryService;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @author lijunping on 2022/1/20
 */
@Slf4j
public class RegistrarServiceSelector implements ImportSelector {

    /**
     * The name of {@link RegistryServiceType} attributes in {@link EnableJobClient}
     */
    private static final String REGISTRAR_TYPE_ATTRIBUTE_NAME = "registryType";

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> annotationAttributes =
                importingClassMetadata.getAnnotationAttributes(EnableJobClient.class.getName());
        RegistryServiceType serverServiceType = (RegistryServiceType) annotationAttributes.get(REGISTRAR_TYPE_ATTRIBUTE_NAME);
        log.info("Use the [{}] method to register the Client service", serverServiceType);
        switch (serverServiceType) {
            case NACOS:
                return new String[]{NacosRegistryService.class.getName()};
            case ZOOKEEPER:
                return new String[]{ZookeeperRegistryService.class.getName()};
        }
        throw new RpcException("Unsupported ServerServiceType：" + serverServiceType);
    }
}
