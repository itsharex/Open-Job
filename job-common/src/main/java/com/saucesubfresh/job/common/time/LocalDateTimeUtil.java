package com.saucesubfresh.job.common.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author: 李俊平
 * @Date: 2022-02-26 15:32
 */
public interface LocalDateTimeUtil {

    String DATETIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将 毫秒级 时间戳转化为 {@link LocalDateTime}
     *
     * @param milliseconds milliseconds 毫秒级时间戳
     * @return {@link LocalDateTime}
     */
    static LocalDateTime toLocalDateTime(long milliseconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.systemDefault());
    }

    /**
     * Date转换为LocalDateTime
     */
    static LocalDateTime convertDateToLDT(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param time
     * @return
     */
    static Date convertLDTToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将时间字符串转为自定义时间格式的LocalDateTime
     * @param time 需要转化的时间字符串
     * @param format 自定义的时间格式
     * @return
     */
    static LocalDateTime parse(String time, String format) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(format));
    }

    /**
     * 将LocalDateTime转为自定义的时间格式的字符串
     * @param localDateTime 需要转化的LocalDateTime
     * @param format 自定义的时间格式
     * @return
     */
    static String format(LocalDateTime localDateTime, String format) {
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }
}
