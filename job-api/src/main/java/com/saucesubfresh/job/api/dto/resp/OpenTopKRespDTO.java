package com.saucesubfresh.job.api.dto.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lijunping on 2022/4/11
 */
@Data
public class OpenTopKRespDTO implements Serializable {

    /**
     * key
     */
    private String key;

    /**
     * 当日执行总次数
     */
    private Integer totalCount;

    /**
     * 当日执行成功次数
     */
    private Integer successCount;
}
