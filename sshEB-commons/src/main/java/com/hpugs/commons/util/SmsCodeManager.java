package com.hpugs.commons.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 短信验证码管理类
 *
 * @author 高尚 
 * @version 1.0
 * @date 创建时间：2017年12月25日 下午5:18:34
 */
public class SmsCodeManager {
	
	/**
	 * 全局短信验证码Map
	 */
	private static Map<String, Map<String, String>> smsCodeMap = new HashMap<String, Map<String, String>>();
	
	/**
	 * @Description 保存短信验证码
	 * @param phone
	 * @param smsCode
	 * 
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年9月29日 上午11:37:08
	 */
	public static void saveSmsCode(String phone, Map<String, String> smsCode){
		smsCodeMap.put(phone, smsCode);
	}
	
	/**
	 * @Description 通过电话号码得到短信验证码
	 * @param phone
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年9月29日 上午11:37:31
	 */
	public static Map<String, String> getSmsCodeMapById(String phone){
		return smsCodeMap.get(phone);
	}

}
