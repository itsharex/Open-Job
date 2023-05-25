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
package com.saucesubfresh.job.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saucesubfresh.job.admin.convert.OpenJobAlarmRecordConvert;
import com.saucesubfresh.job.admin.entity.OpenJobAlarmRecordDO;
import com.saucesubfresh.job.admin.mapper.OpenJobAlarmRecordMapper;
import com.saucesubfresh.job.admin.service.OpenJobAlarmRecordService;
import com.saucesubfresh.job.api.dto.batch.BatchDTO;
import com.saucesubfresh.job.api.dto.create.OpenJobAlarmRecordCreateDTO;
import com.saucesubfresh.job.api.dto.req.OpenJobAlarmRecordReqDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobAlarmRecordRespDTO;
import com.saucesubfresh.job.common.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: 李俊平
 * @Date: 2023-05-25 07:42
 */
@Service
public class OpenJobAlarmRecordServiceImpl extends ServiceImpl<OpenJobAlarmRecordMapper, OpenJobAlarmRecordDO> implements OpenJobAlarmRecordService {
    
    @Autowired
    private OpenJobAlarmRecordMapper alarmRecordMapper;
    
    @Override
    public PageResult<OpenJobAlarmRecordRespDTO> selectPage(OpenJobAlarmRecordReqDTO alarmRecordReqDTO) {
        Page<OpenJobAlarmRecordDO> page = alarmRecordMapper.queryPage(alarmRecordReqDTO);
        IPage<OpenJobAlarmRecordRespDTO> convert = page.convert(OpenJobAlarmRecordConvert.INSTANCE::convert);
        return PageResult.build(convert);
    }

    @Override
    public OpenJobAlarmRecordRespDTO getById(Long id) {
        OpenJobAlarmRecordDO alarmRecordDO = alarmRecordMapper.selectById(id);
        return OpenJobAlarmRecordConvert.INSTANCE.convert(alarmRecordDO);
    }

    @Override
    public void save(OpenJobAlarmRecordCreateDTO alarmRecordCreateDTO) {
        alarmRecordMapper.insert(OpenJobAlarmRecordConvert.INSTANCE.convert(alarmRecordCreateDTO));
    }

    @Override
    public boolean deleteBatchIds(BatchDTO batchDTO) {
        alarmRecordMapper.deleteBatchIds(batchDTO.getIds());
        return true;
    }
}
