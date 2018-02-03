package com.hpugs.commons.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @Description 使用httpclient请求
 * @author 付雄
 * @version 1.0
 * @date 创建时间：2017年11月7日 下午3:59:48
 */

public class HttpClientRequestUtil {
	public static CloseableHttpClient httpClient;
	static {
		if (httpClient == null) {
			httpClient = HttpClients.createDefault();
		}
	}

	// post 请求
	public static String doPost(String url, Map<String, Object> params) {
		if (params == null || params.size() == 0) {
			return doGet(url);
		}
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (Entry<String, Object> entry : params.entrySet()) {
			nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			CloseableHttpResponse response = httpClient.execute(httpPost);
			String res = EntityUtils.toString(response.getEntity(), "utf-8");
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// get 请求
	public static String doGet(String url) {
		try {
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			String res = EntityUtils.toString(response.getEntity(), "utf-8");
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}