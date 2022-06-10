package com.saucesubfresh.job.admin.dto.resp;

import lombok.Data;

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
public class OpenJobRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long appId;

    private String jobName;

    private String handlerName;

    private String cronExpression;

    private String params;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;

}
