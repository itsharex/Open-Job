package org.open.job.core.transport;

import lombok.Data;
import lombok.experimental.Accessors;
import org.open.job.core.Message;

/**
 * The message request
 */
@Data
@Accessors(chain = true)
public class MessageRequestBody {
    /**
     * Unique number of message request
     */
    private String requestId;
    /**
     * ID of the client receiving the message
     */
    private String clientId;
    /**
     * The message subject of this consumption
     */
    private Message message;
}
