package org.open.job.admin.dto.update;

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
public class OpenJobUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String jobName;

    private String handlerName;

    private String cronExpression;

    private Integer status;

    private String params;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;

}
