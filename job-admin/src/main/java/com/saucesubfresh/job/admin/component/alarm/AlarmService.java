package com.saucesubfresh.job.admin.component.alarm;

import com.saucesubfresh.job.admin.entity.OpenJobUserDO;
import com.saucesubfresh.job.common.time.LocalDateTimeUtil;
import com.google.common.base.Throwables;
import com.saucesubfresh.job.admin.dto.create.OpenJobLogCreateDTO;
import com.saucesubfresh.job.admin.entity.OpenJobDO;
import com.saucesubfresh.job.admin.mapper.OpenJobMapper;
import com.saucesubfresh.job.admin.mapper.OpenJobUserMapper;
import com.saucesubfresh.starter.alarm.provider.dingtalk.DingtalkAlarmExecutor;
import com.saucesubfresh.starter.alarm.provider.dingtalk.DingtalkMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;

/**
 * @author lijunping on 2022/4/25
 */
@Slf4j
@Component
public class AlarmService {

    private static final String PADDING_ALARM_KEY = "alarm_message";

    @Value("${night-pending-begin-time}")
    private Integer beginTime;

    @Value("${night-pending-end-time}")
    private Integer endTime;

    @Value("${alarm-template}")
    private String alarmTemplate;

    private final DingtalkAlarmExecutor alarmExecutor;
    private final OpenJobMapper openJobMapper;
    private final OpenJobUserMapper userMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    public AlarmService(DingtalkAlarmExecutor alarmExecutor,
                        OpenJobMapper openJobMapper,
                        OpenJobUserMapper userMapper,
                        RedisTemplate<String, Object> redisTemplate) {
        this.alarmExecutor = alarmExecutor;
        this.openJobMapper = openJobMapper;
        this.userMapper = userMapper;
        this.redisTemplate = redisTemplate;
    }


    public void sendAlarm(OpenJobLogCreateDTO alarmMessage){
        final LocalDateTime createTime = alarmMessage.getCreateTime();
        final String hHmm = LocalDateTimeUtil.format(createTime, "HHmm");
        final int currentTime = Integer.parseInt(hHmm);

        if (currentTime > beginTime && currentTime < endTime){
            lPush(PADDING_ALARM_KEY, alarmMessage, 3600L * 24);
            return;
        }

        send(buildRequest(alarmMessage));
    }

    public void sendAlarm(){
        while (lLen(PADDING_ALARM_KEY) > 0) {
            final Object o = redisTemplate.opsForList().leftPop(PADDING_ALARM_KEY);
            if (Objects.isNull(o)){
                continue;
            }
            OpenJobLogCreateDTO alarmMessage = (OpenJobLogCreateDTO) o;
            final DingtalkMessageRequest request = buildRequest(alarmMessage);
            send(request);
        }
    }

    private DingtalkMessageRequest buildRequest(OpenJobLogCreateDTO alarmMessage){
        DingtalkMessageRequest request = new DingtalkMessageRequest();
        // 发送类型
        request.setMsgtype("text");

        // 发送内容
        final Long jobId = alarmMessage.getJobId();
        final LocalDateTime createTime = alarmMessage.getCreateTime();
        final String cause = alarmMessage.getCause();

        OpenJobDO openJobDO = openJobMapper.selectById(jobId);
        if (Objects.isNull(openJobDO)){
            return null;
        }
        Long userId = openJobDO.getCreateUser();
        String title = openJobDO.getJobName();
        String time = LocalDateTimeUtil.format(createTime, LocalDateTimeUtil.DATETIME_FORMATTER);
        String content = String.format(alarmTemplate, title, time, cause);

        DingtalkMessageRequest.TextVO text = new DingtalkMessageRequest.TextVO();
        text.setContent(content);
        request.setText(text);

        // 发送目标
        final OpenJobUserDO crawlerUserDO = userMapper.selectById(userId);
        if (Objects.isNull(crawlerUserDO) || StringUtils.isBlank(crawlerUserDO.getPhone())){
            return null;
        }

        DingtalkMessageRequest.AtVO at = new DingtalkMessageRequest.AtVO();
        at.setAtMobiles(Collections.singletonList(crawlerUserDO.getPhone()));
        request.setAt(at);

        return request;
    }

    private void send(DingtalkMessageRequest request){
        if (Objects.isNull(request)){
            return;
        }
        alarmExecutor.doAlarm(request, callbackMessage -> {
            log.info("send alarm yet");
        });
    }

    /**
     * lLen 方法
     */
    public Long lLen(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error("RedisUtils#pipelineSetEx fail! e:{}", Throwables.getStackTraceAsString(e));
        }
        return 0L;
    }

    /**
     * lpush 方法 并指定 过期时间
     */
    public void lPush(String key, Object value, Long seconds) {
        try {
            redisTemplate.executePipelined((RedisCallback<String>) connection -> {
                connection.lPush(key.getBytes(), value.toString().getBytes());
                connection.expire(key.getBytes(), seconds);
                return null;
            });
        } catch (Exception e) {
            log.error("RedisUtils#pipelineSetEx fail! e:{}", Throwables.getStackTraceAsString(e));
        }
    }
}
