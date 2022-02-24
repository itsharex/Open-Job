package org.open.job.starter.security.utils;

import org.open.job.starter.security.exception.AuthenticationException;

import javax.servlet.http.HttpServletRequest;

import static org.open.job.common.constants.CommonConstant.AUTHENTICATION_HEADER;
import static org.open.job.common.constants.CommonConstant.AUTHENTICATION_TYPE;


/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description: HttpServletRequest 工具类
 */
public class RequestUtils {

    /**
     * @param request
     * @return
     *
     * @throws AuthenticationException
     */
    public static String extractTokenFromHeader(HttpServletRequest request) throws AuthenticationException {

        String header = request.getHeader(AUTHENTICATION_HEADER);

        if (header == null || !header.startsWith(AUTHENTICATION_TYPE)) {
            throw new AuthenticationException("错误的请求头");
        }

        return header.substring(7);
    }
}
