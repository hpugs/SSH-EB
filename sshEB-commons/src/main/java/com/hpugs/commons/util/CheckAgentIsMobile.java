package com.hpugs.commons.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 检查访问来源
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2017年12月4日 上午9:45:05
 */
public class CheckAgentIsMobile {
	
	private final static String[] agent = { "Android", "iPhone", "iPod","iPad", "Windows Phone", "MQQBrowser" };//定义移动端请求的所有可能类型
	
	/**
	 * @Description 检查访问是否来自于移动端
	 * @param request
	 * @return true:移动端；false:PC端
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年12月4日 上午9:52:53
	 */
	public static boolean checkMobile(HttpServletRequest request){
		boolean flag = false;
		String userAgent = request.getHeader("User-Agent");
		if(null != userAgent){
			if (!userAgent.contains("Windows NT") || (userAgent.contains("Windows NT") && userAgent.contains("compatible; MSIE 9.0;"))) {
				// 排除 苹果桌面系统
				if (!userAgent.contains("Windows NT") && !userAgent.contains("Macintosh")) {
					for (String item : agent) {
						if (userAgent.contains(item)) {
							flag = true;
							break;
						}
					}
				}
			}
		}
		return flag;
	}

}
