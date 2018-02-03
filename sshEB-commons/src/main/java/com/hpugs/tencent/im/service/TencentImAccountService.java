package com.hpugs.tencent.im.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.alibaba.fastjson.JSON;
import com.hpugs.commons.util.HttpRequestUtil;
import com.hpugs.tencent.im.model.TencentImKey;

/**
 * @Description 腾讯Im账户管理
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2017年8月29日 下午1:46:14
 */
public class TencentImAccountService {

	/**
	 * @Description 独立模式账号导入接口
	 * @path https://www.qcloud.com/document/product/269/1608
	 * @param identifier 用户名，长度不超过 32 字节
	 * @param nick 用户昵称
	 * @param faceUrl 用户头像URL。
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年8月29日 下午3:36:40
	 */
	public static String CreateAloneImAccount(final String identifier, final String nick, final String faceUrl) throws ClientProtocolException, IOException{
		String result = "success";
		if(null != identifier && 0 < identifier.length() && 32 >= identifier.length()){//判断腾讯IM账户唯一标示是否为空
			String params = "{";
			params += "\"Identifier\":\"" + identifier + "\"";//必填	用户名，长度不超过 32 字节
			if(null != nick && 0 < nick.length()){
				params += ",\"Nick\":\"" + nick + "\"";//选填	用户昵称
			}
			if(null != faceUrl && 0 < faceUrl.length()){
				params += ",\"FaceUrl\":\"" + faceUrl + "\"";//选填	   用户头像URL。
			}
			params += "}";
			String url = "https://console.tim.qq.com/v4/im_open_login_svc/account_import?usersig="+ TencentImKey.USER_SIG +"&identifier="+ TencentImKey.IDENTIFIER +"&sdkappid="+ TencentImKey.SDK_APP_ID +"&random="+ TencentImKey.RANDOM +"&contenttype=json";
			result = HttpRequestUtil.sendRequestJsonPost(url, params);
		}else{
			result = "error";
		}
		return result;
	}
	
	/**
	 * @Description 独立模式帐号批量导入接口
	 * @path https://www.qcloud.com/document/product/269/4919
	 * @param identifiers IM账户集合：["test1","test2","test3","test4","test5"]
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年8月29日 下午3:51:30
	 */
	public static String ImportLargeImAccount(final List<String> identifiers) throws ClientProtocolException, IOException{
		String result = "success";
		if(null != identifiers && 0 < identifiers.size()){//判断腾讯IM账户唯一标示是否为空
			String params = "{\"Accounts\":" + identifiers.toString() + "}";//必填	用户名集合
			String url = "https://console.tim.qq.com/v4/im_open_login_svc/multiaccount_import?usersig="+ TencentImKey.USER_SIG +"&identifier="+ TencentImKey.IDENTIFIER +"&sdkappid="+ TencentImKey.SDK_APP_ID +"&random="+ TencentImKey.RANDOM +"&contenttype=json";
			result = HttpRequestUtil.sendRequestJsonPost(url, params);
		}else{
			result = "error";
		}
		return result;
	}
	
	/**
	 * @Description 获取用户在线状态
	 * @path https://www.qcloud.com/document/product/269/2566
	 * @param identifiers IM账户集合：["test1","test2","test3","test4","test5"]
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年8月29日 下午3:51:30
	 */
	public static String getUsersLoginState(final List<String> identifiers) throws ClientProtocolException, IOException{
		String result = "success";
		if(null != identifiers && 0 < identifiers.size()){//判断腾讯IM账户唯一标示是否为空
			String params = "{\"To_Account\":" + identifiers.toString() + "}";//必填	用户名集合
			String url = "https://console.tim.qq.com/v4/openim/querystate?usersig="+ TencentImKey.USER_SIG +"&identifier="+ TencentImKey.IDENTIFIER +"&sdkappid="+ TencentImKey.SDK_APP_ID +"&random="+ TencentImKey.RANDOM +"&contenttype=json";
			result = HttpRequestUtil.sendRequestJsonPost(url, params);
		}else{
			result = "error";
		}
		return result;
	}
	
	/**
	 * @Description 帐号登录态失效接口
	 * @path https://www.qcloud.com/document/product/269/3853
	 * @param identifier 用户名，长度不超过 32 字节
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年8月29日 下午3:59:02
	 */
	public static String removeNowUserLoginState(final String identifier) throws ClientProtocolException, IOException{
		String result = "success";
		if(null != identifier && 0 < identifier.length()){//判断腾讯IM账户唯一标示是否为空
			String params = "{\"Identifier\":\"" + identifier + "\"}";//必填	用户名，长度不超过 32 字节
			String url = "https://console.tim.qq.com/v4/im_open_login_svc/kick?usersig="+ TencentImKey.USER_SIG +"&identifier="+ TencentImKey.IDENTIFIER +"&sdkappid="+ TencentImKey.SDK_APP_ID +"&random="+ TencentImKey.RANDOM +"&contenttype=json";
			result = HttpRequestUtil.sendRequestJsonPost(url, params);
		}else{
			result = "error";
		}
		return result;
	}
	
	/**
	 * @Description 拉取资料
	 * @param toAccount
	 * @param tagList
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年10月27日 下午3:36:29
	 */
	public static String getUserImInfo(final List<String> toAccount, final List<String> tagList) throws ClientProtocolException, IOException{
		String result = "success";
		if(null != toAccount && 0 < toAccount.size()){//判断腾讯IM账户唯一标示是否为空
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("To_Account", toAccount);
			params.put("TagList", tagList);
			String url = "https://console.tim.qq.com/v4/profile/portrait_get?usersig="+ TencentImKey.USER_SIG +"&identifier="+ TencentImKey.IDENTIFIER +"&sdkappid="+ TencentImKey.SDK_APP_ID +"&random="+ TencentImKey.RANDOM +"&contenttype=json";
			result = HttpRequestUtil.sendRequestJsonPost(url, JSON.toJSONString(params));
		}else{
			result = "error";
		}
		return result;
	}
	
}
