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
package com.saucesubfresh.job.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: 李俊平
 * @Date: 2023-03-21 14:00
 */
@Getter
@AllArgsConstructor
public enum RouteStrategyEnum {

    LB(0),

    SHARDING(1),

    ;

    private final Integer value;

    public static RouteStrategyEnum of(Integer value){
        for (RouteStrategyEnum routeStrategyEnum : RouteStrategyEnum.values()) {
            if (routeStrategyEnum.value.equals(value)){
                return routeStrategyEnum;
            }
        }
        return RouteStrategyEnum.LB;
    }
}
