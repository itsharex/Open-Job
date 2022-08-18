package com.saucesubfresh.job.api.dto.update;

import com.saucesubfresh.rpc.core.enums.ClientStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: 李俊平
 * @Date: 2022-02-26 15:03
 */
@Data
public class OpenJobInstanceUpdateDTO implements Serializable {
    private static final long serialVersionUID = 4199379444084998224L;

    /**
     * 客户端地址：端口
     */
    private String clientId;
    /**
     * 上线时间
     */
    private LocalDateTime onlineTime;
    /**
     * 在线状态
     */
    private ClientStatus status;
    /**
     * 权重
     */
    private int weight = 1;
}
