package org.open.job.admin.dto.resp;

import lombok.Data;
import org.open.job.core.ClientStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: 李俊平
 * @Date: 2022-02-26 14:56
 */
@Data
public class OpenJobInstanceRespDTO implements Serializable {

    private static final long serialVersionUID = -822276416137575995L;
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
