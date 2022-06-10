package com.saucesubfresh.job.admin.domain;

import com.saucesubfresh.rpc.core.Message;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lijunping on 2022/6/10
 */
@Data
public class ScheduleJob implements Serializable {
    private static final long serialVersionUID = -4675286965727768773L;
    
    private String appName;
    
    private Message message;
}
