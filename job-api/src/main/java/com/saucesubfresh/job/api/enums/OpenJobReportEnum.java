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
package com.saucesubfresh.job.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lijunping on 2022/4/12
 */
@Getter
@AllArgsConstructor
public enum OpenJobReportEnum {

    TASK_EXEC_TOTAL_COUNT("任务调度总次数"),

    TASK_EXEC_SUCCESS_COUNT("任务调度成功总次数"),

    ;


    private final String name;
}