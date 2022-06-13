package com.saucesubfresh.job.sample.controller;

import com.saucesubfresh.job.core.collector.JobHandlerCollector;
import com.saucesubfresh.job.core.collector.OpenJobHandler;
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
    private JobHandlerCollector jobHandlerCollector;

    @GetMapping("/test")
    public void test() throws Exception {
        String handlerName = "job-one";
        OpenJobHandler openJobHandler = jobHandlerCollector.getJobHandler(handlerName);
        if (Objects.nonNull(openJobHandler)){
            openJobHandler.handler("job-one");
        }
    }

    @GetMapping("/test1")
    public void test1() throws Exception {
        String handlerName = "job-method-one1";
        OpenJobHandler openJobHandler = jobHandlerCollector.getJobHandler(handlerName);
        if (Objects.nonNull(openJobHandler)){
            openJobHandler.handler("job-method-one1");
        }
    }
}
