package com.hpugs.commons.util;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 常量定义
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2017年12月25日 上午9:07:19
 */
public class ConstantUtil {

	/**
	 * 支付模块
	 * begin-----------------------------------------------------------------------------
	 */
	// 支付宝异步回调地址
	public static final String ALI_NOTIFY_URL = "http://www.manyiaby.com/pay_alipayResult.action";
	// 支付宝我要上专栏异步回调地址
	public static final String ALI_SPECIAL_PART_NOTIFY_URL = "http://www.manyiaby.com/pay_alipaySpecialPartResult.action";
	// 微信异步回调地址
	public static final String WX_NOTIFY_URL = "http://www.manyiaby.com/pay_wxPayResult.action";
	// 微信我要上专栏异步回调地址
	public static final String WX_SPECIAL_PART_NOTIFY_URL = "http://www.manyiaby.com/pay_wxPaySpecialPartResult.action";

	// 支付超时时间
	public static final String PAY_OUT_TIME = "30M";
	
	// 支付宝公共回传参数
	public static final String ALI_PAY_PASSBACK_PARAMS = "artbanku";

	// 支付宝正式环境
	public static final String NET_PATH = "https://openapi.alipay.com/gateway.do";// 支付网关
	public static final String APP_ID = "xxxxxxxxxxxxxxxx";// 满艺网H5
	public static final String CHARSET = "utf-8";
	public static final String FORMAT = "json";
	public static final String SIGN_TYPE = "RSA2";
	public static final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCpi7KGmLHpA4Md4jSROXkwDueChf0V8aHjlEo7lx0sWKy1CKT7lRXDwPja5Zulx5rglNfcf6zKqhL0Fabnk5NPmb3Jphgqwzgtioz1FJIm9O4esvhi/g43iBVuzwdnvU7DUsn2mDYJW/ia1W6F3KsDU9HJOkb/wo0Q+zVXLqxZNEPgX9/mXCFWWdMjy4o61eZaP481C7dDd1/TpZ+/3mWe0Z/fkuGztxvJ2UU44Q6k6o9N2qKiZlgoddK/QflKNp8DiLtwvp9Cel/YcWeeCmZzStHFlzzMsTu/wX46LQQZH30Y0KHH7OirwfRGA0MFitmA0JXuxRCVUoqpb+WDAEJ9AgMBAAECggEAZJQI3ivrLs30lF/Cy3wK1mWhFR8jWkczwGwR9XPZzJr2KZbtOInilXP+rOyU/y2ARUZHH/llqfxmrgHCN5KmSwxrOBqeL5AL3YhZwnHnmdCnCfMlwqPwDyVdgZSzUelNtjWAthbpx3eV7VQ78pFLqfepdfRqdgMV6EL+C7AWcfvDzOCahhFGvwkAOzVNLmhjycwsYl5l4bN/THCV0a6Todh3/RnPptIZH7NCtPbJfLuP+U1lXzzPj4au/4sE4TrzaxRl95en15AA3KZhMbxb7USDxFw6XMbtm83pDeICa+tmCOZ6prYkQkGUo6sUCMOVwBZ/wSRohtI3BywOkoCywQKBgQDva4QUBBgUDzqGSc/HWehNWuvZcR12Fr/LhNzSrvQwbLRIk78NVhMAk+q5Zk2t1DH6/R031q7ni5OnuvceY1f4IZvjftFc49Tc2DzM+PbeoHYDYJUD04zhWZa+L7Y+wq6LGyu3y3XD2UNB76s1fIVTe2dVxXJc1ZO9pZk6PCQvVQKBgQC1SW+4FfhgCEQxSVwyvc7io7HN3/1RmKT6QKQmHaWnBCBbnlyF4pnZAmkccow5ZweXU1NEcTp4Xgz3Ez1Ty5jWLHO3uDUxuo6KnyAqi86hZqenmtu//hJwsKgDCPSkWUl2tcZ/ukVRTk0v3BuAffLYjlGPVpXQyuh4RiBNBQs2iQKBgQCfNOOzW7WXQTktq0+bxNGQ/vDuuyVYLXCEqWkeQHDo8qA/0t+swpwIOlRPzRPCj+Rcfow7YWsVVd9uHgnOD0mkGtVKxj36gxY9dR2ZP8UJ7bIMNOFilf6Mq8Jt3dRWj6b5oavEoGtDPOpYokM7Yf5WsSzaAF44FV4VMlVeQDffsQKBgC+lBT+URpq2kXWCmkCbsevOqcQ5whHX1EQRCRMbZ5Xld7uceySU0Wo/P/DCCihr84rJ03CBcaSA01d8Lvn+4EQpmqAGQbS4Gw5Dk+iapcu1dwqNoINs+tuzEfT6PZGUBcK1M14OTnl34sFZT8ENWO2hOb46O1WMflxd0c9sK+5BAoGAY43H2OYGkHBRN/qfQ218pEuDNzAgALtriD7yV8SG33KrH34adCZaHvf3q8JbkpaJ6BhAoRAS1clMT0veM8Lw8bMlq9ybV1HcRf1DQ+iKXc0c00/aTTFhktv6XmGhnktpwwpOeW6+AZpEEX0gKpiPl5xWtsRFk1x8Le3z9z9X/64=";
	public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlTELZ0eeXGC+oDFtAmgLYvUmQ1fsbSjSS8gV+7l+qGnELqGkK8F6sLL6eLeLKZPee0QTMW6WMPpwU3ZVpmzM9eGO9Ah2FtClRAayekZVpgJ3qH0fslTRfYEJ6HU6dJf1nd+o/rnW9X414bWCtQZy1uUGU35yJgIXRilk0anFNULMOZHa8aEairl4fZN5gYk3OljTBddKbiRi2TfVNJtWIOFRMMF5NR+9iTe75upo4tUPCXmx+DSzsPrAhafcperZ9Oo2Jvt7S0LBRLx3d7NzM1VnhMzABTFgcl9V2SuymW7g86U+cl0+uukleMwDzkFZ72pFUPZ2gAsBdfGHtJepMwIDAQAB";
	// 支付宝沙箱环境
//	public static final String NET_PATH = "https://openapi.alipaydev.com/gateway.do";//支付网关
//	public static final String APP_ID = "xxxxxxxxxxxxxxxx";
//	public static final String CHARSET = "utf-8";
//	public static final String FORMAT = "json";
//	public static final String SIGN_TYPE = "RSA2";
//	public static final String APP_PRIVATE_KEY = 
//			"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDpM8FCJ79KLH8CBz9qJlcoOQ94d+7/KEWnly2hlu0/t+V2Hs//vaUd3G80B3cMaLkkYFX0hx2i/z9o8HBFuFGGv2xeInzpLijVvyPeZ4CxAO+hj45Hdd04WQfiJ6dt8TiEnbaoDru3tffhD0HZgatgFYS6As7a9fVx3w0UbXzv0TvwVO6eY2dXAC55hbbxxVcmzIyvbVpNRO8se5CoIXE/06tNlh4/D61mrrmlgM9fKnO30jrcxEsdwJmLIO9pYC0fnYCa4KqG1n8v18Q7xL2gFiWe3XOX3W07542tazDkCcLaLNzSlGTT1ms0dyy28ngY8QS6Je4ex7qnJp6dpggTAgMBAAECggEAW41fBNrX5YXcgu5XfhP5SahFI5VqNbAY0HewiWzYTZijtCDapdtNNJDy82ku2qdOcDKF7E8TWod83HTwjt6S2yTRpY9CnMTrcSbuLQVTZ6onabUTNYjXRaTmTGom+Cmod5/3Mrk+BxygaTH1NzAKGNm51tD9ZscDCiZ4Wxm03+z0DUjODxXmbPw1mE6SbYwDWSGBDoByMXsPq0z6SP1lGT8NEYQvW4dIS3MIAJJvXVKgqBoYxjynoG8FbMOrQNsc+DCmubWLwB4vNytva8vylrU9IIP4PF0zo228f/LSEqFsglPkEpQ7VtEUByoUKxftyNdiUBSmytU0GRyU71NuwQKBgQD4B/awSwPahKDOqWZQ7d+6dNPu1zcTSC1ZpncoOHhFKrUPVrGSYTKT7faL7eJ0/cAlD5YK7ftMiONE+43Pa9qc2HZ/jPM/2nW157DiQXLKm/2pvIsZJAdRRtZjvutsHgttQ4WZ6JVL3hiZ8J5h+roxE9kuuLZjjZnY9Yqj34pu8wKBgQDwsdMNKhyQAcHm6DWosrET7tNztPRkeOLWnzGiWsoJJGQgM9AwsGBhNnXHVk33/6r7R68hGBzSypFGZPVhMk1WsfAkNCRilmO32J4OaV3qRgPjrGwdsf+uAUqsucgS9wLzHf9S2Vt2f4NX+Uqdc6J2rAzUcuTzbw+qIXBg8tGKYQKBgAkYKahvJ7YEdOz+8xx2jGSG0hqI06EmXVtTKj1UTLykgz0LvbkTTUp1SDv7PflyMHBrcOH3teYpgsPNwXZgiwSDP10P6v9lYDmKREcXhUD+lT97BVKnqokFYukHYBR3yY15E1YAXDEK9LX4R6ZqDheslaML7Mwbrg9xeWLm2GmXAoGBAKQ64WOxIS4NVO2l8b76tiKAqZzgkoTfiv+gPbCmvAYC46KpV9tw7UJHc5OgyrB1rYepANDW8AvQFdHvXxKDzQtjfBRn04/FR6MQ1b/Fm0QXo928L0UPyAJFdVtnpCF0k3mpNSalkmfHV6o2Ofg3B1pryJFJF/kWBZ0zFcfAxsLhAoGAb3EepuqDQ+N6FyU54KH7LBUhbpQ6yN2pkLIEBo+VdXO62J0US/S318IrUvbAsQcChAlUnEG7NOGWwFZehWbiz1SztwOSUThb9tS+vQMTekJjPHJgXeQBZmrXh/Y+KsaMk10R4ZOr8rh1xbI8WGkJNbB3VX8Ezk2cikShVF6g33c=";
//	public static final String ALIPAY_PUBLIC_KEY = 
//			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtNo4rOQ9X7+P+pcrUo3OOG8ksYpSSjXN+AA5AeiSJs0ZY/MVwpf6Ibzj3kfyqDAsA6h4mEAwO+icsjGS482hARCn6x4Nvpr0Gs47hgK3hLr5ZzRKDSVtWNN0Mcu2uEMoxZKR7CU6DTiZfjvgzgZiy6TDu2ZjcBsk3TzAaN0pGq+7gfra1WpznaGy2iQOUSvuNsDWoMZXhVlKMdRuDQeV5nn/iahwm1YLzGEAqxtkIiW1OxOR+7oSeofzrAeMLM1pvxbBWROE4Nf6TAcpDJfsTrwTLDR5Wziy+lB2Gw1Y3yBbd/dq/lylAxWcxnoo7eXfxAXNCdU4bivcMqYm2v8L8QIDAQAB";

