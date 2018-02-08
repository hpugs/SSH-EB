package com.hpugs.service;

import java.util.Map;

/**
 * @Description 公共服务
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2018年2月8日 上午10:45:06
 */
public interface IPublicService {

	/**
	 * @Description 发送短信验证码
	 * @param requestMap
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2018年2月8日 上午10:46:28
	 */
	Map<String, Object> sendSmsCode(Map<String, String> requestMap);

}
