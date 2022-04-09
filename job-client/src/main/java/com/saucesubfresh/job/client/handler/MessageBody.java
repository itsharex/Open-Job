package com.saucesubfresh.job.client.handler;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: 李俊平
 * @Date: 2022-03-05 11:50
 */
@Data
public class MessageBody implements Serializable {
    private static final long serialVersionUID = 8230301428590315404L;
    /**
     * The name of JobHandler
     */
    private String handlerName;
    /**
     * The params, json 字符串
     */
    private String params;
}
