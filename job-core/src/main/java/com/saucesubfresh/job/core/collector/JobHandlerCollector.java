package com.saucesubfresh.job.core.collector;

/**
 * @author: 李俊平
 * @Date: 2022-06-12 17:08
 */
public interface JobHandlerCollector {

    /**
     * 收集 JobHandler
     */
    void collectJobHandler();

    /**
     * 根据 handlerName 获取 OpenJobHandler
     * @param handlerName 绑定的 handlerName
     * @return OpenJobHandler
     */
    OpenJobHandler getJobHandler(String handlerName);
}