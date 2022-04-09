package com.saucesubfresh.job.common.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author: 李俊平
 * @Date: 2022-02-26 15:32
 */
public interface LocalDateTimeUtil {

    /**
     * 将 毫秒级 时间戳转化为 {@link LocalDateTime}
     *
     * @param milliseconds milliseconds 毫秒级时间戳
     * @return {@link LocalDateTime}
     */
    static LocalDateTime toLocalDateTime(long milliseconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.systemDefault());
    }
}
