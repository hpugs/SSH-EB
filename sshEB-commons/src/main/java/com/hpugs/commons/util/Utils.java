package com.hpugs.commons.util;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author 付雄
 * @Description: 所有工具方法大杂烩
 * @version 1.0
 * @date 2017年6月2日 下午8:45:57
 */
public class Utils {
	
	/**
	 * @Description 生成返回参数Map对象
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年9月14日 下午4:23:00
	 */
	public static Map<String, Object> createResultMap(){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(ConstantUtil.RESULT_ACTION_KEY, "");
		resultMap.put(ConstantUtil.RESULT_STATUS_KEY, ConstantUtil.RESULT_STATUS_FAIL_STR);
		resultMap.put(ConstantUtil.RESULT_MSG_KEY, ConstantUtil.RESULT_MSG_FAIL);
		Map<String, Object> dataResult = new HashMap<String, Object>();
		resultMap.put(ConstantUtil.RESULT_DATA_KEY, dataResult);
		return resultMap;
	}
	
	/**
	 * @Description 移除最后一位匹配字符
	 * @param str 待处理字符串
	 * @param ch 最后一位字符
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2018年2月1日 下午2:26:12
	 */
	public static String removeStringLastChar(String str, String ch){
		if(objectIsNotEmpty(str) && objectIsNotEmpty(ch)){
			return str.lastIndexOf(ch) != str.length()-1 ? str : str.substring(0, str.length()-1);
		}else{
			return "";
		}
	}
	
	/**
	 * 
	 * @return 获取23位时间+四位随机数的编码
	 */
	public static String getRandomCode() {
		String randomCode = null;
		SimpleDateFormat sDateFormat;
		Random r = new Random();
		int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
		sDateFormat = new SimpleDateFormat("yyyyMMddHHmmsssss"); // 时间格式化的格式
		randomCode = sDateFormat.format(new Date()) + rannum;
		return randomCode;
	}

	/**
	 * 返回随机8位字符串。
	 * 
	 * @return 返回随机8位字符串
	 */
	public static String get8RandomCodeForUUID() {
		return UUID.randomUUID().toString().toUpperCase().substring(0, 8);
	}

	/**
	 * 返回随机传入的bitNum位字符串。
	 * 
	 * @param bitNum
	 *            传入返回的整形位数
	 * @return 返回随机bitNum位字符串
	 */
	public static String getRandomCodeForUUID(int bitNum) {
		return UUID.randomUUID().toString().toUpperCase().substring(0, bitNum);
	}

