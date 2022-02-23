package org.open.job.admin.dto.create;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 爬虫任务运行日志
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@ApiModel("爬虫任务运行日志")
@Data
public class TaskLogCreateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;
    @ApiModelProperty("任务 id")
    private Long taskId;
    @ApiModelProperty("任务执行状态（1 成功，0 失败）")
    private Integer status;
    @ApiModelProperty("爬虫失败原因")
    private String cause;
    @ApiModelProperty("任务日志创建时间")
    private LocalDateTime createTime;

}
