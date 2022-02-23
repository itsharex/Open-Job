package org.open.job.admin.dto.create;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 爬虫任务表
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@ApiModel("爬虫任务表")
@Data
public class TaskCreateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("爬虫 id")
    @NotNull(message = "spiderId 不能为空")
    private Long spiderId;

    @ApiModelProperty("cron 表达式")
    @NotBlank(message = "cron 表达式不能为空")
    private String cronExpression;

    @ApiModelProperty("任务执行状态（1 启动，0 停止）")
    private Integer status;

    @ApiModelProperty("任务创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("任务更新时间")
    private LocalDateTime updateTime;
    @ApiModelProperty("任务创建人")
    private Long createUser;
    @ApiModelProperty("任务更新人")
    private Long updateUser;

}
