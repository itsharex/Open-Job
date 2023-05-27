/*
 * Copyright © 2022 organization SauceSubFresh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.saucesubfresh.job.api.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lijunping on 2022/4/11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpenJobStatisticRespDTO implements Serializable {

    /**
     * 应用数量
     */
    private Integer appNum;
    /**
     * 报警数量
     */
    private Integer alarmNum;
    /**
     * 任务总数量
     */
    private Integer taskTotalNum;
    /**
     * 任务运行数量
     */
    private Integer taskRunningNum;
    /**
     * 执行器总数量
     */
    private Integer executorTotalNum;
    /**
     * 执行器在线数量
     */
    private Integer executorOnlineNum;
    /**
     * 在线状态
     */
    private String status;
    /**
     * 任务最近一次执行时间
     */
    private LocalDateTime lastRunTime;
    /**
     * 任务最近一次任务状态时间
     */
    private LocalDateTime stateChangeTime;
    /**
     * 运行时长
     */
    private String liveTime;
    /**
     * cpu使用信息
     */
    private String cpuInfo;
    /**
     * 内存使用信息
     */
    private String memoryInfo;
    /**
     * 磁盘使用信息
     */
    private String diskInfo;


}
