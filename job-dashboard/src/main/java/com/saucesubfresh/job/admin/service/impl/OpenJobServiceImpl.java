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
import com.saucesubfresh.job.admin.convert.OpenJobConvert;
import com.saucesubfresh.job.api.dto.batch.BatchDTO;
import com.saucesubfresh.job.api.dto.create.OpenJobCreateDTO;
import com.saucesubfresh.job.api.dto.req.OpenJobReqDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobRespDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobTriggerTimeDTO;
import com.saucesubfresh.job.api.dto.update.OpenJobUpdateDTO;
import com.saucesubfresh.job.admin.entity.OpenJobDO;
import com.saucesubfresh.job.admin.mapper.OpenJobMapper;
import com.saucesubfresh.job.admin.service.OpenJobService;
import com.saucesubfresh.job.common.enums.CommonStatusEnum;
import com.saucesubfresh.job.common.exception.ServiceException;
import com.saucesubfresh.job.common.time.LocalDateTimeUtil;
import com.saucesubfresh.job.common.vo.PageResult;
import com.saucesubfresh.starter.schedule.cron.CronExpression;
import com.saucesubfresh.starter.schedule.domain.ScheduleTask;
import com.saucesubfresh.starter.schedule.executor.ScheduleTaskExecutor;
import com.saucesubfresh.starter.schedule.loader.ScheduleTaskLoader;
import com.saucesubfresh.starter.schedule.manager.ScheduleTaskPoolManager;
import com.saucesubfresh.starter.schedule.manager.ScheduleTaskQueueManager;
import com.saucesubfresh.starter.security.context.UserSecurityContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lijunping on 2022/2/17
 */
@Service
public class OpenJobServiceImpl extends ServiceImpl<OpenJobMapper, OpenJobDO> implements OpenJobService, ScheduleTaskLoader {

    private final OpenJobMapper openJobMapper;
    private final ScheduleTaskExecutor scheduleTaskExecutor;
    private final ScheduleTaskPoolManager scheduleTaskPoolManager;
    private final ScheduleTaskQueueManager scheduleTaskQueueManager;

    public OpenJobServiceImpl(OpenJobMapper openJobMapper,
                              ScheduleTaskExecutor scheduleTaskExecutor,
                              ScheduleTaskPoolManager scheduleTaskPoolManager,
                              ScheduleTaskQueueManager scheduleTaskQueueManager) {
        this.openJobMapper = openJobMapper;
        this.scheduleTaskExecutor = scheduleTaskExecutor;
        this.scheduleTaskPoolManager = scheduleTaskPoolManager;
        this.scheduleTaskQueueManager = scheduleTaskQueueManager;
    }

    @Override
    public PageResult<OpenJobRespDTO> selectPage(OpenJobReqDTO openJobReqDTO) {
        Page<OpenJobDO> page = openJobMapper.queryPage(openJobReqDTO);
        IPage<OpenJobRespDTO> convert = page.convert(OpenJobConvert.INSTANCE::convert);
        return PageResult.build(convert);
    }

    @Override
    public OpenJobRespDTO getById(Long id) {
        OpenJobDO OpenJobDO = openJobMapper.selectById(id);
        return OpenJobConvert.INSTANCE.convert(OpenJobDO);
    }

    @Override
    public boolean save(OpenJobCreateDTO openJobCreateDTO) {
        String cronExpression = openJobCreateDTO.getCronExpression();
        if (!CronExpression.isValidExpression(cronExpression)){
            throw new ServiceException("Invalid cronExpression");
        }
        openJobCreateDTO.setCreateTime(LocalDateTime.now());
        openJobCreateDTO.setCreateUser(UserSecurityContextHolder.getUserId());
        int insert = openJobMapper.insert(OpenJobConvert.INSTANCE.convert(openJobCreateDTO));
        return insert != 0;
    }

