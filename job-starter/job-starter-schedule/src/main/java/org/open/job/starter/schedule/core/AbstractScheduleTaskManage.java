package org.open.job.starter.schedule.core;

import lombok.SneakyThrows;
import org.open.job.starter.schedule.cron.CronExpression;

import java.text.ParseException;
import java.util.Date;

/**
 * @author lijunping on 2022/1/20
 */
public abstract class AbstractScheduleTaskManage implements ScheduleTaskManage{

    protected static final Long UNIT = 1000L;
    /**
     * 获取下次执行的时间
     * @param cronExpression cron 表达式
     * @return
     * @throws ParseException
     */
    @SneakyThrows
    protected long generateNextValidTime(String cronExpression){
        Date date = new Date();
        Date nextDate = new CronExpression(cronExpression).getNextValidTimeAfter(date);
        return nextDate.getTime() / UNIT;
    }
}
