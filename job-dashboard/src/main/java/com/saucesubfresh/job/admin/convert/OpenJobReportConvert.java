package com.saucesubfresh.job.admin.convert;

import com.saucesubfresh.job.api.dto.resp.OpenJobReportRespDTO;
import com.saucesubfresh.job.admin.entity.OpenJobReportDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author lijunping on 2022/4/12
 */
@Mapper
public interface OpenJobReportConvert {

    OpenJobReportConvert INSTANCE = Mappers.getMapper(OpenJobReportConvert.class);

    List<OpenJobReportRespDTO> convertList(List<OpenJobReportDO> crawlerReportDOS);
}
