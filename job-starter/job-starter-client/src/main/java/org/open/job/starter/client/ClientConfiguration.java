package org.open.job.starter.client;

import lombok.Data;
import org.open.job.common.internet.InternetAddressUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Related configuration items needed to build the client
 */
@Data
@ConfigurationProperties(prefix = "com.pro.crawler.client")
public class ClientConfiguration {
    /**
     * client host
     */
    private String clientAddress;
    /**
     * client port
     */
    private int clientPort = 5201;
    /**
     * Register the target server address
     */
    private String serverAddress = "localhost";
    /**
     * Register the target server port
     */
    private int serverPort = 5200;
    /**
     * Registration retries
     */
    private int retryRegisterTimes = 3;
    /**
     * Time interval when retrying to register to Server, unit: millisecond
     *
     * @see java.util.concurrent.TimeUnit#MILLISECONDS
     */
    private long retryRegisterIntervalMilliSeconds = 1000;
    /**
     * Time interval for sending heartbeat, unit: second
     *
     * @see java.util.concurrent.TimeUnit#SECONDS
     */
    private int heartBeatIntervalSeconds = 10;
    /**
     * Get local host
     *
     * @return local host
     */
    public String getClientAddress() {
        return InternetAddressUtils.getLocalIpByNetCard();
    }
}
