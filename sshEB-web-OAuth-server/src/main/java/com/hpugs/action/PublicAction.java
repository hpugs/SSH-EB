package com.hpugs.action;

import com.hpugs.commons.action.BaseAction;

/**
 * @Description 公共Action
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2018年2月3日 下午4:28:32
 */
public class PublicAction extends BaseAction {
	
	/**
	 * @Description 首页
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2018年2月3日 下午4:29:22
	 */
	public String indexJsp(){
		return SUCCESS;
	}
	
	/**
	 * @Description 微信授权页面
	 * @return
	 * 
	 * 用户获取微信授权完成返回的Code值
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2018年2月3日 下午5:08:22
	 */
	public String oauthWX(){
		return SUCCESS;
	}

}
