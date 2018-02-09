package com.hpugs.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.hpugs.ali.util.AliSendSMS;
import com.hpugs.commons.util.ConstantUtil;
import com.hpugs.commons.util.CreateNumber;
import com.hpugs.commons.util.DESUtil;
import com.hpugs.commons.util.SmsCodeManager;
import com.hpugs.commons.util.Utils;
import com.hpugs.dao.ILogSmsSendDao;
import com.hpugs.email.tencent.SendEmailUtil;
import com.hpugs.entity.po.LogSmsSend;
import com.hpugs.service.IPublicService;

/**
 * @Description 公共服务
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2018年2月8日 上午10:45:34
 */
public class PublicServiceImpl implements IPublicService {
	
	private ILogSmsSendDao logSmsSendDao;

	@Override
	public Map<String, Object> sendSmsCode(Map<String, String> requestMap) {
		//返会操作结果对象
		Map<String, Object> resultMap = Utils.createResultMap();
		
		//获取请求参数
		String requestIp = requestMap.get("requestIp");
		String mobile = requestMap.get("mobile");
		String source = requestMap.get("source");
		String smsCodeType = requestMap.get("smsCodeType");
		String aliSmsCodeType = requestMap.get("aliSmsCodeType");
		aliSmsCodeType = Utils.stringIsNotEmpty(aliSmsCodeType) ? aliSmsCodeType : "1";
		String aliMessageCode = requestMap.get("aliMessageCode");
		aliMessageCode = Utils.stringIsNotEmpty(aliMessageCode) ? aliMessageCode : ConstantUtil.MESSAGE_CODE;
		
		//短信发送日志
		LogSmsSend logSmsSend = new LogSmsSend();
		logSmsSend.setIp(requestIp);
		logSmsSend.setPhone(mobile);
		logSmsSend.setSource(Integer.parseInt(source));
		if(null != mobile && 11 == mobile.length()){
			if(ConstantUtil.PC_LOGIN_CODE_TYPE.equals(smsCodeType) //PC登录短信验证码标示
					|| ConstantUtil.PC_REGISTER_CODE_TYPE.equals(smsCodeType) //PC注册短信验证码标示
					|| ConstantUtil.PC_FORGET_CODE_TYPE.equals(smsCodeType) //PC找回密码短信验证码标示
					|| ConstantUtil.PC_UPDATE_CODE_TYPE.equals(smsCodeType) //PC修改密码短信验证码标示
					|| ConstantUtil.PC_THIRD_BINDING_CODE_TYPE.equals(smsCodeType)){//PC第三方账号绑定短信验证码标示
				//短信验证码参数
				logSmsSend.setType(Integer.parseInt(aliSmsCodeType));
				logSmsSend.setAutograph(ConstantUtil.SmsFreeSignName);
				logSmsSend.setTemplate(aliMessageCode);
				
				try {
					resultMap = checkAbleSendSmsCode(logSmsSend, resultMap);
					if(ConstantUtil.RESULT_STATUS_SUCCESS_STR.equals(resultMap.get(ConstantUtil.RESULT_STATUS_KEY).toString())){//判断是否符合发送短信条件
						//生成短信验证码
						String codeNum = CreateNumber.getSmsCode(ConstantUtil.SMS_CODE_LENGTH);
						//将验证码保存到session中
						Map<String, String> smsCode = SmsCodeManager.getSmsCodeMapById(mobile);
						if(null != smsCode){
							smsCode.remove(smsCodeType);//移除之前的验证码
						}else{
							smsCode = new HashMap<String, String>();
						}
						smsCode.put(smsCodeType, DESUtil.encrypt(mobile+":"+smsCodeType+":"+codeNum+":"+ConstantUtil.SMS_CODE_DATE_SDF.format(new Date())));//保存最新的验证码
						SmsCodeManager.saveSmsCode(mobile, smsCode);
						//封装发送的短信验证码
						Map<String, String> smsRequestMap = new HashMap<String, String>();
						smsRequestMap.put("code", codeNum);//短信验证码
						logSmsSend.setContent(DESUtil.encrypt(JSON.toJSONString(smsRequestMap)));//保存短信内容
						SendSmsResponse resp = AliSendSMS.aliYunSend(logSmsSend.getPhone(), smsRequestMap, logSmsSend.getTemplate());
						if(null != resp){
							resultMap = saveSmsLog(logSmsSend, resp, resultMap);
							if(ConstantUtil.RESULT_STATUS_SUCCESS_STR.equals(resultMap.get(ConstantUtil.RESULT_STATUS_KEY).toString())){//判断是否符合发送短信条件
								resultMap.put(ConstantUtil.RESULT_MSG_KEY, "短信已发送到您" + mobile.substring(0, 3) + "****" + mobile.substring(mobile.length()-4) + "手机，请注意查收");
							}
						}else{
							resultMap.put(ConstantUtil.RESULT_STATUS_KEY, ConstantUtil.RESULT_STATUS_FAIL_STR);
							resultMap.put(ConstantUtil.RESULT_MSG_KEY, ConstantUtil.RESULT_SEND_MESSAGE_FAIL);
						}
					}
				} catch (Exception e) {
					SendEmailUtil.send("短信发送异常", "1、短信对象："+JSONObject.toJSONString(logSmsSend)+";2、错误日志："+e.toString());
					resultMap.put(ConstantUtil.RESULT_MSG_KEY, "短信发送异常，请联系客服");
				}
			}else{
				resultMap.put(ConstantUtil.RESULT_MSG_KEY, "短信类型不存在");
			}
		}else{
			resultMap.put(ConstantUtil.RESULT_MSG_KEY, "手机号输入非法");
		}
		
		return resultMap;
	}
	
