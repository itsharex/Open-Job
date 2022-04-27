package com.saucesubfresh.job.admin.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lijunping on 2022/4/11
 */
@Getter
@AllArgsConstructor
public enum SystemScheduleEnum {

    REPORT("报表记录", -1L),

    CLEAR_LOG("定时清除任务日志和采集日志", -2L),

    ALARM_MESSAGE("定时发送钉钉报警", -3L),

    ;

    private final String desc;

    private final Long value;
}
