package com.saucesubfresh.job.admin.dto.resp;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lijunping on 2022/4/12
 */
@Data
public class OpenJobReportRespDTO implements Serializable {
    private static final long serialVersionUID = -6681150509700395747L;

    private Long id;

    /**
     * 任务执行总次数
     */
    private Integer taskExecTotalCount;

    /**
     * 任务执行成功总次数
     */
    private Integer taskExecSuccessCount;

    /**
     * 任务配置创建时间
     */
    private LocalDateTime createTime;
}
