package com.saucesubfresh.job.admin.common.enums;

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
