package com.saucesubfresh.job.core.collector;

import java.lang.reflect.Method;

/**
 * @author: 李俊平
 * @Date: 2022-06-13 06:39
 */
public class MethodJobHandler implements OpenJobHandler{

    private final Object target;
    private final Method method;

    public MethodJobHandler(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    @Override
    public void handler(String params) throws Exception{
        Class<?>[] paramTypes = method.getParameterTypes();
        if (paramTypes.length > 0) {
            method.invoke(target, params);
        } else {
            method.invoke(target);
        }
    }
}
