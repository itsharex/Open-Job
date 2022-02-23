package org.open.job.starter.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.open.job.core.exception.RpcException;

import java.util.Arrays;

/**
 * @author: 李俊平
 * @Date: 2022-01-31 10:48
 */
@Getter
@AllArgsConstructor
public enum ClusterInvokeModelEnum {


    BROADCAST("broadcast"),

    FAIL_BACK("failBack"),

    FAILOVER("failover"),

    ;
    private final String name;

    public static ClusterInvokeModelEnum of(String name){
        return Arrays.stream(values())
                .filter(e-> StringUtils.equalsIgnoreCase(name, e.name()))
                .findFirst()
                .orElseThrow(()-> new RpcException("No ClusterInvokeModelEnum find" + name));
    }
}
