package org.open.job.starter.server;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * server configuration
 */
@Data
@ConfigurationProperties(prefix = "com.pro.crawler.server")
public class ServerConfiguration {
    /**
     * The server port
     */
    private int serverPort = 5200;
    /**
     * client expired executor pool size
     */
    private int expiredPoolSize = 5;
    /**
     * Time threshold for excluding clients
     */
    private long expiredExcludeThresholdSeconds = 30;
    /**
     * Check the client timeout interval in seconds
     */
    private long checkClientExpiredIntervalSeconds = 10;
    /**
     * The cluster model, @See BroadcastClusterInvoker，FailbackClusterInvoker, NormalClusterInvoker
     */
    private String clusterModel = "failover";
    /**
     * The loadBalance model, @See ConsistentHashLoadBalance，RandomWeightedLoadBalance
     */
    private String loadBalance = "consistentHash";
    /**
     * The store model, @See MemoryInstanceStore, RedissonInstanceStore
     */
    private String store;
    /**
     * Invoke retries
     */
    private int retryTimes = 3;
    /**
     * Time interval when retrying to invoke to Client, unit: millisecond
     *
     * @see java.util.concurrent.TimeUnit#MILLISECONDS
     */
    private long retryIntervalMilliSeconds = 1000;
}
