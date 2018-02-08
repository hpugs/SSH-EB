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

/**
 * @Description 公共Action
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2018年2月3日 下午5:53:20
 */
public class PublicAction extends BaseAction {
	
	private IPublicService publicService;
	
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
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("mobile", mobile);
		requestMap.put("codeType", codeType);
		requestMap.put("source", "1");//来源（1：PC（默认）；2：H5；3、Android；4、IOS；5、ERP）
		requestMap.put("requestIp", Utils.getRequestIp(request));
		Map<String, Object> resultMap = publicService.sendSmsCode(requestMap);
		
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

}
