package com.hpugs.ali.util;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.hpugs.commons.util.ConstantUtil;

/**
 * @Description 阿里大于发短信接口:https://api.alidayu.com/docs/api.htm?spm=a3142.7395905.4.6.dby5kF&apiId=25450
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2017年6月16日 下午1:23:27
 */
public class AliSendSMS {
	/**
	 * @Description 集成阿里云短信服务接口
	 * @param phoneNumber 接收短信手机号
	 * @param reqMap 短信发送map配置
	 * @param smsTemplateCode 短信模板ID
	 * @return
	 *
	 * 短信验证码 ：使用同一个签名，对同一个手机号码发送短信验证码，1条/分钟，5条/小时，10条/天。一个手机号码通过阿里大于平台只能收到40条/天。
	 *
	 * @author 高尚
	 * @version 1.0
	 * @throws ClientException 
	 * @throws JsonProcessingException 
	 * @date 创建时间：2017年8月7日 下午12:11:22
	 */
	public static SendSmsResponse aliYunSend(final String phoneNumber, final Map<String, String> reqMap, final String smsTemplateCode) throws ClientException{
		//设置超时时间-可自行调整
		System.setProperty("sun.net.client.defaultConnectTimeout", ConstantUtil.defaultConnectTimeout);
		System.setProperty("sun.net.client.defaultReadTimeout", ConstantUtil.defaultReadTimeout);
		
		//初始化ascClient,暂时不支持多region
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ConstantUtil.accessKeyId_SMS, ConstantUtil.accessKeySecret_SMS);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", ConstantUtil.product, ConstantUtil.domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		//组装请求对象
		SendSmsRequest request = new SendSmsRequest();
		//使用post提交
		request.setMethod(MethodType.POST);
		//必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
		request.setPhoneNumbers(phoneNumber);
		//必填:短信签名-可在短信控制台中找到
		request.setSignName(ConstantUtil.SmsFreeSignName);
		//必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(smsTemplateCode);
		//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		//友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		if(null != reqMap){
			request.setTemplateParam(JSON.toJSONString(reqMap));
		}
		//可选-上行短信扩展码(无特殊需求用户请忽略此字段)
		//request.setSmsUpExtendCode("90997");
		//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		//request.setOutId("manyiaby");
		//请求失败这里会抛ClientException异常
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		return sendSmsResponse;
	}
	
}
