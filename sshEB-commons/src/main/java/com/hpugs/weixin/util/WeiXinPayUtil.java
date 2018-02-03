package com.hpugs.weixin.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.hpugs.commons.util.ConstantUtil;
import com.hpugs.commons.util.CreateNumber;

/**
 * @Description 微信支付集成
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2017年9月25日 下午5:20:02
 */
public class WeiXinPayUtil {
	
	private static final Logger logger = Logger.getLogger(WeiXinPayUtil.class);
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
    /**
     * 微信支付模式一开发前，商户必须在公众平台后台设置支付回调URL。URL实现的功能：接收用户扫码后微信支付系统回调的productid和openid；
     * @Description 生成二维码,供用户扫码支付时，回调支付地址，后调用统一下单接口完成支付
     * @return
     *
     * @author 高尚
     * @version 1.0
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     * @date 创建时间：2017年9月25日 下午8:18:25
     */
    public static String getPCPayRequestUrlPath(final String orderCode) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("appid", ConstantUtil.MP_APPID);//微信分配的公众账号ID（企业号corpid即为此appId） 
		map.put("mch_id", ConstantUtil.MP_MCH_ID);//微信支付分配的商户号
		map.put("product_id", orderCode);//商户定义的商品id 或者订单号
		map.put("time_stamp", new Date().getTime() / 1000);//系统当前时间，定义规则详见时间戳
		map.put("nonce_str", CreateNumber.getRandomNumAndEN(new Random().nextInt(5) + 25));//随机字符串，不长于32位。推荐随机数生成算法
		//将参数进行签名
		map.put("sign", createSign(ConstantUtil.STR_CHARSET, map, ConstantUtil.MP_API_KEY));//签名，详见签名生成算法
		//生成二维码扫码地址
    	String WX_PAY_QR_PATH = "weixin://wxpay/bizpayurl?sign=" + map.get("sign") + "&appid=" + map.get("appid") + "&mch_id=" + map.get("mch_id") + "&product_id=" + map.get("product_id") + "&time_stamp=" + map.get("time_stamp") + "&nonce_str=" + map.get("nonce_str") + "";
    	return WX_PAY_QR_PATH;
    }
    
    /**
     * 用户扫描商户展示在各种场景的二维码进行支付
	 * @Description PC支付调用微信统一下单接口
	 * @param orderCode 订单编号
	 * @param orderCountMonery 订单总金额
	 * @param orderArtworkNames 订单商品名称
	 * @param requestIp 请求支付的用户IP
	 * @param userOpenId 用户的openId
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @author 高尚
	 * @version 1.0
     * @throws IOException 
	 * @date 创建时间：2017年8月15日 下午2:47:12
	 */
	public static Map<String, Object> goPcPay(final String orderCode, final double orderCountMonery, final String orderArtworkNames, final String requestIp, final String notifyUrl) throws NoSuchAlgorithmException, IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appid", ConstantUtil.MP_APPID);//微信分配的公众账号ID（企业号corpid即为此appId） 
		map.put("mch_id", ConstantUtil.MP_MCH_ID);//微信支付分配的商户号
		//选填
//		map.put("device_info", "");//终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
		map.put("nonce_str", CreateNumber.getRandomNumAndEN(new Random().nextInt(5) + 25));//随机字符串，不长于32位。推荐随机数生成算法
		//选填
//		map.put("sign_type", "MD5");//签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
		map.put("body", orderArtworkNames);//商品简单描述，该字段须严格按照规范传递，具体请见参数规定
		//选填
//		map.put("detail", "");//单品优惠字段(暂未上线)
		//选填
		map.put("attach", orderCode);//附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
		map.put("out_trade_no", orderCode);//商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
		//选填
//		map.put("fee_type", "");//符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
		map.put("total_fee", Math.round(orderCountMonery * 100) + "");//订单总金额，单位为分，详见支付金额
		map.put("spbill_create_ip", requestIp);//必须传正确的用户端IP,详见获取用户ip指引
		//选填
//		map.put("time_start", "");//订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
		//选填
