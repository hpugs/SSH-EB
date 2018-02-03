package com.hpugs.commons.util;

import java.util.Random;

/**
 * @Description 自动化生成密码、验证码
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2017年6月16日 上午11:08:24
 */
public class CreateNumber {

	/**
	 * @Description 生成密码，剔除不易识别的：l,o,z,I,O,Z
	 * @param count 新密码长度，默认为6位
	 * @return 根据密码长度，生成一个以字母开头，字母和数字组成的密码
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年6月16日 上午11:36:51
	 */
	public static String getNewPassword(Integer count){
		StringBuffer stringBufferPassword = new StringBuffer();
		char [] str1 = {'a','b','c','d','e','f','g','h','i','j','k','m','n','p','q','r','s','t','u','v','w','x','y','A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y'};
		char [] str2 = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','m','n','p','q','r','s','t','u','v','w','x','y','A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y'};
		count = count != null ? count : 6;
		Random random = new Random();
		stringBufferPassword.append(str1[random.nextInt(str1.length)]);
		for(int i = 1; i<count; i++){
			stringBufferPassword.append(str2[random.nextInt(str2.length)]);
		}
		return stringBufferPassword.toString();
	}
	
	/**
	 * @Description 生成短信验证码
	 * @param count 短息验证码长度，默认为6位
	 * @return 根据密码长度，生成由数字组成的密码
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年6月17日 下午1:18:44
	 */
	public static String getSmsCode(Integer count){
		StringBuffer stringBufferPassword = new StringBuffer();
		char [] str = {'0','1','2','3','4','5','6','7','8','9'};
		count = count != null ? count : 6;
		Random random = new Random();
		for(int i = 0; i<count; i++){
			stringBufferPassword.append(str[random.nextInt(str.length)]);
		}
		return stringBufferPassword.toString();
	}
	
	/**
	 * @Description 生成图片验证码，剔除不易识别的：l,o,z,I,O,Z
	 * @param count 图片验证码字符长度
	 * @return 
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年6月17日 下午1:36:16
	 */
	public static String getImageCode(Integer count){
		StringBuffer stringBufferPassword = new StringBuffer();
		char [] str = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','m','n','p','q','r','s','t','u','v','w','x','y','A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y'};
		count = count != null ? count : 4;
		Random random = new Random();
		for(int i = 0; i<count; i++){
			stringBufferPassword.append(str[random.nextInt(str.length)]);
		}
		return stringBufferPassword.toString();
	}
	
	/**
	 * @Description 生成随机数字,默认长度为4
	 * @param type 是否包含字符
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年8月15日 上午11:21:46
	 */
	public static String getRandomNumber(Integer count){
		StringBuffer sb = new StringBuffer();
		char [] str = {'0','1','2','3','4','5','6','7','8','9'};
		count = count != null ? count : 4;
		Random random = new Random();
		for(int i = 0; i<count; i++){
			sb.append(str[random.nextInt(str.length)] == 0 ? str[random.nextInt(str.length)] + 1 : str[random.nextInt(str.length)]);
		}
		return sb.toString();
	}
	
	/**
	 * @Description 生成随机英文字符默认长度为4
	 * @param count
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年8月18日 下午3:37:26
	 */
	public static String getRandomEN(Integer count){
		StringBuffer sb = new StringBuffer();
		char [] str = {'a','b','c','d','e','f','g','h','i','l','j','k','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','J','K','I','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		count = count != null ? count : 4;
		Random random = new Random();
		for(int i = 0; i<count; i++){
			sb.append(str[random.nextInt(str.length)]);
		}
		return sb.toString();
	}
	
	/**
	 * @Description 生成随机字符默认长度为4
	 * @param count
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年8月18日 下午3:38:02
	 */
	public static String getRandomNumAndEN(Integer count){
		StringBuffer sb = new StringBuffer();
		char [] str = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','l','j','k','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','J','K','I','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		count = count != null ? count : 4;
		Random random = new Random();
		for(int i = 0; i<count; i++){
			sb.append(str[random.nextInt(str.length)]);
		}
		return sb.toString();
	}
}
