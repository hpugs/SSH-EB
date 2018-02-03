package com.hpugs.ali.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.hpugs.commons.util.ConstantUtil;

/**
 * @Description 支付宝支付
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2017年9月23日 下午7:06:25
 */
public class AliPayUtil {
	
	/**
	 * @Description 支付宝 PC支付接口调用
	 * @param out_trade_no
	 * @param total_amount
	 * @param subject
	 * @return
	 * @throws AlipayApiException
	 * 
	 * https://docs.open.alipay.com/270/alipay.trade.page.pay
	 * 
	 * 返回参数处理
	 * String form = alipayTradePagePayResponse.getBody(); //调用SDK生成表单
	 * httpResponse.setContentType("text/html;charset=" + CHARSET);
	 * httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
	 * httpResponse.getWriter().flush();
	 * httpResponse.getWriter().close();
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年9月23日 下午7:37:03
	 */
	public static AlipayTradePagePayResponse goPcPay(final String out_trade_no, final double total_amount, final String subject, final String returnUrl, final String notifyUrl, final String quitUrl) throws AlipayApiException {
		AlipayClient alipayClient = new DefaultAlipayClient(ConstantUtil.NET_PATH, ConstantUtil.APP_ID, ConstantUtil.APP_PRIVATE_KEY, ConstantUtil.FORMAT, ConstantUtil.CHARSET, ConstantUtil.ALIPAY_PUBLIC_KEY, ConstantUtil.SIGN_TYPE); //获得初始化的AlipayClient
	    AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
	    //支付成功后返回页面
		alipayRequest.setReturnUrl(returnUrl);// 支付宝处理完请求后，当前页面自动跳转到商户网站里指定页面的http路径。
		//支付完成异步通知页面
		alipayRequest.setNotifyUrl(notifyUrl);// 支付宝服务器主动通知商户服务器里指定的页面http/https路径。
		
		//填充业务参数
		Map<String, String> bizContent = new HashMap<String, String>();
		bizContent.put("out_trade_no", out_trade_no);//总订单号
		bizContent.put("total_amount", total_amount + "");//订单总金额
		bizContent.put("subject", subject);//支付描述
		bizContent.put("timeout_express", ConstantUtil.PAY_OUT_TIME);//支付超时时间
		bizContent.put("quit_url", quitUrl);//支付宝支付页面点击返回 
		bizContent.put("passback_params", URLEncoder.encode(ConstantUtil.ALI_PAY_PASSBACK_PARAMS));//公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝会在异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝
		alipayRequest.setBizContent(JSON.toJSONString(bizContent, SerializerFeature.WriteMapNullValue));
		
		//发起支付宝请求
	    AlipayTradePagePayResponse alipayTradePagePayResponse = alipayClient.pageExecute(alipayRequest);//调用SDK生成表单
	    return alipayTradePagePayResponse;
	}
	
	/**
	 * @Description 支付宝 H5支付接口调用
	 * @param out_trade_no 商户订单号，需要保证不重复
	 * @param total_amount 订单金额
	 * @param body 订单详细
	 * @param timeout_express
	 *            该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。
	 *            该参数数值不接受小数点， 如 1.5h，可转换为 90m。 注：若为空，则默认为15d。
	 * @param product_code 销售产品码，商家和支付宝签约的产品码。该产品请填写固定值：QUICK_WAP_WAY
	 * @return
	 * @throws AlipayApiException
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年9月23日 下午7:07:57
	 */
	public static AlipayTradeWapPayResponse goH5Pay(final String out_trade_no, final double total_amount, final String subject, final String returnUrl, final String notifyUrl, final String quitUrl) throws AlipayApiException {
		AlipayTradeWapPayResponse alipayTradeWapPayResponse = null;
		if (null != out_trade_no && 0 < total_amount && null != subject) {
			AlipayClient alipayClient = new DefaultAlipayClient(ConstantUtil.NET_PATH, ConstantUtil.APP_ID, ConstantUtil.APP_PRIVATE_KEY, ConstantUtil.FORMAT, ConstantUtil.CHARSET, ConstantUtil.ALIPAY_PUBLIC_KEY, ConstantUtil.SIGN_TYPE); // 获得初始化的AlipayClient
			AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();// 创建API对应的request
			//支付成功后返回页面
			alipayRequest.setReturnUrl(returnUrl);// 支付宝处理完请求后，当前页面自动跳转到商户网站里指定页面的http路径。
			//支付完成异步通知页面
			alipayRequest.setNotifyUrl(notifyUrl);// 支付宝服务器主动通知商户服务器里指定的页面http/https路径。
			
			//填充业务参数
			Map<String, String> bizContent = new HashMap<String, String>();
			bizContent.put("out_trade_no", out_trade_no);//总订单号
			bizContent.put("total_amount", total_amount + "");//订单总金额
			bizContent.put("subject", subject);//支付描述
			bizContent.put("timeout_express", ConstantUtil.PAY_OUT_TIME);//支付超时时间
			bizContent.put("QUICK_WAP_PAY", "QUICK_WAP_WAY");//销售产品码，商家和支付宝签约的产品码。该产品请填写固定值：QUICK_WAP_WAY
			bizContent.put("quit_url", quitUrl);//支付宝支付页面点击返回 
			bizContent.put("passback_params", URLEncoder.encode(ConstantUtil.ALI_PAY_PASSBACK_PARAMS));//公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝会在异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝
			alipayRequest.setBizContent(JSON.toJSONString(bizContent, SerializerFeature.WriteMapNullValue));
			
			//发起支付宝请求
			alipayTradeWapPayResponse = alipayClient.pageExecute(alipayRequest);// 调用SDK生成表单
		}
		return alipayTradeWapPayResponse;
	}
	
