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
package com.saucesubfresh.job.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saucesubfresh.job.admin.entity.OpenJobAlarmRecordDO;
import com.saucesubfresh.job.api.dto.req.OpenJobAlarmRecordReqDTO;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * @author: 李俊平
 * @Date: 2023-05-25 07:40
 */
@Repository
public interface OpenJobAlarmRecordMapper extends BaseMapper<OpenJobAlarmRecordDO> {
    default Page<OpenJobAlarmRecordDO> queryPage(OpenJobAlarmRecordReqDTO alarmRecordReqDTO){
        return selectPage(alarmRecordReqDTO.page(), Wrappers.<OpenJobAlarmRecordDO>lambdaQuery()
                .eq(Objects.nonNull(alarmRecordReqDTO.getJobId()), OpenJobAlarmRecordDO::getJobId, alarmRecordReqDTO.getJobId())
                .eq(Objects.nonNull(alarmRecordReqDTO.getServerId()), OpenJobAlarmRecordDO::getServerId, alarmRecordReqDTO.getServerId())
                .between(Objects.nonNull(alarmRecordReqDTO.getBeginTime()), OpenJobAlarmRecordDO::getCreateTime, alarmRecordReqDTO.getBeginTime(), alarmRecordReqDTO.getEndTime())
                .orderByDesc(OpenJobAlarmRecordDO::getCreateTime)
        );
    }
}
