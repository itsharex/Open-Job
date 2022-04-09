package com.saucesubfresh.job.admin.event;

import lombok.Getter;
import com.saucesubfresh.job.admin.dto.create.OpenJobLogCreateDTO;
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
