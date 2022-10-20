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
