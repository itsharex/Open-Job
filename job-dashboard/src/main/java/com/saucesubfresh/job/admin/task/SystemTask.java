package com.saucesubfresh.job.admin.task;

import com.saucesubfresh.job.admin.mapper.OpenJobLogMapper;
import com.saucesubfresh.job.admin.service.OpenJobReportService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author lijunping on 2022/4/22
 */
@Component
public class SystemTask {

    @Value("${clear-interval}")
    private Integer clearInterval;

    private final OpenJobLogMapper openJobLogMapper;
    private final OpenJobReportService openJobReportService;

    public SystemTask(OpenJobLogMapper openJobLogMapper,
                      OpenJobReportService openJobReportService) {
        this.openJobLogMapper = openJobLogMapper;
        this.openJobReportService = openJobReportService;
    }


    /**
     * 定时插入报表任务
     */
    @Scheduled(cron = "0 59 23 * * ?")
    public void addReportTask(){
        openJobReportService.insertReport();
    }

    /**
     * 定时清除任务日志任务
     */
    @Scheduled(cron = "0 0 12 ? * 6")
    public void clearTaskLogTask(){
        openJobLogMapper.clearLog(clearInterval);
    }
}