//		map.put("time_expire", "");//订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则注意：最短失效时间间隔必须大于5分钟
		//选填
//		map.put("goods_tag", "");//商品标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
		map.put("notify_url", notifyUrl);//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
		map.put("trade_type", "NATIVE");//trade_type=NATIVE，此参数必传。JSAPI--扫码支付
		//选填
		map.put("product_id", orderCode);//trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
		//选填
//		map.put("openid", "");//trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
		//选填
//		map.put("limit_pay", "");//no_credit--指定不能使用信用卡支付
		map.put("scene_info", "{\"store_info\":{\"id\":\"manyiaby\",\"name\": \"满艺网\",\"area_code\": \"110108\",\"address\": \"北京市海淀区闵庄路永泰自在香山\"}}");//该字段用于上报支付的场景信息,针对H5支付有以下三种场景,请根据对应场景上报,H5支付不建议在APP端使用，针对场景1，2请接入APP支付，不然可能会出现兼容性问题
		
		//将参数进行签名
		map.put("sign", createSign(ConstantUtil.STR_CHARSET, map, ConstantUtil.MP_API_KEY));//签名，详见签名生成算法
		
		//正式环境
		return httpsRequest(ConstantUtil.PAY_URL, ConstantUtil.REQUEST_TYPE, getRequestXml(map));
	}
	
	/**
	 * 商户已有H5商城网站，用户通过消息或扫描二维码在微信内打开网页时，可以调用微信支付完成下单购买的流程
	 * @Description 公众号支付调用微信统一下单接口
	 * @param orderCode 订单编号
	 * @param orderCountMonery 订单总金额
	 * @param orderArtworkNames 订单商品名称
	 * @param requestIp 请求支付的用户IP
	 * @param userOpenId trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识。
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @author 高尚
	 * @version 1.0
	 * @throws IOException 
	 * @throws ParseException 
	 * @date 创建时间：2017年8月15日 下午2:47:12
	 */
	public static Map<String, Object> goMPPay(final String orderCode, final double orderCountMonery, final String orderArtworkNames, final String requestIp, final String userOpenId, final String notifyUrl) throws NoSuchAlgorithmException, IOException, ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appid", ConstantUtil.MP_APPID);//微信分配的公众账号ID（企业号corpid即为此appId） 
		map.put("mch_id", ConstantUtil.MP_MCH_ID);//微信支付分配的商户号
		//选填
//		map.put("device_info", "");//终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
		map.put("nonce_str", CreateNumber.getRandomNumAndEN(new Random().nextInt(5) + 25));//随机字符串，不长于32位。推荐随机数生成算法
		//选填
		map.put("sign_type", "MD5");//签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
		map.put("body", orderArtworkNames);//商品简单描述，该字段须严格按照规范传递，具体请见参数规定
		//选填
//		map.put("detail", "");//单品优惠字段(暂未上线)
		//选填
//		map.put("attach", orderCode);//附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
		map.put("out_trade_no", orderCode);//商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
		//选填
//		map.put("fee_type", "");//符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
		map.put("total_fee", Math.round(orderCountMonery * 100)+"");//订单总金额，单位为分，详见支付金额
		map.put("spbill_create_ip", requestIp);//必须传正确的用户端IP,详见获取用户ip指引
		//选填
//		map.put("time_start", "");//订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
		//选填
//		map.put("time_expire", "");//订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则注意：最短失效时间间隔必须大于5分钟
		//选填
//		map.put("goods_tag", "");//商品标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
		map.put("notify_url", notifyUrl);//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
		map.put("trade_type", "JSAPI");//trade_type=JSAPI，此参数必传。JSAPI--公众号支付
		//选填
//		map.put("product_id", "NATIVE");//trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
		//选填
		map.put("openid", userOpenId);//trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
		//选填
