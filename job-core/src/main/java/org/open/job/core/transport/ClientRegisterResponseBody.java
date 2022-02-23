package org.open.job.core.transport;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Register client response body content
 */
@Data
@Accessors(chain = true)
public class ClientRegisterResponseBody {
    /**
     * ClientId generated during registration
     */
    private String clientId;
    /**
     * response status
     * <p>
     * If it is {@link MessageResponseStatus#SUCCESS}, the registration is successful
     */
    private MessageResponseStatus status = MessageResponseStatus.SUCCESS;
}
