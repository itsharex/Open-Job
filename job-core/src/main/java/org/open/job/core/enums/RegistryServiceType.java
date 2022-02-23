package org.open.job.core.enums;

/**
 * @author lijunping on 2022/1/20
 */
public enum RegistryServiceType {

    /**
     * Use grpc register to server
     */
    GRPC,
    /**
     * Use nacos client register to server
     */
    NACOS,
    /**
     * Use zookeeper client register to server
     */
    ZOOKEEPER,
    ;
}