//		map.put("limit_pay", "");//no_credit--指定不能使用信用卡支付
//		map.put("scene_info", "{\"store_info\":{\"id\":\"manyiaby\",\"name\": \"满艺网\",\"area_code\": \"110108\",\"address\": \"北京市海淀区闵庄路永泰自在香山\"}}");//该字段用于上报支付的场景信息,针对H5支付有以下三种场景,请根据对应场景上报,H5支付不建议在APP端使用，针对场景1，2请接入APP支付，不然可能会出现兼容性问题
		
		//将参数进行签名
		map.put("sign", createSign(ConstantUtil.STR_CHARSET, map, ConstantUtil.MP_API_KEY));//签名，详见签名生成算法
		
		//获取统一下单码
		map = httpsRequest(ConstantUtil.PAY_URL, ConstantUtil.REQUEST_TYPE, getRequestXml(map));

		return getJsPay(map);
	}
	
	/**
	 * @Description 在微信浏览器里面打开H5网页中执行JS调起支付。接口输入输出数据格式为JSON。
					注意：WeixinJSBridge内置对象在其他浏览器中无效。
	 *
	 * 微信内部浏览器支付，二次签名
	 *
	 * @author 高尚
	 * @version 1.0
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws ParseException 
	 * @date 创建时间：2017年11月21日 下午5:46:13
	 */
	private static Map<String, Object> getJsPay(Map<String, Object> param) throws NoSuchAlgorithmException, UnsupportedEncodingException, ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appId", ConstantUtil.MP_APPID);//公众号名称，由商户传入
		map.put("timeStamp", (new Date().getTime() - sdf.parse("1970-01-01 00:00:00").getTime())/1000);//时间戳，自1970年以来的秒数
		map.put("nonceStr", CreateNumber.getRandomNumAndEN(new Random().nextInt(5) + 25));//随机串 
		map.put("package", "prepay_id="+param.get("prepay_id"));//随机串 
		map.put("signType", "MD5");//微信签名方式
		map.put("paySign", createSign(ConstantUtil.STR_CHARSET, map, ConstantUtil.MP_API_KEY));//微信签名 
		map.put("packages", "prepay_id="+param.get("prepay_id"));//随机串 
		return map;
	}
    
    /**
     * H5支付是指商户在微信客户端外的移动端网页展示商品或服务，用户在前述页面确认使用微信支付时，商户发起本服务呼起微信客户端进行支付。
     * 主要用于触屏版的手机浏览器请求微信支付的场景。可以方便的从外部浏览器唤起微信支付。
	 * @Description H5支付调用微信统一下单接口
	 * @param orderCode 订单编号
	 * @param orderCountMonery 订单总金额
	 * @param orderArtworkNames 订单商品名称
	 * @param requestIp 请求支付的用户IP
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @author 高尚
	 * @version 1.0
     * @throws IOException 
	 * @date 创建时间：2017年8月15日 下午2:47:12
	 */
	public static Map<String, Object> goH5Pay(final String orderCode, final double orderCountMonery, final String orderArtworkNames, final String requestIp, final String notifyUrl) throws NoSuchAlgorithmException, IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appid", ConstantUtil.DV_APPID);//微信分配的公众账号ID（企业号corpid即为此appId） 
		map.put("mch_id", ConstantUtil.DV_MCH_ID);//微信支付分配的商户号
		//选填
//		map.put("device_info", "");//终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
		map.put("nonce_str", CreateNumber.getRandomNumAndEN(new Random().nextInt(5) + 25));//随机字符串，不长于32位。推荐随机数生成算法
		//选填
//		map.put("sign_type", "MD5");//签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
		map.put("body", orderArtworkNames);//商品简单描述，该字段须严格按照规范传递，具体请见参数规定
		//选填
//		map.put("detail", "");//单品优惠字段(暂未上线)
		//选填
		map.put("attach", orderCode);//附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
		map.put("out_trade_no", orderCode);//商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
		//选填
//		map.put("fee_type", "");//符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
		map.put("total_fee", Math.round(orderCountMonery * 100) + "");//订单总金额，单位为分，详见支付金额
		map.put("spbill_create_ip", requestIp);//必须传正确的用户端IP,详见获取用户ip指引
		//选填
//		map.put("time_start", "");//订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
		//选填
//		map.put("time_expire", "");//订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则注意：最短失效时间间隔必须大于5分钟
		//选填
