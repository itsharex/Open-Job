package com.saucesubfresh.job.api.dto.resp;

import lombok.Data;

import java.util.List;

/**
 * @author lijunping on 2022/8/23
 */
@Data
public class OpenJobTriggerTimeDTO {

    private List<String> times;

    private String errMsg;
}
