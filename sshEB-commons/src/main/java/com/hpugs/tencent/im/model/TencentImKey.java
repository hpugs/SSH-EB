package com.hpugs.tencent.im.model;

import com.hpugs.commons.util.CreateNumber;

/**
 * @Description 腾讯IM控制台常量
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2017年8月29日 下午2:05:15
 */
public class TencentImKey {
	
	//APP在云通信控制台上获取的Appid。
	public static final Integer SDK_APP_ID = 1400031194;
	
	//用户名，调用REST API时一般为APP管理员帐号。
	public static final String IDENTIFIER = "manyiaby_admin";
	
	//用户名对应的签名。对于使用独立帐号体系的APP，参见Linux平台下生成usersig和Windows平台下生成usersig。对于使用托管帐号体系的APP，参见下载UserSig。
	//注意：生成的sig有效期为180天，开发者需要在sig过期前，重新生成sig。
	//创建时间：2017-08-29 15:39
	public static final String USER_SIG = "eJxFkN1ugkAQRt*FW5tmf6QpTbxQsdUUNShibEw2W3ahU8NKh1VKTN*9SDDenjNfZr65OFGwfpRFAUpIKzgq58UhzkOL9W8BqIVMrcYGU9d1GSE3e9ZYwtE0ghHqUsYJuUtQ2lhIoQ3m0tQgP2shVQ6mmygha9R8Eo5nY*Kvws3zeTtUiOXKDWrs*bAr5pGPi0kZJ0jW9tB749nrEEZRpsJNEmdfs9MukqPqKf9efOA0W*5xb2RVsfdcBdOYBj9hGi3DweDKb0vVQbRlr3X6zbmcUq-fSQu5bmsS7nmMMtpxmSTHk7HC1oVuv-P3Dw68XyY_";
	
	//标识当前请求的整数随机数参数,32位无符号整数随机数。
	public static final String RANDOM = CreateNumber.getRandomNumber(32);
	
	//私钥
	public static final String privStr = "-----BEGIN PRIVATE KEY-----\n" +
			"MIGEAgEAMBAGByqGSM49AgEGBSuBBAAKBG0wawIBAQQge9ROaS42WyrB4a3WAXVD\n" +
			"Hg3DJkhMYxGs7lN3wM+Rsm2hRANCAATVBUVtG+AhKIEE5JfBjKgsr8ylfw3rQZTr\n" +
			"QVc4fr7O1X93KVQ8QVOEh7AbkLmUqLxbu7P8hPJG2+EKplpTAU+G\n" +
			"-----END PRIVATE KEY-----";
    
	//公钥
	public static final String pubStr = "-----BEGIN PUBLIC KEY-----\n" +
			"MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAE1QVFbRvgISiBBOSXwYyoLK/MpX8N60GU\n" +
			"60FXOH6+ztV/dylUPEFThIewG5C5lKi8W7uz/ITyRtvhCqZaUwFPhg==\n" +
			"-----END PUBLIC KEY-----";

}
