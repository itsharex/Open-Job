package com.saucesubfresh.job.admin.convert;


import com.saucesubfresh.job.admin.domain.AlarmMessage;
import com.saucesubfresh.job.api.dto.create.OpenJobLogCreateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 任务表
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Mapper
public interface OpenJobAlarmMessageConvert {

    OpenJobAlarmMessageConvert INSTANCE = Mappers.getMapper(OpenJobAlarmMessageConvert.class);

    AlarmMessage convert(OpenJobLogCreateDTO jobLogCreateDTO);
}
