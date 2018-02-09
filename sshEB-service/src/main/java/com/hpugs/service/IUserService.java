package com.hpugs.service;

import java.util.Map;

import com.hpugs.entity.po.UserAccount;

/**
 * @Description 用户模块
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2018年2月8日 上午9:43:56
 */
public interface IUserService {

	/**
	 * @Description 账号密码登录
	 * @param requestMap
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2018年2月8日 上午10:03:31
	 */
	Map<String, Object> loginAccount(Map<String, String> requestMap);

	/**
	 * @Description 手机号验证码登录
	 * @param requestMap
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2018年2月8日 上午10:04:02
	 */
	Map<String, Object> loginMobile(Map<String, String> requestMap);

	/**
	 * @Description 账号注册
	 * @param requestMap
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2018年2月8日 上午10:05:08
	 */
	Map<String, Object> saveUser(Map<String, String> requestMap);
	
	/**
	 * @Description 重置密码
	 * @param requestMap
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2018年2月8日 上午10:25:48
	 */
	Map<String, Object> updatePasswd(Map<String, String> requestMap);

	/**
	 * @Description 根据用户Id得到用户详情
	 * @param userId
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2018年2月8日 上午10:05:30
	 */
	Map<String, Object> getUserInfo(Integer userId);

	/**
	 * @Description 根据用户Id得到账号对象
	 * @param string
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2018年2月9日 下午3:06:54
	 */
	UserAccount getUserAccountById(String id);

	
}
