package org.open.job.admin.dto.req;

import lombok.Data;
import org.open.job.common.vo.DateTimePageQuery;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 任务表
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */

@Data
public class OpenJobReqDTO extends DateTimePageQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    private String jobName;

    private String handlerName;

    private Integer status;

}
