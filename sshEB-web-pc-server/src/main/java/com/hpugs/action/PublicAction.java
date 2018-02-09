package com.hpugs.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.hpugs.commons.action.BaseAction;
import com.hpugs.commons.util.ConstantUtil;
import com.hpugs.commons.util.DESUtil;
import com.hpugs.commons.util.Utils;
import com.hpugs.commons.util.VerifyCodeUtil;
import com.hpugs.service.IPublicService;
import com.hpugs.service.IUserService;

/**
 * @Description 公共Action
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2018年2月3日 下午5:53:20
 */
public class PublicAction extends BaseAction {
	
	private IPublicService publicService;
	private IUserService userService;
	
	//手机号、验证码类型
	private String mobile, codeType;
	
	/**
	 * @Description 首页
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2018年2月3日 下午5:59:14
	 */
	public String indexJsp(){
		return SUCCESS;
	}
	
	/**
	 * @Description 发送短信验证码
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年12月25日 下午3:14:01
	 */
	public void sendSmsCode(){
		//返会操作结果对象
		Map<String, Object> resultMap = Utils.createResultMap();
		//判断是否为修改密码
		if(ConstantUtil.PC_UPDATE_CODE_TYPE.equals(codeType)){
			Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute("userInfo");
			if(null == userInfo){
				resultMap.put(ConstantUtil.RESULT_STATUS_KEY, ConstantUtil.RESULT_STATUS_NOTLOGIN_STR);
				resultMap.put(ConstantUtil.RESULT_MSG_KEY, ConstantUtil.RESULT_MSG_NOTLOGIN);
				writeJsonFromObject(resultMap);
				return;
			}
			mobile = userService.getUserAccountById(userInfo.get("userId").toString()).getMobile();
		}
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("mobile", mobile);//手机号
		requestParams.put("smsCodeType", codeType);//短信验证码类型
		requestParams.put("source", "1");//来源（1：PC（默认）；2：H5；3、Android；4、IOS；5、ERP）
		requestParams.put("requestIp", Utils.getRequestIp(request));//ip地址
		requestParams.put("aliSmsCodeType", "1");//短信验证码
		requestParams.put("aliMessageCode", ConstantUtil.MESSAGE_CODE);//短信验证码签名
		resultMap = publicService.sendSmsCode(requestParams);
		writeJsonFromObject(resultMap);
	}
	
	/**
	 * @Description 获取图片验证码
	 * @throws IOException
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年12月26日 下午2:54:06
	 */
	public void getImageCode() throws IOException{
		String imageCode = VerifyCodeUtil.outputVerifyImage(110, 30, response.getOutputStream(), ConstantUtil.IMG_CODE_LENGTH);
		//将图片验证码保存到session中，防止被获取对验证加密
		session.setAttribute(ConstantUtil.PC_REGISTER_IMG_CODE_TYPE, DESUtil.encrypt(imageCode));
	}

	public void setPublicService(IPublicService publicService) {
		this.publicService = publicService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

}
