package org.open.job.starter.server.selector;

import lombok.extern.slf4j.Slf4j;
import org.open.job.core.enums.RegistryServiceType;
import org.open.job.core.exception.RpcException;
import org.open.job.starter.server.annotation.EnableJobServer;
import org.open.job.starter.server.discovery.support.NacosRegistryService;
import org.open.job.starter.server.discovery.support.ZookeeperRegistryService;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @author lijunping on 2022/1/20
 */
@Slf4j
public class RegistryServiceSelector implements ImportSelector {

    /**
     * The name of {@link RegistryServiceType} attributes in {@link EnableJobServer}
     */
    private static final String REGISTRAR_TYPE_ATTRIBUTE_NAME = "registryType";

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> annotationAttributes =
                importingClassMetadata.getAnnotationAttributes(EnableJobServer.class.getName());
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
