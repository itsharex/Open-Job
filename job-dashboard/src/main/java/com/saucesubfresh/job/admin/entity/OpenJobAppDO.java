package com.saucesubfresh.job.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lijunping on 2022/6/10
 */
@Data
@TableName("open_job_app")
public class OpenJobAppDO implements Serializable {
    private static final long serialVersionUID = 598192183560573801L;

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * appName
     */
    private String appName;
    /**
     * appDesc
     */
    private String appDesc;
    /**
     * 任务创建人
     */
    private Long createUser;
    /**
     * 任务日志创建时间
     */
    private LocalDateTime createTime;
}