	// 微信支付公众号配置--服务号--》基本配置
	public static final String MP_API_KEY = "xxxxxxxxxxxxxxxx";
	public static final String MP_APPID = "xxxxxxxxxxxxxxxx";
	public static final String MP_APPSECRET = "xxxxxxxxxxxxxxxx";
	public static final String MP_MCH_ID = "xxxxxxxxxxxxxxxx";
	// 微信开发者--》(移动端)基本配置
	public static final String DV_API_KEY = "xxxxxxxxxxxxxxxx";
	public static final String DV_APPID = "xxxxxxxxxxxxxxxx";
	public static final String DV_APPSECRET = "xxxxxxxxxxxxxxxx";
	public static final String DV_MCH_ID = "xxxxxxxxxxxxxxxx";
	// 微信开发者--》(PC)基本配置
	public static final String DV_PC_API_KEY = "xxxxxxxxxxxxxxxx";
	public static final String DV_PC_APPID = "xxxxxxxxxxxxxxxx";
	public static final String DV_PC_APPSECRET = "xxxxxxxxxxxxxxxx";
	public static final String DV_PC_MCH_ID = "xxxxxxxxxxxxxxxx";
	// 微信统一下单地址
	public static final String PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// 请求方式
	public static final String REQUEST_TYPE = "POST";
	// 请求字符编码
	public static final String STR_CHARSET = "UTF-8";
	/**
	 * 支付模块
	 * end-----------------------------------------------------------------------------
	 */

