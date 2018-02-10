package com.hpugs.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

import org.apache.http.client.ClientProtocolException;

import com.alibaba.fastjson.JSONObject;
import com.hpugs.commons.action.BaseAction;
import com.hpugs.commons.util.ConstantUtil;
import com.hpugs.commons.util.DESUtil;
import com.hpugs.commons.util.HttpRequestUtil;
import com.hpugs.commons.util.Utils;
import com.hpugs.service.IUserService;
import com.hpugs.service.IUserThirdLoginService;

/**
 * @Description 第三方登录授权
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2018年2月10日 下午2:34:35
 */
public class LoginOAuthAction extends BaseAction {
	
	private IUserService userService;
	private IUserThirdLoginService userThirdLoginService;
	
	private String wxLoginJsp;//微信授权登录地址

	//手机号、短信验证码、登录密码、图片验证码
	private String mobile, smsCode, passwd, imageCode;
	private String code;//微信第三方登录回调code
	private String state;//微信第三方登录回调state
	
	private String thirdType;//类型(1/2/3) 1:QQ 2:微信 3:新浪
	private String thirdAccessToken;//AccessToken
	private String thirdOpenId;//授权用户唯一标识
	private String thirdAvatar;//头像
	private String thirdNickName;//昵称
	private String thirdGender;//性别
	private String thirdCountry;//国家
	private String thirdProvince;//省
	private String thirdCity;//市
	
	/**
	 * @Description 登录页面
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @throws UnsupportedEncodingException 
	 * @date 创建时间：2017年8月8日 上午10:38:48
	 */
	public String wxLoginJsp() throws UnsupportedEncodingException{
		wxLoginJsp = "http://oauth.hpugs.com/oauthWXJsp.action?appid=" + ConstantUtil.DV_PC_APPID + "&redirect_uri=" + URLEncoder.encode("http://www.hpugs.com/oauth/wxLoginResult.action", "utf-8") + "&response_type=code&scope=snsapi_login&state=PcWxLogin&isMp=false";
		return SUCCESS;
	}
	
	/**
	 * @Description 微信授权登录回调处理
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年12月14日 上午10:59:23
	 */
	public String wxLoginResult() throws ClientProtocolException, IOException{
		if(null != code && "PcWxLogin".equalsIgnoreCase(state)){//判断是否为微信授权回调
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + ConstantUtil.MP_APPID + "&secret=" + ConstantUtil.MP_APPSECRET + "&code=" + code + "&grant_type=authorization_code";
			String resultMsg = HttpRequestUtil.sendRequestGet(url);
			JSONObject jsonObject = JSONObject.parseObject(resultMsg);
			if(null != jsonObject && null != jsonObject.getString("openid")){
				url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + jsonObject.getString("access_token") + "&openid=" + jsonObject.getString("openid");
				resultMsg = HttpRequestUtil.sendRequestGet(url);
				jsonObject = JSONObject.parseObject(resultMsg);
				thirdType = "2";//类型(1/2/3) 1:QQ 2:微信 3:新浪
				thirdOpenId = jsonObject.getString("openid");
				thirdAvatar = jsonObject.getString("headimgurl");//头像
				thirdNickName = jsonObject.getString("nickname");//昵称
				thirdGender = jsonObject.getString("sex");//性别
				thirdCountry = jsonObject.getString("country");//国家
				thirdProvince = jsonObject.getString("province");//省
				thirdCity = jsonObject.getString("city");//市
				return SUCCESS;
			}else{
				this.addFieldError("errorMsg", "微信授权失败，请联系客服");
				return "error";
			}
		}else{
			this.addFieldError("errorMsg", "微信登录失败");
			return "error";
		}
	}
	
	/**
	 * @Description QQ授权登录回调处理
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * 
	 * path:http://wiki.connect.qq.com/get_user_info
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年12月14日 上午10:59:23
	 */
	public String qqLoginResult() throws ClientProtocolException, IOException{
		if(null != thirdAccessToken && null != thirdOpenId){//判断是否为QQ授权回调
			String url = "https://graph.qq.com/user/get_user_info?access_token=" + thirdAccessToken + "&oauth_consumer_key=101445067&openid=" + thirdOpenId;
			String resultMsg = HttpRequestUtil.sendRequestGet(url);
			JSONObject jsonObject = JSONObject.parseObject(resultMsg);
			if(0 == jsonObject.getInteger("ret")){
				thirdType = "1";//类型(1/2/3) 1:QQ 2:微信 3:新浪
				thirdAvatar = jsonObject.getString("figureurl_qq_2");//头像
				thirdNickName = jsonObject.getString("nickname");//昵称
				thirdGender = jsonObject.getString("gender");//性别
				return SUCCESS;
			}else{
				this.addFieldError("errorMsg", "QQ授权失败，请联系客服");
				return "error";
			}
		}else{
			this.addFieldError("errorMsg", "QQ登录失败");
			return "error";
		}
	}
	