	/**
	 * 判断字符串是否为空 ， 空：false ; 非空：true。
	 * 
	 * @param ch
	 *            传入字符串
	 * @return 真假
	 */
	public static boolean stringIsNotEmpty(String ch) {
		if (ch != null && !"".equals(ch)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断Object对象不为空 ， 空：false ; 非空：true。
	 * 
	 * @param obj
	 *            传入对象
	 * @return 真假
	 */
	public static boolean objectIsNotEmpty(Object obj) {
		if (obj != null && !"".equals(obj)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断Object对象为空 ， 空：true; 非空：false。
	 * 
	 * @param obj
	 *            传入对象
	 * @return 真假
	 */
	public static boolean objectIsEmpty(Object obj) {
		if (obj != null && !"".equals(obj)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断集合是否为空， 空：false ; 非空：true。
	 * 
	 * @param coll
	 *            传入集合
	 * @return 真假
	 */
	public static boolean collectionIsNotEmpty(Collection<?> coll) {
		if (coll != null && coll.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return 获取MD5编码字符串值，算法不可逆。
	 */
	public final static String EncodeMd5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] md5Password = md5.digest(s.getBytes());
			int j = md5Password.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md5Password[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param urls
	 *            带有“@”组合的“，”分割的URL字符串
	 * @return 返回一个不带@后面组合信息的带有“，”的URl组合字符串
	 */
	public static String getUrlsValueByDeal(String urls) {
		if (urls != null && !"".equals(urls)) {
			StringBuilder sb = new StringBuilder();
			String[] fileUrls = urls.split(",");
			if (fileUrls.length == 1) {
				sb.append(fileUrls[0].substring(0, fileUrls[0].indexOf("@")));
			} else {
				for (int i = 0; i < fileUrls.length; i++) {
					if (i == fileUrls.length - 1) {
						sb.append(fileUrls[i].substring(0, fileUrls[i].indexOf("@")));
					} else {
						sb.append(fileUrls[i].substring(0, fileUrls[i].indexOf("@"))).append(",");
					}
				}
			}
			return sb.toString();
		} else {
			return "";
		}
	}

	/**
	 * 判断exsitsUrlsStr中是否包含地址deleteURL。
	 * 
	 * @param exsitsUrlsStr
	 *            传入包含url
	 * @param deleteURL
	 *            传入查找url
	 * @return 真假
	 */
	public static boolean isFindUrl(String exsitsUrlsStr, String deleteURL) {
		boolean isFind = false;
		String[] exsitsUrls = exsitsUrlsStr.split(",");
		for (int i = 0; i < exsitsUrls.length; i++) {
			if (exsitsUrls[i].equals(deleteURL)) {
				isFind = true;
				break;
			}
		}
		return isFind;
	}

	/**
	 * 将条件中汉字带英文的'替换成" 避免和合sql的的'产生冲突，适用于Oracle
	 * 
	 * @param condition
	 * @return
	 */
	public static String replaceSelectCharForSQL(String condition) {
		return condition.replaceAll("'", "''");
	}

	/**
	 * 转换字符串为int 转换失败返回defaultVal
	 * 
	 * @param str
	 * @param defaultVal
	 * @return
	 */
	public static int parseInt(String str, int defaultVal) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return defaultVal;
		}
	}

	/**
	 * 把集合转换成字符串，以拼接符的形式分开
	 * 
	 * @param list
	 *            集合
	 * @param str
	 *            分隔符
	 * @return
	 */
	public static String listToString(List list, String str) {
		if (list != null) {
			Object[] arrayStr = list.toArray();
			return StringUtils.join(arrayStr, ",");
		} else {
			return "";
		}
	}

	/**
	 * 删除原有的url串中的传入的删除地址deleteURL，并更新最新的URL串返回
	 * 
	 * @param fileUploadRoot
	 *            根路径，上传文件路径，网络映射磁盘
	 * @param exsitsUrlsStr
	 *            数据库中原有的url串
	 * @param deleteURL
	 *            传入的删除地址deleteURL
	 * @return
	 */
	public static String getFinalUrlsString(String fileUploadRoot, String exsitsUrlsStr, String deleteURL) {
		String[] exsitsUrls = exsitsUrlsStr.split(",");
		String url = deleteURL.substring(0, deleteURL.indexOf("@"));
		String filePath = (fileUploadRoot + url).replace("/", "\\");
		File file = new File(filePath);
		if (file.exists())
			file.delete();// 删除磁盘文件
		// 更新数据库中的url值
		if (exsitsUrls.length == 1)
			exsitsUrlsStr = "";
		else {
			StringBuilder sb = new StringBuilder();
			List<String> urlList = new ArrayList<String>();
			for (int i = 0; i < exsitsUrls.length; i++) {
				if (!exsitsUrls[i].equals(deleteURL))
					urlList.add(exsitsUrls[i]);
			}
			for (int i = 0; i < urlList.size(); i++) {
				if (i == urlList.size() - 1)
					sb.append(urlList.get(i));
				else
					sb.append(urlList.get(i)).append(",");
			}
			exsitsUrlsStr = sb.toString();
		}
		return exsitsUrlsStr;
	}
	
	/**
	 * @Description 从Request对象中获得客户端IP，处理了HTTP代理服务器和Nginx的反向代理截取了ip
	 * @path http://www.cnblogs.com/ITtangtang/p/3927768.html
	 * @param request 用户请求
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年8月25日 上午11:16:18
	 */
	public static String getRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }
	
	/**
	 * @Description 计算两个日期之间的间隔
	 * @param newDate 当前日期
	 * @param oldDate 待计算日期
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年8月26日 下午12:13:49
	 */
	public static String computeDateInterval(Date newDate, Date oldDate){
		String interval = "刚刚";
		
		long minutes = newDate.getTime() - oldDate.getTime();
		
		if(0 >= (minutes / 355 / 24 / 60 / 60 / 1000)){
			if(0 >= (minutes / 30 / 24 / 60 / 60 / 1000)){
				if(0 >= (minutes / 24 / 60 / 60 / 1000)){
					if(0 >= (minutes / 60 / 60 / 1000)){
						minutes = (minutes / 60 / 1000);
						if(5 < minutes){
							interval = (minutes % 5 == 0 ? minutes : minutes/5 * 5) + "分钟前";
						}
					}else{
						interval = (minutes / 60 / 60 / 1000) + "小时前";
					}
				}else{
					interval = (minutes / 24 / 60 / 60 / 1000) + "日前";
				}
			}else{
				interval = (minutes / 30 / 24 / 60 / 60 / 1000) + "月前";
			}
		}else{
			interval = (minutes / 355 / 24 / 60 / 60 / 1000) + "年前";
		}
		
		return interval;
	}
}
