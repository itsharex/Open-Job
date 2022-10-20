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

import com.saucesubfresh.job.api.dto.create.OpenJobAppCreateDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobAppRespDTO;
import com.saucesubfresh.job.api.dto.update.OpenJobAppUpdateDTO;
import com.saucesubfresh.job.admin.entity.OpenJobAppDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 任务运行日志
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
@Mapper
public interface OpenJobAppConvert {

    OpenJobAppConvert INSTANCE = Mappers.getMapper(OpenJobAppConvert.class);

    OpenJobAppRespDTO convert(OpenJobAppDO OpenJobAppDO);

    OpenJobAppDO convert(OpenJobAppCreateDTO OpenJobAppCreateDTO);

    OpenJobAppDO convert(OpenJobAppUpdateDTO OpenJobAppUpdateDTO);

    List<OpenJobAppRespDTO> convertList(List<OpenJobAppDO> openJobAppDOS);
}


