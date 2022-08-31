package com.saucesubfresh.job.admin.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lijunping on 2022/4/25
 */
@Data
public class AlarmMessage implements Serializable {

    private static final long serialVersionUID = 8540414931056416275L;

    /**
     * 任务 id
     */
    private Long jobId;

    /**
     * 任务执行失败原因
     */
    private String cause;

    /**
     * 任务日志创建时间
     */
    private LocalDateTime createTime;
}
