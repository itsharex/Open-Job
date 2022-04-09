package com.saucesubfresh.job.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("open_job_log")
public class OpenJobLogDO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;
	/**
	 * 任务 id
	 */
	private Long jobId;
	/**
	 * 任务执行状态（1 成功，0 失败）
	 */
	private Integer status;
	/**
	 * 任务失败原因
	 */
	private String cause;
	/**
	 * 任务日志创建时间
	 */
	private LocalDateTime createTime;

}