	/**
	 * @Description 第三方登录接口
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年12月14日 下午2:26:04
	 */
	public String thirdLogin(){
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("thirdType", thirdType);//类型(1/2/3) 1:QQ 2:微信 3:新浪
		requestParams.put("thirdOpenId", thirdOpenId);//授权用户唯一标识
		requestParams.put("thirdAvatar", thirdAvatar);//头像
		requestParams.put("thirdNickName", thirdNickName);//昵称
		requestParams.put("thirdGender", thirdGender);//性别
		requestParams.put("thirdCountry", thirdCountry);//国家
		requestParams.put("thirdProvince", thirdProvince);//省
		requestParams.put("thirdCity", thirdCity);//市
		Map<String, Object> resultMap = userThirdLoginService.saveOrUpdateOauthInfo(requestParams);
		if(null != resultMap.get(ConstantUtil.RESULT_STATUS_KEY)){
			switch (resultMap.get(ConstantUtil.RESULT_STATUS_KEY).toString()) {
				case "0"://未绑定手机号
					return "notBindingPhone";
					
				case "1"://已绑定手机号
					//将用户信息保存到session中
					setUserInfoInSession((Integer)resultMap.get("userAccountId"));
					return "okBindingPhone";
			}
		}
		this.addFieldError("errorMsg", "第三方登录失败，请联系客服");
		return "error";
	}
	
	/**
	 * @Description 第三方账号绑定手机号页面
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年12月14日 下午2:23:40
	 */
	public String thirdBindingJsp(){
		return SUCCESS;
	}
	
	/**
	 * @Description 第三方账号绑定手机号接口
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @throws IOException 
	 * @throws DataFormatException 
	 * @throws MalformedURLException 
	 * @date 创建时间：2017年12月14日 下午2:23:40
	 */
	public void thirdBinding() throws MalformedURLException, DataFormatException, IOException{
		//返会操作结果对象
		Map<String, Object> resultMap = Utils.createResultMap();
		if(null != imageCode){
			String oldImageCode = (String) session.getAttribute(ConstantUtil.PC_REGISTER_IMG_CODE_TYPE);
			if(null != oldImageCode){
				if(imageCode.equalsIgnoreCase(DESUtil.decrypt(oldImageCode))){
					//清除图片验证码
					session.removeAttribute(ConstantUtil.PC_REGISTER_IMG_CODE_TYPE);
		
					Map<String, String> requestParams = new HashMap<String, String>();
					requestParams.put("mobile", mobile);
					requestParams.put("passwd", passwd);
					requestParams.put("smsCode", smsCode);
					requestParams.put("smsCodeType", ConstantUtil.PC_THIRD_BINDING_CODE_TYPE);
					requestParams.put("thirdType", thirdType);
					requestParams.put("thirdOpenId", thirdOpenId);
					
					resultMap = userService.saveThirdAccount(requestParams);
					if(ConstantUtil.RESULT_STATUS_SUCCESS_STR.equals(resultMap.get(ConstantUtil.RESULT_STATUS_KEY)) && null != resultMap.get(ConstantUtil.RESULT_DATA_KEY)){
						setUserInfoInSession((int)resultMap.get(ConstantUtil.RESULT_DATA_KEY));
						resultMap.put(ConstantUtil.RESULT_REQUEST_SOURCE_KEY, session.getAttribute(ConstantUtil.LOGIN_REQUEST_SOURCE_PATH));
					}
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
	
	private void setUserInfoInSession(Integer userId) {
		session.setAttribute("userInfo", userId);
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setUserThirdLoginService(IUserThirdLoginService userThirdLoginService) {
		this.userThirdLoginService = userThirdLoginService;
	}

	public String getWxLoginJsp() {
		return wxLoginJsp;
	}

	public void setWxLoginJsp(String wxLoginJsp) {
		this.wxLoginJsp = wxLoginJsp;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getThirdType() {
		return thirdType;
	}

	public void setThirdType(String thirdType) {
		this.thirdType = thirdType;
	}

	public String getThirdAccessToken() {
		return thirdAccessToken;
	}

	public void setThirdAccessToken(String thirdAccessToken) {
		this.thirdAccessToken = thirdAccessToken;
	}

	public String getThirdOpenId() {
		return thirdOpenId;
	}

	public void setThirdOpenId(String thirdOpenId) {
		this.thirdOpenId = thirdOpenId;
	}

	public String getThirdAvatar() {
		return thirdAvatar;
	}

	public void setThirdAvatar(String thirdAvatar) {
		this.thirdAvatar = thirdAvatar;
	}

	public String getThirdNickName() {
		return thirdNickName;
	}

	public void setThirdNickName(String thirdNickName) {
		this.thirdNickName = thirdNickName;
	}

	public String getThirdGender() {
		return thirdGender;
	}

	public void setThirdGender(String thirdGender) {
		this.thirdGender = thirdGender;
	}

	public String getThirdCountry() {
		return thirdCountry;
	}

	public void setThirdCountry(String thirdCountry) {
		this.thirdCountry = thirdCountry;
	}

	public String getThirdProvince() {
		return thirdProvince;
	}

	public void setThirdProvince(String thirdProvince) {
		this.thirdProvince = thirdProvince;
	}

	public String getThirdCity() {
		return thirdCity;
	}

	public void setThirdCity(String thirdCity) {
		this.thirdCity = thirdCity;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}

}