//		map.put("goods_tag", "");//商品标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
		map.put("notify_url", notifyUrl);//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
		map.put("trade_type", "MWEB");//trade_type=NATIVE，此参数必传。H5支付的交易类型为MWEB
		//选填
//		map.put("product_id", "NATIVE");//trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
		//选填
//		map.put("openid", "");//trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
		//选填
//		map.put("limit_pay", "");//no_credit--指定不能使用信用卡支付
		map.put("scene_info", "{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"http://m.manyiaby.com\",\"wap_name\": \"满艺网\"}}");//该字段用于上报支付的场景信息,针对H5支付有以下三种场景,请根据对应场景上报,H5支付不建议在APP端使用，针对场景1，2请接入APP支付，不然可能会出现兼容性问题
		
		//将参数进行签名
		map.put("sign", createSign(ConstantUtil.STR_CHARSET, map, ConstantUtil.DV_API_KEY));//签名，详见签名生成算法
		
		return httpsRequest(ConstantUtil.PAY_URL, ConstantUtil.REQUEST_TYPE, getRequestXml(map));
	}
	
	/**
	 * 适用于商户在移动端APP中集成微信支付功能
	 * @Description APP支付调用微信统一下单接口
	 * @param orderCode 订单编号
	 * @param orderCountMonery 订单总金额
	 * @param orderArtworkNames 订单商品名称
	 * @param requestIp 请求支付的用户IP
	 * @param userOpenId trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识。
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @author 高尚
	 * @version 1.0
	 * @throws IOException 
	 * @throws ParseException 
	 * @date 创建时间：2017年8月15日 下午2:47:12
	 */
	public static Map<String, Object> goAppPay(final String orderCode, final double orderCountMonery, final String orderArtworkNames, final String requestIp, final String notifyUrl) throws NoSuchAlgorithmException, IOException, ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appid", ConstantUtil.DV_APPID);//微信分配的公众账号ID（企业号corpid即为此appId） 
		map.put("mch_id", ConstantUtil.DV_MCH_ID);//微信支付分配的商户号
		//选填
//		map.put("device_info", "");//终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
		map.put("nonce_str", CreateNumber.getRandomNumAndEN(new Random().nextInt(5) + 25));//随机字符串，不长于32位。推荐随机数生成算法
		//选填
//		map.put("sign_type", "MD5");//签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
		map.put("body", orderArtworkNames);//商品简单描述，该字段须严格按照规范传递，具体请见参数规定
		//选填
//		map.put("detail", "");//单品优惠字段(暂未上线)
		//选填
		map.put("attach", orderCode);//附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
		map.put("out_trade_no", orderCode);//商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
		//选填
//		map.put("fee_type", "");//符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
		map.put("total_fee", Math.round(orderCountMonery * 100) + "");//订单总金额，单位为分，详见支付金额
		map.put("spbill_create_ip", requestIp);//必须传正确的用户端IP,详见获取用户ip指引
		//选填
//		map.put("time_start", "");//订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
		//选填
//		map.put("time_expire", "");//订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则注意：最短失效时间间隔必须大于5分钟
		//选填
//		map.put("goods_tag", "");//商品标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
		map.put("notify_url", notifyUrl);//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
		map.put("trade_type", "APP");//trade_type=APP，此参数必传。APP--app支付
		//选填
//		map.put("product_id", "NATIVE");//trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
		//选填
//		map.put("openid", "");//trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
		//选填
