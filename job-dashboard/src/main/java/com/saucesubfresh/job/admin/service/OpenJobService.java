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
package com.saucesubfresh.job.admin.service;

import com.saucesubfresh.job.api.dto.req.OpenJobReqDTO;
import com.saucesubfresh.job.api.dto.create.OpenJobCreateDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobRespDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobTriggerTimeDTO;
import com.saucesubfresh.job.api.dto.update.OpenJobUpdateDTO;
import com.saucesubfresh.job.common.vo.PageResult;

import java.util.List;

/**
 * 任务表
 *
 * @author lijunping
 * @email lijunping365@gmail.com
 * @date 2021-09-06 10:10:03
 */
public interface OpenJobService {

    PageResult<OpenJobRespDTO> selectPage(OpenJobReqDTO OpenJobReqDTO);

    OpenJobRespDTO getById(Long id);

    boolean save(OpenJobCreateDTO OpenJobCreateDTO);

    boolean updateById(OpenJobUpdateDTO OpenJobUpdateDTO);

    boolean start(Long id);

    boolean stop(Long id);

    boolean deleteById(Long id);

    boolean run(Long id);

    OpenJobTriggerTimeDTO nextTriggerTime(String cronExpress);

    boolean validateCron(String cronExpress);
}
