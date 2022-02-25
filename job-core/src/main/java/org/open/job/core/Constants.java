package org.open.job.core;

/**
 * @author lijunping on 2022/2/25
 */
public interface Constants {

    /**
     * The name of the service registered by the client to nacos
     */
    String CLIENT_SERVICE_NAME = "job-client-services";

    /**
     * The name of the service registered by the client to zookeeper
     */
    String CLIENT_ROOT_PATH = "/JobClient";
}
