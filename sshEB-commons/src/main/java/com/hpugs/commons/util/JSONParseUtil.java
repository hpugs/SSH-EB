package com.hpugs.commons.util;

import com.alibaba.fastjson.JSON;

/**
 * @Description json和bean转换
 * @author 付雄
 * @version 1.0
 * @date 创建时间：2017年11月8日 上午10:25:07
 */

public class JSONParseUtil {

	public static String beanToJson(Object o) {
		String res = JSON.toJSONString(o);
		return res;
	}

	public static <T> T jsonToBean(JSON json, Class<T> clazz) {
		T t = json.toJavaObject(json, clazz);
		return t;
	}
	
}
