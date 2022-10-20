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
package com.saucesubfresh.job.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.saucesubfresh.job.admin.entity.OpenJobReportDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lijunping on 2022/4/11
 */
@Repository
public interface OpenJobReportMapper extends BaseMapper<OpenJobReportDO> {

    default List<OpenJobReportDO> queryList(){
        return selectList(Wrappers.lambdaQuery());
    }
}
