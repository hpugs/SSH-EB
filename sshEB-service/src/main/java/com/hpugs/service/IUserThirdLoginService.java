package com.hpugs.service;

import java.util.Map;

/**
 * @Description 第三方登录
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2018年2月10日 下午3:30:52
 */
public interface IUserThirdLoginService {

	/**
	 * @Description 保存用户第三方登录信息
	 * @param requestParams
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2018年2月10日 下午3:59:17
	 */
	Map<String, Object> saveOrUpdateOauthInfo(Map<String, String> requestParams);

}
