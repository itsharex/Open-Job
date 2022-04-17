package com.saucesubfresh.job.admin.service.impl;

import com.saucesubfresh.job.admin.common.enums.OpenJobReportEnum;
import com.saucesubfresh.job.admin.dto.resp.OpenJobInstanceRespDTO;
import com.saucesubfresh.job.admin.dto.resp.OpenJobReportRespDTO;
import com.saucesubfresh.job.admin.dto.resp.OpenJobStatisticNumberRespDTO;
import com.saucesubfresh.job.admin.dto.resp.OpenJobStatisticReportRespDTO;
import com.saucesubfresh.job.admin.mapper.OpenJobLogMapper;
import com.saucesubfresh.job.admin.mapper.OpenJobMapper;
import com.saucesubfresh.job.admin.service.OpenJobInstanceService;
import com.saucesubfresh.job.admin.service.OpenJobReportService;
import com.saucesubfresh.job.admin.service.OpenJobStatisticService;
import com.saucesubfresh.rpc.core.enums.ClientStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lijunping on 2022/4/11
 */
@Service
public class OpenJobStatisticServiceImpl implements OpenJobStatisticService {

    private final OpenJobMapper openJobMapper;
    private final OpenJobLogMapper openJobLogMapper;
    private final OpenJobInstanceService instanceService;
    private final OpenJobReportService openJobReportService;

    public OpenJobStatisticServiceImpl(OpenJobMapper openJobMapper,
                                       OpenJobLogMapper openJobLogMapper,
                                       OpenJobInstanceService instanceService,
                                       OpenJobReportService openJobReportService) {
        this.openJobMapper = openJobMapper;
        this.openJobLogMapper = openJobLogMapper;
        this.instanceService = instanceService;
        this.openJobReportService = openJobReportService;
    }

    @Override
    public OpenJobStatisticNumberRespDTO getStatisticNumber() {

        int taskTotalCount = openJobMapper.getTotalCount();
        int taskRunningCount = openJobMapper.getRunningCount();
        int scheduleTotalCount = openJobLogMapper.getScheduleTotalCount();
        int scheduleSucceedCount = openJobLogMapper.getScheduleSucceedCount();
        final List<OpenJobInstanceRespDTO> instanceList = instanceService.getInstanceList();
        int instanceTotalCount = instanceList.size();
        long instanceOnlineCount = instanceList.stream().filter(e->e.getStatus() == ClientStatus.ON_LINE).count();

        OpenJobStatisticNumberRespDTO numberRespDTO = new OpenJobStatisticNumberRespDTO();
        numberRespDTO.setTaskTotalNum(taskTotalCount);
        numberRespDTO.setTaskRunningNum(taskRunningCount);
        numberRespDTO.setScheduleTotalNum(scheduleTotalCount);
        numberRespDTO.setScheduleSucceedNum(scheduleSucceedCount);
        numberRespDTO.setExecutorTotalNum(instanceTotalCount);
        numberRespDTO.setExecutorOnlineNum((int) instanceOnlineCount);
        return numberRespDTO;
    }

    @Override
    public List<OpenJobStatisticReportRespDTO> getStatisticReport() {
        List<OpenJobReportRespDTO> reportRespDTOS = openJobReportService.getOpenJobReportList();
        if (CollectionUtils.isEmpty(reportRespDTOS)){
            return Collections.emptyList();
        }

        List<OpenJobStatisticReportRespDTO> openJobStatisticReportRespDTOS = reportRespDTOS.stream().map(e -> {
            OpenJobStatisticReportRespDTO openJobStatisticReportRespDTO = new OpenJobStatisticReportRespDTO();
            openJobStatisticReportRespDTO.setDate(e.getCreateTime());
            openJobStatisticReportRespDTO.setName(OpenJobReportEnum.TASK_EXEC_TOTAL_COUNT.getName());
            openJobStatisticReportRespDTO.setValue(e.getTaskExecTotalCount());
            return openJobStatisticReportRespDTO;
        }).collect(Collectors.toList());

        final List<OpenJobStatisticReportRespDTO> taskExecSuccessList = reportRespDTOS.stream().map(e -> {
            OpenJobStatisticReportRespDTO openJobStatisticReportRespDTO = new OpenJobStatisticReportRespDTO();
            openJobStatisticReportRespDTO.setDate(e.getCreateTime());
            openJobStatisticReportRespDTO.setName(OpenJobReportEnum.TASK_EXEC_SUCCESS_COUNT.getName());
            openJobStatisticReportRespDTO.setValue(e.getTaskExecSuccessCount());
            return openJobStatisticReportRespDTO;
        }).collect(Collectors.toList());
        openJobStatisticReportRespDTOS.addAll(taskExecSuccessList);

        return openJobStatisticReportRespDTOS;
    }
}
