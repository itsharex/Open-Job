package com.saucesubfresh.job.admin.service.impl;

import com.saucesubfresh.job.admin.common.enums.SystemScheduleEnum;
import com.saucesubfresh.job.admin.convert.OpenJobReportConvert;
import com.saucesubfresh.job.admin.dto.resp.OpenJobReportRespDTO;
import com.saucesubfresh.job.admin.entity.OpenJobReportDO;
import com.saucesubfresh.job.admin.mapper.OpenJobLogMapper;
import com.saucesubfresh.job.admin.mapper.OpenJobReportMapper;
import com.saucesubfresh.job.admin.service.OpenJobReportService;
import com.saucesubfresh.starter.schedule.core.ScheduleTaskManage;
import com.saucesubfresh.starter.schedule.domain.ScheduleTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lijunping on 2022/4/11
 */
@Service
public class OpenJobReportServiceImpl implements OpenJobReportService {

    @Value("${report-cron-express}")
    private String reportCronExpress;
    
    private final OpenJobLogMapper openJobLogMapper;
    private final OpenJobReportMapper openJobReportMapper;
    private final ScheduleTaskManage scheduleTaskManage;

    public OpenJobReportServiceImpl(OpenJobLogMapper openJobLogMapper,
                                    OpenJobReportMapper openJobReportMapper,
                                    ScheduleTaskManage scheduleTaskManage) {
        this.openJobLogMapper = openJobLogMapper;
        this.openJobReportMapper = openJobReportMapper;
        this.scheduleTaskManage = scheduleTaskManage;
    }

    @PostConstruct
    public void addReportTask(){
        ScheduleTask scheduleTask = new ScheduleTask();
        scheduleTask.setTaskId(SystemScheduleEnum.REPORT.getValue());
        scheduleTask.setCronExpression(reportCronExpress);
        scheduleTaskManage.addScheduleTask(scheduleTask);
    }

    @Override
    public void insertReport() {
        int scheduleTotalCount = openJobLogMapper.getScheduleTotalCount();
        int scheduleSucceedCount = openJobLogMapper.getScheduleSucceedCount();
        OpenJobReportDO openJobReportDO = new OpenJobReportDO();
        openJobReportDO.setTaskExecTotalCount(scheduleTotalCount);
        openJobReportDO.setTaskExecSuccessCount(scheduleSucceedCount);
        openJobReportDO.setCreateTime(LocalDateTime.now());
        openJobReportMapper.insert(openJobReportDO);
    }

    @Override
    public List<OpenJobReportRespDTO> getOpenJobReportList() {
        List<OpenJobReportDO> openJobReportDOS = openJobReportMapper.queryList();
        return OpenJobReportConvert.INSTANCE.convertList(openJobReportDOS);
    }

}
