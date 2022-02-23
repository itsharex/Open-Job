package org.open.job.core.transport;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * The client sends the response entity of the heartbeat check
 */
@Data
@Accessors(chain = true)
public class ClientHeartBeatResponseBody {
    /**
     * The client id
     */
    private String clientId;
    /**
     * heartbeat time
     * <p>
     * time unit：milliseconds
     */
    private long heartbeatTimeMillis;
    /**
     * resposne status
     */
    private MessageResponseStatus status = MessageResponseStatus.SUCCESS;
}
