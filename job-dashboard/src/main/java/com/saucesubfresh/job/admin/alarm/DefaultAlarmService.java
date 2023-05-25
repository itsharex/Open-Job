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
package com.saucesubfresh.job.admin.alarm;

import com.saucesubfresh.job.admin.domain.AlarmMessage;
import com.saucesubfresh.job.admin.entity.OpenJobAlarmRecordDO;
import com.saucesubfresh.job.admin.entity.OpenJobDO;
import com.saucesubfresh.job.admin.entity.OpenJobUserDO;
import com.saucesubfresh.job.admin.mapper.OpenJobAlarmRecordMapper;
import com.saucesubfresh.job.admin.mapper.OpenJobMapper;
import com.saucesubfresh.job.admin.mapper.OpenJobUserMapper;
import com.saucesubfresh.job.common.time.LocalDateTimeUtil;
import com.saucesubfresh.starter.alarm.exception.AlarmException;
import com.saucesubfresh.starter.alarm.provider.dingtalk.DingDingRobotAlarmExecutor;
import com.saucesubfresh.starter.alarm.provider.dingtalk.DingDingRobotAlarmRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;

/**
 * @author lijunping on 2022/4/25
 */
@Slf4j
@Component
public class DefaultAlarmService implements AlarmService{

    @Value("${com.saucesubfresh.alarm.template}")
    private String alarmTemplate;
    @Value("${com.saucesubfresh.alarm.night-begin-time}")
    private Integer beginTime;
    @Value("${com.saucesubfresh.alarm.night-end-time}")
    private Integer endTime;

    private final OpenJobMapper openJobMapper;
    private final OpenJobUserMapper userMapper;
    private final OpenJobAlarmRecordMapper alarmRecordMapper;
    private final DingDingRobotAlarmExecutor alarmExecutor;

    public DefaultAlarmService(OpenJobMapper openJobMapper,
                               OpenJobUserMapper userMapper,
                               OpenJobAlarmRecordMapper alarmRecordMapper,
                               DingDingRobotAlarmExecutor alarmExecutor) {
        this.openJobMapper = openJobMapper;
        this.userMapper = userMapper;
        this.alarmRecordMapper = alarmRecordMapper;
        this.alarmExecutor = alarmExecutor;
    }


    @Override
    public void sendAlarm(AlarmMessage alarmMessage) {
        final Long jobId = alarmMessage.getJobId();
        final LocalDateTime createTime = alarmMessage.getCreateTime();
        final String cause = alarmMessage.getCause();
        final Long appId = alarmMessage.getAppId();
        final String serverId = alarmMessage.getServerId();

        OpenJobDO openJobDO = openJobMapper.selectById(jobId);
        Long userId = openJobDO.getCreateUser();
        String title = openJobDO.getJobName();
        String time = LocalDateTimeUtil.format(createTime, LocalDateTimeUtil.DATETIME_FORMATTER);
        String content = String.format(alarmTemplate, title, time, cause);

        OpenJobAlarmRecordDO alarmRecordDO = new OpenJobAlarmRecordDO();
        alarmRecordDO.setAppId(appId);
        alarmRecordDO.setJobId(jobId);
        alarmRecordDO.setServerId(serverId);
        alarmRecordDO.setMessage(content);
        alarmRecordDO.setReceiver(userId);
        alarmRecordDO.setCreateTime(LocalDateTime.now());
        alarmRecordMapper.insert(alarmRecordDO);

        final String hHmm = LocalDateTimeUtil.format(createTime, "HHmm");
        final int currentTime = Integer.parseInt(hHmm);
        // 控制消息发送时间段，判断是否在合法发送时间
        if (currentTime > beginTime && currentTime < endTime){
            log.warn("不在合法发送时间内，取消本次发送");
            return;
        }

        OpenJobUserDO crawlerUserDO = userMapper.selectById(userId);
        DingDingRobotAlarmRequest request = buildAlarmRequest(content, crawlerUserDO.getPhone());
        send(request);
    }

    private DingDingRobotAlarmRequest buildAlarmRequest(String content, String phone){
        // 发送内容
        DingDingRobotAlarmRequest.TextVO text = new DingDingRobotAlarmRequest.TextVO();
        text.setContent(content);

        DingDingRobotAlarmRequest request = new DingDingRobotAlarmRequest();
        // 发送类型
        request.setMsgtype("text");
        request.setText(text);

        // 发送目标
        DingDingRobotAlarmRequest.AtVO at = new DingDingRobotAlarmRequest.AtVO();
        if (StringUtils.isNotBlank(phone)){
            at.setAtMobiles(Collections.singletonList(phone));
        }else {
            at.setIsAtAll(true);
        }
        request.setAt(at);
        return request;
    }

    private void send(DingDingRobotAlarmRequest request){
        try{
            alarmExecutor.doAlarm(request);
        }catch (AlarmException e){
            log.error("send alarm message failed {}", e.getMessage());
        }
    }
}
