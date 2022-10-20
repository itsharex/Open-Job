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
import com.saucesubfresh.job.admin.entity.OpenJobDO;
import com.saucesubfresh.job.admin.entity.OpenJobUserDO;
import com.saucesubfresh.job.admin.mapper.OpenJobMapper;
import com.saucesubfresh.job.admin.mapper.OpenJobUserMapper;
import com.saucesubfresh.job.common.time.LocalDateTimeUtil;
import com.saucesubfresh.starter.alarm.exception.AlarmException;
import com.saucesubfresh.starter.alarm.provider.dingtalk.DingtalkAlarmExecutor;
import com.saucesubfresh.starter.alarm.provider.dingtalk.DingtalkMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;

/**
 * @author lijunping on 2022/4/25
 */
@Slf4j
@Component
public class DefaultAlarmService implements AlarmService{

    @Value("${alarm-template}")
    private String alarmTemplate;

    private final DingtalkAlarmExecutor alarmExecutor;
    private final OpenJobMapper openJobMapper;
    private final OpenJobUserMapper userMapper;

    public DefaultAlarmService(DingtalkAlarmExecutor alarmExecutor,
                               OpenJobMapper openJobMapper,
                               OpenJobUserMapper userMapper) {
        this.alarmExecutor = alarmExecutor;
        this.openJobMapper = openJobMapper;
        this.userMapper = userMapper;
    }


    @Override
    public void sendAlarm(AlarmMessage alarmMessage) {
        send(buildRequest(alarmMessage));
    }

    private DingtalkMessageRequest buildRequest(AlarmMessage alarmMessage){
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
        try{
            alarmExecutor.doAlarm(request);
            log.info("send alarm yet");
        }catch (AlarmException e){
            log.error("send alarm message failed {}", e.getMessage());
        }
    }
}
