package com.saucesubfresh.job.admin.alarm;

import com.saucesubfresh.job.admin.domain.AlarmMessage;

/**
 * @author lijunping on 2022/8/31
 */
public interface AlarmService {

    /**
     * 发送报警消息
     *
     * @param alarmMessage
     */
    void sendAlarm(AlarmMessage alarmMessage);
}
