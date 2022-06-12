package com.saucesubfresh.job.core.collector;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: 李俊平
 * @Date: 2022-06-12 17:18
 */
public abstract class AbstractJobHandlerCollector implements JobHandlerCollector{

    protected final ConcurrentMap<String, OpenJobHandler> handlerMap = new ConcurrentHashMap<>();

    @Override
    public OpenJobHandler getJobHandler(String handlerName){
        return handlerMap.get(handlerName);
    }
}
