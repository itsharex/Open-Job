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
package com.saucesubfresh.job.admin.convert;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.saucesubfresh.job.api.dto.create.OpenJobLogCreateDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobLogRespDTO;
import com.saucesubfresh.job.admin.entity.OpenJobLogDO;

/**
 * 任务运行日志
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Mapper
public interface OpenJobLogConvert {

    OpenJobLogConvert INSTANCE = Mappers.getMapper(OpenJobLogConvert.class);

    OpenJobLogRespDTO convert(OpenJobLogDO OpenJobLogDO);

    OpenJobLogDO convert(OpenJobLogCreateDTO OpenJobLogCreateDTO);

    OpenJobLogDO convert(OpenJobLogUpdateDTO OpenJobLogUpdateDTO);

}


