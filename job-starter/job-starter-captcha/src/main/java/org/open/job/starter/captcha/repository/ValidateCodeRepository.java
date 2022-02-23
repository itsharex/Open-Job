package org.open.job.starter.captcha.repository;

import org.open.job.starter.captcha.core.domain.ValidateCode;
import org.open.job.starter.captcha.enums.ValidateCodeType;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 * Description：保存、获取、移除验证码的模版接口
 */
public interface ValidateCodeRepository {
    /**
     * 保存验证码
     * @param deviceId
     * @param code
     * @param validateCodeType
     */
    void save(String deviceId, ValidateCode code, ValidateCodeType validateCodeType);
    /**
     * 获取验证码
     * @param deviceId
     * @param validateCodeType
     * @return
     */
    ValidateCode get(String deviceId, ValidateCodeType validateCodeType);
    /**
     * 移除验证码
     * @param deviceId
     * @param codeType
     */
    void remove(String deviceId, ValidateCodeType codeType);
}