	/**
	 * 阿里云短信
	 */
	// 阿里云超时时间
	public static final String defaultConnectTimeout = "10000";
	public static final String defaultReadTimeout = "10000";
	// 阿里云初始化ascClient
	public static final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
	public static final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
	// 阿里云的短信AK
	public static final String accessKeyId_SMS = "xxxxxxxxxxxxxxxx";// 你的accessKeyId,参考本文档步骤2
	public static final String accessKeySecret_SMS = "xxxxxxxxxxxxxxxx";// 你的accessKeySecret，参考本文档步骤2
	// 短信签名
	public static final String SmsFreeSignName = "hpugs";
	//短信常量
	public static final String MESSAGE_TEMPLATE = "SMS_95675001";// 短信通知模板ID:尊敬的${userName}，您的新密码为：${password}，请妥善保管。如非本人操作，请即时变更
	public static final String MESSAGE_CODE = "SMS_95670001";// 短信验证码模板ID:您的验证码是：${code}，该验证码5分钟内有效，请勿随意告知他人。
	public static final String RESULT_SEND_MESSAGE_FAIL = "短信发送失败";// 短信发送失败提示
	public static final String RESULT_SEND_MESSAGE_SUCCESS = "短信发送成功";// 短信发送成功提示
	public static final String CAN_SEND_MESSAGE = "短信可以发送";// 是否可以发送短信提示
	public static final String RESULT_SEND_MESSAGE_TOPLIMIT = "该手机今日短信通知已达到上限，如有需要请联系客服。谢谢";// 短信通知上限
	public static final String RESULT_SEND_MESSAGE_STATE = "OK";// 短信发送状态
	
