package org.open.job.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 爬虫任务表
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Data
@TableName("open_job")
public class OpenJobDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 绑定的 handler 的名字
     */
    private String handlerName;
    /**
     * cron 表达式
     */
    private String cronExpression;
    /**
     * 任务执行状态（1 启动，0 停止）
     */
    private Integer status;
    /**
     * 任务创建时间
     */
    private LocalDateTime createTime;
    /**
     * 任务更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 任务创建人
     */
    private Long createUser;
    /**
     * 任务更新人
     */
    private Long updateUser;

}