	/**
	 * @Description 短信验证码发送量控制
	 * @param resultMap
	 * @return
	 * 
	 * 短信验证码 ：使用同一个签名，对同一个手机号码发送短信验证码，1条/分钟，5条/小时，10条/天。一个手机号码通过阿里大于平台只能收到40条/天。
	 * 
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年8月4日 下午5:34:08
	 */
	public Map<String, Object> checkAbleSendSmsCode(LogSmsSend logSmsSend, Map<String, Object> resultMap){
		//得到当前日期0点0分0秒
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//用于获取一个IP下1天内同一个短信模板接收到的短信量
		String hqlIP = "WHERE ip = '" + logSmsSend.getIp() + "' AND template = '" + logSmsSend.getTemplate() + "' AND enabled = 2 AND source = " + logSmsSend.getSource() + " AND responseSubTime > '" + sdf.format(new Date()) + "'";
		String hqlPhone = "FROM LogSmsSend WHERE phone = '" + logSmsSend.getPhone() + "' AND template = '" + logSmsSend.getTemplate() + "' AND enabled = 2 AND responseSubTime > '" + sdf.format(new Date()) + "'";
		
		Integer ipCount = logSmsSendDao.getCount(hqlIP);
		if(10 > ipCount){
			List<LogSmsSend> logSmsSends = (List<LogSmsSend>) logSmsSendDao.findByHQL(hqlPhone);
			if(null == logSmsSends){
				resultMap.put(ConstantUtil.RESULT_STATUS_KEY, ConstantUtil.RESULT_STATUS_SUCCESS_STR);
				resultMap.put(ConstantUtil.RESULT_MSG_KEY, "短信可以发送");
			}else if(10 > logSmsSends.size()){
				int hCount = 0;
				for(LogSmsSend item : logSmsSends){
					if(new Date().getHours() == item.getResponseSubTime().getHours()){
						hCount++;
					}
				}
				if(5 > hCount){
					resultMap.put(ConstantUtil.RESULT_STATUS_KEY, ConstantUtil.RESULT_STATUS_SUCCESS_STR);
					resultMap.put(ConstantUtil.RESULT_MSG_KEY, "短信可以发送");
				}else{
					resultMap.put(ConstantUtil.RESULT_MSG_KEY, "您手机当前1小时内获取短信达到上限，如有需要请联系客服。谢谢");
				}
			}else{
				resultMap.put(ConstantUtil.RESULT_MSG_KEY, "您手机今日获取短信达到上限，如有需要请联系客服。谢谢");
			}
		}else{
			resultMap.put(ConstantUtil.RESULT_MSG_KEY, "当前网络获取短信达到上限，请切换网络重试。谢谢");
		}
		return resultMap;
	}
	
	/**
	 * @Description 短信发送记录保存
	 * @param resp
	 * @param resultMap
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年8月4日 下午5:34:34
	 */
	public Map<String, Object> saveSmsLog(LogSmsSend logSmsSend, SendSmsResponse resp, Map<String, Object> resultMap){
		//短信发送状态码
		logSmsSend.setResponseCode(resp.getCode());
		logSmsSend.setResponseMsg(resp.getMessage());
		logSmsSend.setRequestId(resp.getRequestId());
		logSmsSend.setBizId(resp.getBizId());
		if(null != resp && "OK".equals(resp.getCode())){//短信是否发送成功
			resultMap.put(ConstantUtil.RESULT_STATUS_KEY, ConstantUtil.RESULT_STATUS_SUCCESS_STR);
			resultMap.put(ConstantUtil.RESULT_MSG_KEY, "短信发送成功");
		}else{
			resultMap.put(ConstantUtil.RESULT_STATUS_KEY, ConstantUtil.RESULT_STATUS_FAIL_STR);
			resultMap.put(ConstantUtil.RESULT_MSG_KEY, "短信发送失败");
		}
		logSmsSend.setResponseSubTime(new Date());//短信发送时间
		logSmsSend.setEnabled(2);//记录是否有效
		logSmsSend.setGmtCreate(new Date());//记录创建时间
		logSmsSendDao.saveOrUpdateObject(logSmsSend);
		return resultMap;
	}

	public void setLogSmsSendDao(ILogSmsSendDao logSmsSendDao) {
		this.logSmsSendDao = logSmsSendDao;
	}

}
