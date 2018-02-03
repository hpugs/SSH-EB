package com.hpugs.action;

import com.hpugs.commons.action.BaseAction;

/**
 * @Description 登录Action
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2018年2月3日 下午5:25:08
 */
public class LoginAction extends BaseAction {
	
	/**
	 * @Description 登录页面
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2018年2月3日 下午5:29:06
	 */
	public String loginJsp(){
		return SUCCESS;
	}

	/**
	 * @Description 账号密码登录接口
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2018年2月3日 下午5:29:38
	 */
	public void loginCheck(){
		
	}
	
	/**
	 * @Description 短信验证码登录
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2018年2月3日 下午5:30:22
	 */
	public void smsCodeLoginCheck(){
		
	}
	
}