	/**
	 * @Description 支付宝 APP支付接口调用
	 * @param out_trade_no 商户订单号，需要保证不重复
	 * @param total_amount 订单金额
	 * @param subject 支付描述
	 * @param timeout_express
	 *            该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。
	 *            该参数数值不接受小数点， 如 1.5h，可转换为 90m。 注：若为空，则默认为15d。
	 * @param product_code 销售产品码，商家和支付宝签约的产品码。该产品请填写固定值：QUICK_MSECURITY_PAY
	 * @return
	 * @throws AlipayApiException
	 * @throws UnsupportedEncodingException
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年9月23日 下午7:07:57
	 */
	public static String goAppPay(final String out_trade_no, final double total_amount, final String subject, final String notifyUrl) throws AlipayApiException, UnsupportedEncodingException {
		String orderStr = "";
		
		//判断传入参数是否异常
		if (null != out_trade_no && 0 < total_amount && null != subject) {
			//实例化客户端
			AlipayClient alipayClient = new DefaultAlipayClient(ConstantUtil.NET_PATH, ConstantUtil.APP_ID, ConstantUtil.APP_PRIVATE_KEY, ConstantUtil.FORMAT, ConstantUtil.CHARSET, ConstantUtil.ALIPAY_PUBLIC_KEY, ConstantUtil.SIGN_TYPE);
			//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
			AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
			//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
			AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
			model.setBody(subject);
			model.setSubject(subject);
			model.setOutTradeNo(out_trade_no);
			model.setTimeoutExpress(ConstantUtil.PAY_OUT_TIME);
			model.setTotalAmount(total_amount + "");
			model.setProductCode("QUICK_MSECURITY_PAY");
			request.setBizModel(model);
			request.setNotifyUrl(notifyUrl);
			try {
			        //这里和普通的接口调用不同，使用的是sdkExecute
			        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
			        //System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
			        orderStr = response.getBody();
			    } catch (AlipayApiException e) {
			        e.printStackTrace();
			}
			
		}
		return orderStr;
	}
	
	/**
	 * @Description 统一收单关闭支付交易订单
	 * @param out_trade_no 平台统一订单
	 * @param operator_id 平台业务员Id
	 * @return
	 * @throws AlipayApiException
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年9月23日 下午7:09:09
	 */
	public static AlipayTradeCloseResponse alipayTradeClose(final String out_trade_no, final String operator_id) throws AlipayApiException{
		AlipayTradeCloseResponse alipayTradeCloseResponse = null;
		
		if(null != out_trade_no){
			//创建支付宝连接
			AlipayClient alipayClient = new DefaultAlipayClient(ConstantUtil.NET_PATH, ConstantUtil.APP_ID, ConstantUtil.APP_PRIVATE_KEY, ConstantUtil.FORMAT, ConstantUtil.CHARSET, ConstantUtil.ALIPAY_PUBLIC_KEY, ConstantUtil.SIGN_TYPE);
			AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
			
			//业务请求参数的集合，
			Map<String, String> bizContent = new HashMap<String, String>();
			bizContent.put("out_trade_no", out_trade_no);//平台统一总订单号
			bizContent.put("operator_id", operator_id != null ? operator_id : "");//平台业务员Id
			request.setBizContent(JSON.toJSONString(bizContent, SerializerFeature.WriteMapNullValue));
			
			//发起支付宝请求
			alipayTradeCloseResponse = alipayClient.execute(request);
		}
		return alipayTradeCloseResponse;
	}
	