    @Override
    public boolean updateById(OpenJobUpdateDTO openJobUpdateDTO) {
        OpenJobDO openJobDO = openJobMapper.selectById(openJobUpdateDTO.getId());
        OpenJobDO convert = OpenJobConvert.INSTANCE.convert(openJobUpdateDTO);
        int update = openJobMapper.updateById(convert);
        updateScheduleTask(openJobDO, convert);
        return update != 0;
    }

    @Override
    public boolean start(Long id) {
        OpenJobDO openJobDO = openJobMapper.selectById(id);
        openJobDO.setStatus(CommonStatusEnum.YES.getValue());
        openJobMapper.updateById(openJobDO);
        ScheduleTask scheduleTask = createScheduleTask(openJobDO);
        scheduleTaskPoolManager.add(scheduleTask);
        scheduleTaskQueueManager.put(scheduleTask.getTaskId(), scheduleTask.getCronExpression());
        return Boolean.TRUE;
    }

    @Override
    public boolean stop(Long id) {
        OpenJobDO openJobDO = openJobMapper.selectById(id);
        openJobDO.setStatus(CommonStatusEnum.NO.getValue());
        openJobMapper.updateById(openJobDO);
        scheduleTaskPoolManager.remove(id);
        return Boolean.TRUE;
    }

    @Override
    public boolean deleteBatchIds(BatchDTO batchDTO) {
        List<Long> ids = batchDTO.getIds();
        openJobMapper.deleteBatchIds(ids);
        ids.forEach(scheduleTaskPoolManager::remove);
        return Boolean.TRUE;
    }

    @Override
    public boolean run(Long id) {
        scheduleTaskExecutor.execute(Collections.singletonList(id));
        return true;
    }

    @Override
    public OpenJobTriggerTimeDTO nextTriggerTime(String cronExpress, Integer count) {
        OpenJobTriggerTimeDTO dto = new OpenJobTriggerTimeDTO();
        List<String> result = new ArrayList<>();
        String errMsg = null;
        try {
            result = genNextTimes(count, cronExpress);
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
        dto.setTimes(result);
        dto.setErrMsg(errMsg);
        return dto;
    }

    @Override
    public List<ScheduleTask> loadScheduleTask() {
        List<OpenJobDO> scheduleTasks = openJobMapper.queryStartJob();
        if (CollectionUtils.isEmpty(scheduleTasks)){
            return Collections.emptyList();
        }
        return scheduleTasks.stream().map(this::createScheduleTask).collect(Collectors.toList());
    }

    @Override
    public boolean validateCron(String cronExpress) {
        try {
            CronExpression.validateExpression(cronExpress);
            return Boolean.TRUE;
        } catch (ParseException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private void updateScheduleTask(OpenJobDO oldJob, OpenJobDO newJob){
        boolean status = oldJob.getStatus().equals(CommonStatusEnum.YES.getValue());
        boolean equals = StringUtils.equals(oldJob.getCronExpression(), newJob.getCronExpression());
        if (!equals && status){
            ScheduleTask scheduleTask = createScheduleTask(newJob);
            scheduleTaskPoolManager.add(scheduleTask);
            scheduleTaskQueueManager.put(scheduleTask.getTaskId(), scheduleTask.getCronExpression());
        }
    }

    private ScheduleTask createScheduleTask(OpenJobDO openJobDO){
        ScheduleTask scheduleTask = new ScheduleTask();
        scheduleTask.setTaskId(openJobDO.getId());
        scheduleTask.setCronExpression(openJobDO.getCronExpression());
        return scheduleTask;
    }

    private List<String> genNextTimes(int size, String cronExpress) throws ParseException {
        List<String> result = new ArrayList<>();
        Date lastTime = new Date();
        for (int i = 0; i < size; i++) {
            lastTime = new CronExpression(cronExpress).getNextValidTimeAfter(lastTime);
            LocalDateTime localDateTime = LocalDateTimeUtil.convertDateToLDT(lastTime);
            String time = LocalDateTimeUtil.format(localDateTime, LocalDateTimeUtil.DATETIME_FORMATTER);
            result.add(time);
        }
        return result;
    }
}
