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

import com.saucesubfresh.job.api.dto.req.OpenJobInstanceReqDTO;
import com.saucesubfresh.job.api.dto.resp.OpenJobInstanceRespDTO;
import com.saucesubfresh.job.common.vo.PageResult;

import java.util.List;

/**
 * @author: 李俊平
 * @Date: 2022-02-26 15:05
 */
public interface OpenJobInstanceService {
    
    PageResult<OpenJobInstanceRespDTO> selectPage(OpenJobInstanceReqDTO instanceReqDTO);

    Boolean offlineClient(String clientId);

    Boolean onlineClient(String clientId);

    List<OpenJobInstanceRespDTO> getInstanceList(Long appId);
}