//		map.put("limit_pay", "");//no_credit--指定不能使用信用卡支付
		map.put("scene_info", "{\"store_id\":\"manyiaby\",\"store_name\":\"满艺网\"}");//该字段用于上报支付的场景信息,针对H5支付有以下三种场景,请根据对应场景上报,H5支付不建议在APP端使用，针对场景1，2请接入APP支付，不然可能会出现兼容性问题
		//将参数进行签名
		map.put("sign", createSign(ConstantUtil.STR_CHARSET, map, ConstantUtil.DV_API_KEY));//签名，详见签名生成算法
		
		//微信返回的支付交易会话ID
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("appid", ConstantUtil.DV_APPID);
		resultMap.put("partnerid", ConstantUtil.DV_MCH_ID);
		resultMap.put("prepayid", httpsRequest(ConstantUtil.PAY_URL, ConstantUtil.REQUEST_TYPE, getRequestXml(map)).get("prepay_id"));
		resultMap.put("package", "Sign=WXPay");
		resultMap.put("noncestr", CreateNumber.getRandomNumAndEN(new Random().nextInt(5) + 25));//随机字符串，不长于32位。推荐随机数生成算法
		resultMap.put("timestamp", (new Date().getTime() - sdf.parse("1970-01-01 00:00:00").getTime()) / 1000);
		resultMap.put("sign", createSign(ConstantUtil.STR_CHARSET, resultMap, ConstantUtil.DV_API_KEY));
		
		return resultMap;
	}
    
    //请求xml组装  
    public static String getRequestXml(Map<String, Object> map){  
    	SortedMap<String, Object> parameters = new TreeMap<String, Object>(map);
        StringBuffer sb = new StringBuffer();  
        sb.append("<xml>");  
        Set es = parameters.entrySet();
        Iterator it = es.iterator();  
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next(); 
            String key = (String)entry.getKey();  
            String value = (String)entry.getValue();  
            if ("attach".equalsIgnoreCase(key)||"body".equalsIgnoreCase(key)||"sign".equalsIgnoreCase(key)||"scene_info".equalsIgnoreCase(key)) {
                sb.append("<"+key+">"+"<![CDATA["+value+"]]></"+key+">");  
            }else {  
                sb.append("<"+key+">"+value+"</"+key+">");  
            }
        }  
        sb.append("</xml>");
        return sb.toString();  
	} 
    
    /**
     * @Description 微信返回Xml转Map
     * @param responseXml
     * @return
     * @throws JDOMException
     * @throws IOException
     *
     * @author 高尚
     * @version 1.0
     * @date 创建时间：2017年9月26日 下午3:06:38
     */
    public static Map<String, Object> getMapByResponseXml(String responseXml) throws JDOMException, IOException{
    	Map<String, Object> map = new HashMap<String, Object>();
    	SAXBuilder saxBuilder = new SAXBuilder();
    	InputStream in = new ByteArrayInputStream(responseXml.getBytes("UTF-8"));
        Document doc = saxBuilder.build(in);
        Element element = doc.getRootElement();//获得xml的根元素
        map.put("return_code", element.getChildText("return_code"));
        map.put("return_msg", element.getChildText("return_msg"));
        if("SUCCESS".equals(map.get("return_code"))){
        	map.put("appid", element.getChildText("appid"));
            map.put("mch_id", element.getChildText("mch_id"));
            map.put("device_info", element.getChildText("device_info"));
            map.put("nonce_str", element.getChildText("nonce_str"));
            map.put("sign", element.getChildText("sign"));
            map.put("result_code", element.getChildText("result_code"));
            map.put("err_code", element.getChildText("err_code"));
            map.put("err_code_des", element.getChildText("err_code_des"));
            
            //微信支付异步回调参数
//            map.put("openid", element.getChildText("openid"));
//            map.put("is_subscribe", element.getChildText("is_subscribe"));
//            map.put("trade_type", element.getChildText("trade_type"));
//            map.put("bank_type", element.getChildText("bank_type"));
//            map.put("total_fee", element.getChildText("total_fee"));
//            map.put("fee_type", element.getChildText("fee_type"));
//            map.put("cash_fee", element.getChildText("cash_fee"));
//            map.put("cash_fee_type", element.getChildText("cash_fee_type"));
//            map.put("coupon_fee", element.getChildText("coupon_fee"));
//            map.put("coupon_count", element.getChildText("coupon_count"));
//            map.put("coupon_id_$n", element.getChildText("coupon_id_$n"));
//            map.put("coupon_fee_$n", element.getChildText("coupon_fee_$n"));
//            map.put("transaction_id", element.getChildText("transaction_id"));
//            map.put("out_trade_no", element.getChildText("out_trade_no"));
//            map.put("attach", element.getChildText("attach"));
//            map.put("time_end", element.getChildText("time_end"));
            //微信支付调起支付参数
            if("SUCCESS".equals(map.get("result_code"))){
            	map.put("trade_type", element.getChildText("trade_type"));
            	map.put("prepay_id", element.getChildText("prepay_id"));//微信内部H5支付，trade_type为JSAPI时有返回，用于js调起微信内部浏览器支付
            	map.put("code_url", element.getChildText("code_url"));//PC扫码支付地址，trade_type为NATIVE时有返回，用于生成二维码，展示给用户进行扫码支付
            	map.put("mweb_url", element.getChildText("mweb_url"));//H5支付，mweb_url为拉起微信支付收银台的中间页面，可通过访问该url来拉起微信客户端，完成支付,mweb_url的有效期为5分钟。
            }
        }
    	return map;
    }
    
    //生成签名  
    private static String createSign(String characterEncoding, Map<String, Object> map, String API_KEY) throws NoSuchAlgorithmException, UnsupportedEncodingException{  
    	SortedMap<String, Object> parameters = new TreeMap<String, Object>(map);
        StringBuffer sb = new StringBuffer();  
        Set es = parameters.entrySet();  
        Iterator it = es.iterator();
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
            	sb.append(k + "=" + v + "&");  
	        }  
	    }  
	    sb.append("key=" + API_KEY);
	    String sign = EncoderByMd5(sb.toString(), characterEncoding).toUpperCase(); 
	    return sign;
	}
    
    //MD5加密
    private static String EncoderByMd5(String md5Str, String characterEncoding) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    	//确定计算方法
    	MessageDigest md5 = MessageDigest.getInstance("MD5");
		//加密后的字符串
		byte[] result = md5.digest(md5Str.getBytes(characterEncoding));
		StringBuffer buffer = new StringBuffer();  
        // 把没一个byte 做一个与运算 0xff;  
        for (byte b : result) {
            // 与运算  
            int number = b & 0xff;// 加盐  
            String str = Integer.toHexString(number);  
            if (str.length() == 1) {  
                buffer.append("0");  
            }
            buffer.append(str);  
        }  
        // 标准的md5加密后的结果  
        return buffer.toString();  
	}

	//请求方法  
    private static Map<String, Object> httpsRequest(String requestUrl, String requestMethod, String outputStr) throws IOException { 
    	HttpURLConnection conn = null;
    	InputStream inputStream = null;
    	InputStreamReader inputStreamReader = null;
    	BufferedReader bufferedReader = null;
    	try {
    		URL url = new URL(requestUrl);  
    		conn = (HttpURLConnection) url.openConnection();  
		  
    		conn.setDoOutput(true);  
    		conn.setDoInput(true);  
    		conn.setUseCaches(false);  
    		// 设置请求方式（GET/POST）  
    		conn.setRequestMethod(requestMethod);  
    		conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");  
    		// 当outputStr不为null时向输出流写数据  
    		if (null != outputStr) {  
    			OutputStream outputStream = conn.getOutputStream();  
    			// 注意编码格式  
    			outputStream.write(outputStr.getBytes("UTF-8"));  
    			outputStream.close();  
    		}  
    		// 从输入流读取返回内容  
    		inputStream = conn.getInputStream();  
    		inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
    		bufferedReader = new BufferedReader(inputStreamReader);  
    		String str = null;  
    		StringBuffer buffer = new StringBuffer();  
    		while ((str = bufferedReader.readLine()) != null) {  
    			buffer.append(str);
    		}
    		return getMapByResponseXml(buffer.toString());  
    	} catch (ConnectException | JDOMException ce) {
    		logger.error("微信支付异常，接收请求返回参数解析失败。");
    	} finally {
    		// 释放资源  
    		if(null != bufferedReader){
    			bufferedReader.close();
    		}
    		if(null != inputStreamReader){
    			inputStreamReader.close();
    		} 
    		if(null != inputStream){
    			inputStream.close(); 
    		}  
    		if(null != conn){
    			conn.disconnect(); 
    		}
		} 
    	return null;  
    }

}
