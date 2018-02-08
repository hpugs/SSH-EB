package com.hpugs.action;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.hpugs.commons.action.BaseAction;
import com.hpugs.commons.util.ConstantUtil;
import com.hpugs.commons.util.DESUtil;
import com.hpugs.commons.util.Utils;
import com.hpugs.service.IUserService;

/**
 * @Description 用户模块
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2017年12月24日 下午5:03:53
 */
public class UserAction extends BaseAction {
	
	private IUserService userService;
	
	//姓名、账号、密码、短信验证码、图片验证码、访问子页面地址、头像地址、性别、邮箱
	private String name, account, passwd, smsCode, imageCode, itemPath, headImage, gender, email;
	
	/**
	 * @Description 登录页面
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年12月25日 下午2:13:28
	 */
	public String loginJsp(){
		String referer =  request.getHeader("referer");//获的请求来源
		if(null == referer || 0 < referer.indexOf("user_registerJsp")){//从注册页面跳入返回首页
			referer = "indexJsp.action";
		}
		session.setAttribute(ConstantUtil.LOGIN_REQUEST_SOURCE_PATH, referer);
		return SUCCESS;
	}
	
	/**
	 * @Description 注册页面
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年12月25日 下午2:13:28
	 */
	public String registerJsp(){
		return SUCCESS;
	}
	
	/**
	 * @Description 账号密码登录
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年12月25日 下午3:12:32
	 */
	public void checkAccount(){
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("account", account);
		requestMap.put("passwd", passwd);
		requestMap.put("requestIp", Utils.getRequestIp(request));
		requestMap.put("sessionId", session.getId());
		Map<String, Object> resultMap = userService.loginAccount(requestMap);
		if(ConstantUtil.RESULT_STATUS_SUCCESS_STR.equals(resultMap.get(ConstantUtil.RESULT_STATUS_KEY)) && null != resultMap.get(ConstantUtil.RESULT_DATA_KEY)){
			setUserInfoInSession((int)resultMap.get(ConstantUtil.RESULT_DATA_KEY));
			resultMap.put(ConstantUtil.RESULT_REQUEST_SOURCE_KEY, session.getAttribute(ConstantUtil.LOGIN_REQUEST_SOURCE_PATH));
		}
		writeJsonFromObject(resultMap);
	}
	
	/**
	 * @Description 手机号验证码登录
	 * @param userAccountService
	 *
	 * @author 高尚
	 * @version 1.0
	 * @throws ParseException 
	 * @date 创建时间：2017年12月25日 下午3:12:47
	 */
	public void checkMobile(){
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("account", account);
		requestMap.put("smsCode", smsCode);
		Map<String, Object> resultMap = userService.loginMobile(requestMap);
		if(ConstantUtil.RESULT_STATUS_SUCCESS_STR.equals(resultMap.get(ConstantUtil.RESULT_STATUS_KEY)) && null != resultMap.get(ConstantUtil.RESULT_DATA_KEY)){
			setUserInfoInSession((int)resultMap.get(ConstantUtil.RESULT_DATA_KEY));
			resultMap.put(ConstantUtil.RESULT_REQUEST_SOURCE_KEY, session.getAttribute(ConstantUtil.LOGIN_REQUEST_SOURCE_PATH));
		}
		writeJsonFromObject(resultMap);
	}
	
	/**
	 * @Description 注册账号
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年12月26日 下午3:07:42
	 */
	public void registerAccount(){
		//返会操作结果对象
		Map<String, Object> resultMap = Utils.createResultMap();
		if(null != imageCode){
			String oldImageCode = (String) session.getAttribute(ConstantUtil.PC_REGISTER_CODE_TYPE);
			if(null != oldImageCode){
				if(imageCode.equalsIgnoreCase(DESUtil.decrypt(oldImageCode))){
					Map<String, String> requestMap = new HashMap<String, String>();
					requestMap.put("account", account);
					requestMap.put("passwd", passwd);
					requestMap.put("smsCode", smsCode);
					resultMap = userService.saveUser(requestMap);
				}else{
					resultMap.put(ConstantUtil.RESULT_MSG_KEY, "图片验证码输入错误");
				}
			}else{
				resultMap.put(ConstantUtil.RESULT_MSG_KEY, "图片验证码已失效，请重新获取");
			}
		}else{
			resultMap.put(ConstantUtil.RESULT_MSG_KEY, "请输入图片验证码");
		}
		writeJsonFromObject(resultMap);
	}
	
	/**
	 * @Description 重置密码
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年12月26日 上午11:46:49
	 */
	public void forgetPasswd(){
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("account", account);
		requestMap.put("passwd", passwd);
		requestMap.put("smsCodeType", ConstantUtil.PC_FORGET_CODE_TYPE);
		requestMap.put("smsCode", smsCode);
		Map<String, Object> resultMap = userService.updatePasswd(requestMap);
		writeJsonFromObject(resultMap);
	}
	
	/**
	 * @Description 退出登录
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2018年1月8日 下午4:43:01
	 */
	public String loginOut(){
		session.removeAttribute("userInfo");
		return SUCCESS;
	}
	
	/**
	 * @Description 封装用户信息到Session中
	 * @param id
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年12月25日 下午7:50:11
	 */
	private void setUserInfoInSession(final Integer userId){
		session.setAttribute("userInfo", userService.getUserInfo(userId));
	}
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}

	public String getItemPath() {
		return itemPath;
	}

	public void setItemPath(String itemPath) {
		this.itemPath = itemPath;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
