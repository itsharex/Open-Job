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
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lijunping on 2022/4/11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpenJobStatisticRespDTO implements Serializable {

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
     * 调度总次数
     */
    private Integer scheduleTotalNum;
    /**
     * 调度成功次数
     */
    private Integer scheduleSucceedNum;


}
