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
