package com.hpugs.commons.util;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @Description HTTP请求工具类
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2017年8月15日 上午9:30:55
 */
public class HttpRequestUtil {
	
	private static DefaultHttpClient httpClient;
	
	static{
		//简单的单例
		if(null == httpClient){
			httpClient = new DefaultHttpClient();
		}
	}
	
	/**
	 * @Description HttpPost请求
	 * @param url url地址
	 * @param params 传入参数
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年8月15日 上午9:41:25
	 */
	public static String sendRequestPost(String url, final Map<String, Object> paramsMap, final String tocken) throws ClientProtocolException, IOException{
		String resultMsg = "请求失败";//反馈参数
		
		HttpPost method = new HttpPost(url);
		
		if (null != paramsMap) {
			//拼接请求参数
			List<NameValuePair> params = new ArrayList<NameValuePair>(); 
			for(String key : paramsMap.keySet()){
				params.add(new BasicNameValuePair(key, paramsMap.get(key).toString()));
			}
			
			//解决中文乱码问题
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
            method.setEntity(entity);
        }
		if(null != tocken){
			method.setHeader("Authorization", "BasicAuth:" + tocken);
		}
        HttpResponse response = httpClient.execute(method);
        url = URLDecoder.decode(url.replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "UTF-8");
        //请求发送成功，并得到响应
        if (response.getStatusLine().getStatusCode() == 200) {
        	//读取服务器返回过来的json字符串数据
        	HttpEntity entity = response.getEntity();//调用getEntity()方法获取到一个HttpEntity实例
        	resultMsg = EntityUtils.toString(entity, "utf-8");//用EntityUtils.toString()这个静态方法将HttpEntity转换成字符串,防止服务器返回的数据带有中文,所以在转换的时候将字符集指定成utf-8就可以了
        }
		
		return resultMsg;
	}
	
	/**
	 * @Description HttpPost请求
	 * @param url url地址
	 * @param paramsMap 传入参数
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年8月29日 下午4:33:16
	 */
	public static String sendRequestJsonPost(String url, String params) throws ClientProtocolException, IOException{
		String resultMsg = "请求失败";//反馈参数
		
		HttpPost method = new HttpPost(url);
		method.setHeader("Content-Type", "application/json;charset=UTF-8");
		if (null != params) {
	        StringEntity stringEntity = new StringEntity(params, "UTF-8");
            method.setEntity(stringEntity);
        }else{
        	StringEntity stringEntity = new StringEntity("{}");
            method.setEntity(stringEntity);
        }
        HttpResponse response = httpClient.execute(method);
        url = URLDecoder.decode(url.replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "UTF-8");
        //请求发送成功，并得到响应
        if (response.getStatusLine().getStatusCode() == 200) {
        	//读取服务器返回过来的json字符串数据
        	HttpEntity entity = response.getEntity();//调用getEntity()方法获取到一个HttpEntity实例
        	resultMsg = EntityUtils.toString(entity, "utf-8");//用EntityUtils.toString()这个静态方法将HttpEntity转换成字符串,防止服务器返回的数据带有中文,所以在转换的时候将字符集指定成utf-8就可以了
        }
		
		return resultMsg;
	}
	
	/**
	 * @Description HttpGet请求
	 * @param url url地址
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年8月15日 上午9:42:07
	 */
	public static String sendRequestGet(String url) throws ClientProtocolException, IOException{
		String resultMsg = "请求失败";//反馈参数
		
		HttpGet method = new HttpGet(url);
        HttpResponse response = httpClient.execute(method);
        url = URLDecoder.decode(url.replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "UTF-8");
        //请求发送成功，并得到响应
        if (response.getStatusLine().getStatusCode() == 200) {
        	//读取服务器返回过来的json字符串数据
        	HttpEntity entity = response.getEntity();//调用getEntity()方法获取到一个HttpEntity实例
        	resultMsg = EntityUtils.toString(entity, "utf-8");//用EntityUtils.toString()这个静态方法将HttpEntity转换成字符串,防止服务器返回的数据带有中文,所以在转换的时候将字符集指定成utf-8就可以了
        }
		
		return resultMsg;
	}

}
