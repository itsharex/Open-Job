package com.saucesubfresh.job.admin.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lijunping on 2022/4/11
 */
@Getter
@AllArgsConstructor
public enum SystemScheduleEnum {

    REPORT("报表记录", -1L)

    ;

    private final String desc;

    private final Long value;
}
