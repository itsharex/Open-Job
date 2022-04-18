package com.saucesubfresh.job.admin.service;

import com.saucesubfresh.job.admin.dto.resp.OpenJobReportRespDTO;

import java.util.List;

/**
 * @author lijunping on 2022/4/11
 */
public interface OpenJobReportService {

    void insertReport();

    List<OpenJobReportRespDTO> getOpenJobReportList();
}
