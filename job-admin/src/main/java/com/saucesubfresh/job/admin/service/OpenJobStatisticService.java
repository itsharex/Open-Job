package com.saucesubfresh.job.admin.service;


import com.saucesubfresh.job.admin.dto.resp.OpenJobStatisticNumberRespDTO;
import com.saucesubfresh.job.admin.dto.resp.OpenJobStatisticReportRespDTO;

import java.util.List;

/**
 * @author lijunping on 2022/4/11
 */
public interface OpenJobStatisticService {

    OpenJobStatisticNumberRespDTO getStatisticNumber();

    List<OpenJobStatisticReportRespDTO> getStatisticReport();
}