package com.saucesubfresh.job.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lijunping on 2022/4/11
 */
@Data
@TableName("open_job_report")
public class OpenJobReportDO {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 任务执行总次数
     */
    private Integer taskExecTotalCount;

    /**
     * 任务执行成功总次数
     */
    private Integer taskExecSuccessCount;

    /**
     * 任务配置创建时间
     */
    private LocalDateTime createTime;

}
