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
package com.saucesubfresh.job.admin.event;

import lombok.Getter;
import com.saucesubfresh.job.api.dto.create.OpenJobLogCreateDTO;
import org.springframework.context.ApplicationEvent;

/**
 * @author lijunping on 2022/2/28
 */
@Getter
public class JobLogEvent extends ApplicationEvent {
    private final OpenJobLogCreateDTO jobLogCreateDTO;


    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public JobLogEvent(Object source, OpenJobLogCreateDTO jobLogCreateDTO) {
        super(source);
        this.jobLogCreateDTO = jobLogCreateDTO;
    }
}
