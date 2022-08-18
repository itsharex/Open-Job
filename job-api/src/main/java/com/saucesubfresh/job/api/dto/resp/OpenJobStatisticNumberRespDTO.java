package com.saucesubfresh.job.api.dto.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lijunping on 2022/4/11
 */
@Data
@Accessors(chain = true)
public class OpenJobStatisticNumberRespDTO implements Serializable {

    /**
     * 任务总数量
     */
    private Integer taskTotalNum;
    /**
     * 任务运行数量
     */
    private Integer taskRunningNum;
    /**
     * 调度总次数
     */
    private Integer scheduleTotalNum;
    /**
     * 调度成功次数
     */
    private Integer scheduleSucceedNum;
    /**
     * 执行器总数量
     */
    private Integer executorTotalNum;
    /**
     * 执行器在线数量
     */
    private Integer executorOnlineNum;

}
