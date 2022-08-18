package com.saucesubfresh.job.api.dto.req;

import com.saucesubfresh.job.common.vo.DateTimePageQuery;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 任务运行日志
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Data
public class OpenJobAppReqDTO extends DateTimePageQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String appName;

    private String appDesc;

    private Long createUser;

    private LocalDateTime createTime;

}