	/**
	 * @endpoint 下为取值
	 * http://oss-cn-hangzhou.aliyuncs.com	以HTTP协议，公网访问杭州区域的Bucket
	 * https://oss-cn-beijing.aliyuncs.com	以HTTPS协议，公网范围北京区域的Bucket
	 * http://my-domain.com	以HTTP协议，通过用户自定义域名（CNAME）访问特定Bucket
	 */
	public static final String endpoint_OSS = "https://oss-cn-beijing.aliyuncs.com";
	// 阿里云的OSS AK
	public static final String accessKeyId_OSS = "xxxxxxxxxxxxxxxx";// 你的accessKeyId,参考本文档步骤2
	public static final String accessKeySecret_OSS = "xxxxxxxxxxxxxxxx";// 你的accessKeySecret，参考本文档步骤2
	// 阿里oss的bucketName
	public static final String ALI_OSS_BUCKET_NAME = "hpugs";
	// 阿里oss临时的bucketName
	public static final String ALI_OSS_BUCKET_NAME_TEMPORARY = "hpugs-temporary";
	//自定义域名 TODO 待自定义
	public static final String ALI_OSS_VISIT_IMG_PREFIX = "http://" + ALI_OSS_BUCKET_NAME + ".oss-cn-beijing.aliyuncs.com/";
	public static final String ALI_OSS_VISIT_IMG_PREFIX_TEMPORARY = "http://" + ALI_OSS_BUCKET_NAME_TEMPORARY + ".oss-cn-beijing.aliyuncs.com/";
	//阿里文件夹保存格式
	public static final SimpleDateFormat ALI_OSS_FILE_PATH = new SimpleDateFormat("/yyyy/MM/dd/");
	
	/**
	 * 腾讯企业云邮
	 */
	public static final String EMAIL_NAME  = "manyi@manyiaby.com";//发送邮件的名称
	public static final String EMAIL_NICK_NAME  = "hpugs";//发送邮件的名称
	public static final String EMAIL_PASSWORD = "xxxxxxxxxxxxxxxx";//发送邮件的密码
	public static final String GET_EMAIL_TRANSPORT_PROTOCOL = "imap";//接收邮件协议
	public static final String GET_EMAIL_SMTP_HOST = "imap.exmail.qq.com";//服务器地址（接收邮件服务器）
	public static final String SEND_EMAIL_TRANSPORT_PROTOCOL = "smtp";//发送邮件协议
	public static final String SEND_EMAIL_SMTP_HOST = "smtp.exmail.qq.com";//服务器地址（发送邮件服务器）
	public static final String GET_EMAIL_SMTP_PORT = "993";//接收邮件使用993端口
	public static final String SEND_EMAIL_SMTP_PORT = "465";//发送邮件使用465端口
	public static final String MAIL_SMTP_AUTH = "true";//设置使用验证
	public static final String MAIL_SMTP_STARTTLS_ENABLE = "true";//使用 STARTTLS安全连接
	
