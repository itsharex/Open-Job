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
package com.saucesubfresh.job.sample.controller;

import com.saucesubfresh.starter.job.register.core.JobHandlerRegister;
import com.saucesubfresh.starter.job.register.core.OpenJobHandler;
import com.saucesubfresh.starter.job.register.param.JobParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author lijunping on 2022/6/13
 */
@RestController
public class OpenJobHandlerTest {

    @Autowired
    private JobHandlerRegister jobHandlerRegister;

    @GetMapping("/test")
    public void test() throws Exception {
        String handlerName = "job-one";
        OpenJobHandler openJobHandler = jobHandlerRegister.getJobHandler(handlerName);
        if (Objects.nonNull(openJobHandler)){
            JobParam param = JobParam.builder()
                    .params("job-one")
                    .build();
            openJobHandler.handler(param);
        }
    }

    @GetMapping("/test1")
    public void test1() throws Exception {
        String handlerName = "job-method-one1";
        OpenJobHandler openJobHandler = jobHandlerRegister.getJobHandler(handlerName);
        if (Objects.nonNull(openJobHandler)){
            JobParam param = JobParam.builder()
                    .params("job-one")
                    .build();
            openJobHandler.handler(param);
        }
    }
}
