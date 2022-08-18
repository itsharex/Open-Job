package com.saucesubfresh.job.api.dto.req;

import lombok.Data;
import com.saucesubfresh.job.common.vo.DateTimePageQuery;

import java.io.Serializable;

/**
 * 任务运行日志
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Data
public class OpenJobLogReqDTO extends DateTimePageQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long jobId;

    private Integer status;

    private String cause;

}
