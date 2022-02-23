package org.open.job.starter.captcha.properties;

import lombok.Data;

/**
 * @author : lijunping
 * @weixin : ilwq18242076871
 *
 */
@Data
public class SmsCodeProperties {

	/**验证码的位数*/
	private int length = 6;

	/**验证码过期时间 /s*/
	private int expireTime = 3600;
}