	/**
	 * 默认接收邮件信息的邮箱地址
	 */
	public static final String DEFAULT_EMAIL_NAME  = "1453296946@qq.com";
	public static final String DEFAULT_EMAIL_NICK_NAME  = "hpugs";
	
	/**
	 * 短信验证码模块
	 */
	public static final SimpleDateFormat SMS_CODE_DATE_SDF = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");// 用于短信验证码过期校验
	public static final Integer MAX_ERROR_SMS_CODE_DATE = 5;// 短信验证码最大的有效时间（分钟计算）
	public static final String PC_LOGIN_CODE_TYPE = "pcLoginCode";// PC登录短信验证码标示
	public static final String PC_REGISTER_CODE_TYPE = "pcRegisterCode";// PC注册短信验证码标示
	public static final String PC_FORGET_CODE_TYPE = "pcForgetCode";// PC找回密码短信验证码标示
	public static final String PC_UPDATE_CODE_TYPE = "pcUpdateCode";// PC修改密码短信验证码标示
	public static final String PC_THIRD_BINDING_CODE_TYPE = "pcThirdBindingCode";// PC第三方账号绑定短信验证码标示
	public static final int SMS_CODE_LENGTH = 6;// 生成短信验证码的位数
	public static final int IMG_CODE_LENGTH = 4;// 生成图片验证码的位数
	public static final int PASSWORD_LENGTH = 6;// 生成密码的位数
	
	/**
	 * 客户模块
	 */
	public static final String STAFF_ADMIN_ACCOUNT = "hpugs";//系统管理员账号
	public static final String STAFF_ADMIN_PWD = "6F1871450FAA1C4151DC0971C566CBBADA10A9E46A6034F1";//系统管理员密码（已加密）
	public static final String INSIDE_REGISTER_PASSWD = "B2FC75F5F4EDB1B68EC970841420DF5A9074476474973AE4D6964A48EF4B51A1";// 内部创建账号口令
	public static final Integer MAX_ERROR_LOGIN_NUMBER = 10;// 一天内最大支持连续登录错误次数
	public static final String ACCOUNT_HAS_LOCK_MSG = "账号已被锁定，请24小时后再次登录";

	/**
	 * 订单模块
	 */
	public static final int ORDER_NO_PAY = 1;// 订单待付款
	public static final int ORDER_NO_SEND = 2;// 订单待发货
	public static final int ORDER_NO_GET = 3;// 订单待签收
	public static final int ORDER_NO_EVALUATE = 4;// 订单待评价
	public static final int ORDER_IS_SUCCESS = 5;// 订单已完成
	public static final int ORDER_IS_DELETE = 6;// 订单已删除
	public static final int ORDER_OVER_TIME = 7;// 订单支付超时
	public static final int ORDER_IS_CANCEL = 8;// 订单已作废
	public static final int ORDER_IS_REFUND = 9;// 订单已退款

	/**
	 * 公共返回参数Key
	 */
	public static final String RESULT_ACTION_KEY = "action";// 返回的接口名称Key
	public static final String RESULT_STATUS_KEY = "status";// 返回的状态码Key
	public static final String RESULT_MSG_KEY = "msg";// 返回的操作信息Key
	public static final String RESULT_DATA_KEY = "data";// 返回的数据Key
	public static final String RESULT_EASY_UI_TOTAL_KEY = "total";// easy ui 数据总数
	public static final String RESULT_EASY_UI_ROWS_KEY = "rows";// easy ui 当前页数据
	public static final String RESULT_REQUEST_SOURCE_KEY = "source";// 请求来源地址

