package org.open.job.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@Data
@TableName("task_log")
public class TaskLogDO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;
	/**
	 * 爬虫 id
	 */
	private Long taskId;
	/**
	 * 爬虫执行状态（1 成功，0 失败）
	 */
	private Integer status;
	/**
	 * 爬虫失败原因
	 */
	private String cause;
	/**
	 * 任务日志创建时间
	 */
	private LocalDateTime createTime;

}
