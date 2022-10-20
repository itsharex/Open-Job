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

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lijunping on 2022/4/12
 */
@Data
public class OpenJobReportRespDTO implements Serializable {
    private static final long serialVersionUID = -6681150509700395747L;

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
