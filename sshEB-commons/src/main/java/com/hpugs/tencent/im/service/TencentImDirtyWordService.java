package com.hpugs.tencent.im.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.hpugs.commons.util.HttpRequestUtil;
import com.hpugs.tencent.im.model.TencentImKey;

/**
 * @Description 脏字管理
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2017年8月29日 下午4:02:15
 */
public class TencentImDirtyWordService {

	/**
	 * @Description APP管理员可以通过该接口查询已设置的自定义脏字
	 * @path https://www.qcloud.com/document/product/269/2396
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年8月29日 下午4:05:27
	 */
	public static String getDirtyWordList() throws ClientProtocolException, IOException{
		String result = "success";
		String url = "https://console.tim.qq.com/v4/openim_dirty_words/get?usersig="+ TencentImKey.USER_SIG +"&identifier="+ TencentImKey.IDENTIFIER +"&sdkappid="+ TencentImKey.SDK_APP_ID +"&random="+ TencentImKey.RANDOM +"&contenttype=json";
		result = HttpRequestUtil.sendRequestJsonPost(url, null);
		return result;
	}
	
	/**
	 * @Description 添加APP自定义脏字
	 * @path https://www.qcloud.com/document/product/269/2397
	 * @param dirtyWordsList 自定义脏字列表（必填），列表中的脏字不能超过50个
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年8月29日 下午4:09:36
	 */
	public static String addDirtyWord(final List<String> dirtyWordsList) throws ClientProtocolException, IOException{
		String result = "success";
		if(null != dirtyWordsList && 0 < dirtyWordsList.size() && 50 >= dirtyWordsList.size()){//判断腾讯IM账户唯一标示是否为空
			String params = "{\"DirtyWordsList\":" + dirtyWordsList.toString() + "}";//必填	自定义脏字列表（必填），列表中的脏字不能超过50个,每个自定义脏字不能超过200字节
			String url = "https://console.tim.qq.com/v4/openim_dirty_words/add?usersig="+ TencentImKey.USER_SIG +"&identifier="+ TencentImKey.IDENTIFIER +"&sdkappid="+ TencentImKey.SDK_APP_ID +"&random="+ TencentImKey.RANDOM +"&contenttype=json";
			result = HttpRequestUtil.sendRequestJsonPost(url, params);
		}else{
			result = "error";
		}
		return result;
	}
	
	/**
	 * @Description 删除APP自定义脏字
	 * @path https://www.qcloud.com/document/product/269/2398
	 * @param dirtyWordsList 自定义脏字列表（必填），列表中的脏字不能超过50个
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年8月29日 下午4:09:36
	 */
	public static String deleteDirtyWord(final List<String> dirtyWordsList) throws ClientProtocolException, IOException{
		String result = "success";
		if(null != dirtyWordsList && 0 < dirtyWordsList.size() && 50 >= dirtyWordsList.size()){//判断腾讯IM账户唯一标示是否为空
			String params = "{\"DirtyWordsList\":" + dirtyWordsList.toString() + "}";//必填	自定义脏字列表（必填），列表中的脏字不能超过50个,每个自定义脏字不能超过200字节
			String url = "https://console.tim.qq.com/v4/openim_dirty_words/delete?usersig="+ TencentImKey.USER_SIG +"&identifier="+ TencentImKey.IDENTIFIER +"&sdkappid="+ TencentImKey.SDK_APP_ID +"&random="+ TencentImKey.RANDOM +"&contenttype=json";
			result = HttpRequestUtil.sendRequestJsonPost(url, params);
		}else{
			result = "error";
		}
		return result;
	}
	
}
