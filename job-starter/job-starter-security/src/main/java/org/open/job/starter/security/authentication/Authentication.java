package org.open.job.starter.security.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.open.job.starter.security.service.UserDetails;

import java.io.Serializable;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description: 认证实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authentication implements Serializable {
    private static final long serialVersionUID = 334230053031460426L;

    private UserDetails userDetails;

}