	/**
	 * @Description 统一收单交易退款接口
	 * @param out_trade_no 订单支付时传入的商户订单号,不能和 trade_no同时为空。 （必填）
	 * @param total_amount 需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数（必填）
	 * @param refund_reason 退款的原因说明
	 * @param out_request_no 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
	 * @return
	 * @throws AlipayApiException
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年12月6日 下午5:47:01
	 */
	public static AlipayTradeRefundResponse alipayRefund(final String out_trade_no, final double refund_amount, final String refund_reason, final String out_request_no) throws AlipayApiException{
		AlipayTradeRefundResponse response = null;
		if(null != out_trade_no){
			if(0 < refund_amount){
				AlipayClient alipayClient = new DefaultAlipayClient(ConstantUtil.NET_PATH, ConstantUtil.APP_ID, ConstantUtil.APP_PRIVATE_KEY, ConstantUtil.FORMAT, ConstantUtil.CHARSET, ConstantUtil.ALIPAY_PUBLIC_KEY, ConstantUtil.SIGN_TYPE);
				AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
				//业务请求参数的集合，
				Map<String, String> bizContent = new HashMap<String, String>();
				bizContent.put("out_trade_no", out_trade_no);
				bizContent.put("refund_amount", refund_amount + "");
				bizContent.put("refund_reason", refund_reason);
				bizContent.put("out_request_no", out_request_no);
				request.setBizContent(JSON.toJSONString(bizContent, SerializerFeature.WriteMapNullValue));
				response = alipayClient.execute(request);
			}
		}
		return response;
	}
	
	/**
	 * @Description 统一收单交易退款查询
	 * @param out_trade_no 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。 trade_no,out_trade_no如果同时存在优先取trade_no
	 * @param out_request_no 请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号
	 * @return
	 * @throws AlipayApiException
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年12月6日 下午5:57:21
	 */
	public static AlipayTradeFastpayRefundQueryResponse alipayRefundQuery(final String out_trade_no, final String out_request_no) throws AlipayApiException{
		AlipayTradeFastpayRefundQueryResponse response = null;
		if(null != out_trade_no){
			if(null != out_request_no){
				AlipayClient alipayClient = new DefaultAlipayClient(ConstantUtil.NET_PATH, ConstantUtil.APP_ID, ConstantUtil.APP_PRIVATE_KEY, ConstantUtil.FORMAT, ConstantUtil.CHARSET, ConstantUtil.ALIPAY_PUBLIC_KEY, ConstantUtil.SIGN_TYPE);
				AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
				//业务请求参数的集合，
				Map<String, String> bizContent = new HashMap<String, String>();
				bizContent.put("out_trade_no", out_trade_no);
				bizContent.put("out_request_no", out_request_no);
				request.setBizContent(JSON.toJSONString(bizContent, SerializerFeature.WriteMapNullValue));
				response = alipayClient.execute(request);
			}
		}
		return response;
	}
	
	/**
	 * @Description 支付宝加签方法（如果不用SDK调用，推荐用该方法加签）
	 * @param content 待签名字符串
	 * @return 签名结果
	 * @throws AlipayApiException
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年9月23日 下午7:09:39
	 */
	public static String alipayRsaSign(String content) throws AlipayApiException{
		String sign = AlipaySignature.rsaSign(content, ConstantUtil.APP_PRIVATE_KEY, ConstantUtil.CHARSET, ConstantUtil.SIGN_TYPE);
		return sign;
	}
	
	/**
	 * 此方法会去掉sign_type做验签，暂时除生活号（原服务窗）激活开发者模式外都使用V1。
	 * @Description 根据支付宝的通知返回参数，进行参数校验
	 * @param params 支付宝的通知返回参数
	 * @return 通知是否来自于支付宝
	 * @throws AlipayApiException
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年9月23日 下午7:10:26
	 */
	public static boolean alipayRsaCheckV1(Map<String, String> params) throws AlipayApiException{
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		//计算得出通知验证结果
		boolean verify_result = AlipaySignature.rsaCheckV1(params, ConstantUtil.ALIPAY_PUBLIC_KEY, ConstantUtil.CHARSET, ConstantUtil.SIGN_TYPE);
		return verify_result;
	}
	
	/**
	 * 此方法不会去掉sign_type验签，用于生活号（原服务窗）激活开发者模式
	 * @Description 根据支付宝的通知返回参数，进行参数校验
	 * @param params 支付宝的通知返回参数
	 * @return 通知是否来自于支付宝
	 * @throws AlipayApiException
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年9月23日 下午7:10:26
	 */
	public static boolean alipayRsaCheckV2(Map<String,String> params) throws AlipayApiException{
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		//计算得出通知验证结果
		boolean verify_result = AlipaySignature.rsaCheckV2(params, ConstantUtil.ALIPAY_PUBLIC_KEY, ConstantUtil.CHARSET, ConstantUtil.SIGN_TYPE);
		return verify_result;
	}

}
