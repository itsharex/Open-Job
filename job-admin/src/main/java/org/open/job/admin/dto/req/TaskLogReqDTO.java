package org.open.job.admin.dto.req;

import lombok.Data;
import org.open.job.common.vo.DateTimePageQuery;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 爬虫任务运行日志
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Data
public class TaskLogReqDTO extends DateTimePageQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long taskId;

    private Integer status;

    private String cause;

    private LocalDateTime createTime;

}