	/**
	 * 公共返回参数部分
	 */
	public static final String RESULT_STATUS_FAIL_STR = "0";// 执行失败的状态码
	public static final String RESULT_STATUS_SUCCESS_STR = "1";// 执行成功的状态码
	public static final String RESULT_STATUS_NOTLOGIN_STR = "2";// 未登录的状态码
	public static final String RESULT_STATUS_NOTSESSION_STR = "3";// session过期的状态码
	public static final String RESULT_STATUS_MUST_UPDATE_STR = "4";// 强制更新
	public static final String RESULT_STATUS_AUTO_UPDATE_STR = "5";// 提示更新
	public static final String RESULT_STATUS_NO_MOBILE_STR = "6";// 手机号未注册
	public static final String RESULT_STATUS_OK_MOBILE_STR = "7";// 手机号已被注册
	public static final String RESULT_MSG_LOGIN_SUCCESS = "登录成功";// 登录成功的提示信息
	public static final String RESULT_MSG_SUCCESS = "操作成功";// 执行成功的提示信息
	public static final String RESULT_MSG_NOTLOGIN = "当前未登录，请先登录";// 未登录的提示信息
	public static final String RESULT_MSG_FAIL = "操作失败";// 执行失败的提示信息
	public static final String RESULT_MSG_ERROR = "缺少接口访问参数";// app接口请求必填参数不能为空
	public static final String RESULT_MSG_NOT_ACTION = "接口方法名传入错误";// 接口方法访问参数错误
	public static final String RESULT_MSG_HEAD_ERROR = "缺少接口请求头";// 请求头不能为空
	public static final String LOGIN_REQUEST_SOURCE_PATH = "loginRequestSourcePath";// 登录请求来源地址

	/**
	 * Android更新信息 0:不更新 1:提示更新 2:强制更新
	 */
	public static final StringBuffer ANDROID_VERSION_CODE = new StringBuffer().append("44:2;");
	/**
	 * Ios更新信息 0:不更新 1:提示更新 2:强制更新
	 */
	public static final StringBuffer IOS_VERSION_CODE = new StringBuffer().append("20171117:2;");

	/**
	 * 文件上传模块
	 */
	public static final String UPLOAD_LOCAL_PATH = "\\\\192.168.12.252\\imgServer\\artwork\\images\\";// 文件上传本地路径
	public static final String UPLOAD_NETWORK_PATH = "http://192.168.12.252:8081/img/";// 文件上传本地路径
	public static final String FILE_UPLOAD_FAIL = "文件上传失败";// 文件上传本地路径
	public static final String THUM_PC_NAME = "_pc_thum";// pc缩略图标记
	public static final int IMAGE_THUM_WIDTH = 200;// pc宽度压缩尺寸
	public static final int IMAGE_THUM_HEIGHT = 200;// pc高度压缩尺寸
	
	/**
	 * 照片保存位置
	 */
	public static final Map<String, String> IMAGE_PATH_MAP = new HashMap<String, String>();
	// 照片地址Key
	public static final String IMAGE_PATH_KEY_USER_AVATAR_IMAGE = "userAvatar";
	public static final String IMAGE_PATH_KEY_ARTWORK_IMAGE = "artworkImagePath";
	public static final String IMAGE_PATH_KEY_SYS_TYPE_IMAGE = "sysType";
	public static final String IMAGE_PATH_KEY_ID_IMAGE = "id";
	static {
		// 照片保存位置
		IMAGE_PATH_MAP.put(IMAGE_PATH_KEY_USER_AVATAR_IMAGE, "user/avatar/");// 头像地址
		IMAGE_PATH_MAP.put(IMAGE_PATH_KEY_ARTWORK_IMAGE, "artwork/");// 艺术品照片地址
		IMAGE_PATH_MAP.put(IMAGE_PATH_KEY_SYS_TYPE_IMAGE, "sys/type/");// 分类
		IMAGE_PATH_MAP.put(IMAGE_PATH_KEY_ID_IMAGE, "ID/");// 身份证
	}
	
	//用户头像默认值
	public static final String USER_AVATAR_DEFAULT = "public/avatar/userhead1.png";
	//艺术品默认值
	public static final String ARTWORK_DEFAULT = "public/artwork/atworkimage1.png";
	
	//艺术品列表使用的图片后缀
	public static final String ARTWORK_LIST_IMAGE_SUFFIX = "/default";
	//艺术品详情使用的图片后缀
	public static final String ARTWORK_INFO_IMAGE_SUFFIX = "/default";
	//艺术品详情使用的图片后缀
	public static final String ARTWORK_INFO_IMAGE_ORIGINAL_SUFFIX = "/original";
	//用户头像使用的图片后缀
	public static final String USER_HEAD_IMAGE_SUFFIX = "/default";
	//用户身份证使用的图片后缀
	public static final String USER_ID_IMAGE_SUFFIX = "/default";

}
