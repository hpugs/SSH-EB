package com.hpugs.email.tencent;

import com.hpugs.commons.util.ConstantUtil;

/**
 * @Description 发送电子邮件
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2017年12月5日 上午11:05:53
 */
public class SendEmailUtil {
	
	/**
	 * @Description 发送电子邮件
	 * @param emailPO 邮件内容详情对象
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年12月5日 上午11:21:39
	 */
	public static boolean send(EmailPO emailPO){
		boolean flag = false;
		flag = EmailManageUtil.sendEmail(emailPO);
		return flag;
	}
	
	/**
	 * @Description 发送文本邮件
	 * @param sendEmailNickName 发件人昵称
	 * @param emailNumber 收件人邮箱地址
	 * @param emailNickName 收件人昵称
	 * @param subject 邮件主题
	 * @param context 邮件详情
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年12月5日 上午11:22:12
	 */
	public static boolean send(final String sendEmailNickName, final String emailNumber, final String emailNickName, final String subject, final String context){
		boolean flag = false;
		EmailPO emailPO = new EmailPO();
		if(null != sendEmailNickName){
			emailPO.setSendEmailUserNickName(sendEmailNickName);
		}else{
			emailPO.setSendEmailUserNickName(ConstantUtil.EMAIL_NICK_NAME);
		}
		if(null != emailNumber){
			emailPO.setGetEmailUserNumber(emailNumber);
			if(null != emailNickName){
				emailPO.setGetEmailUserNickName(emailNumber);
			}
			if(null != subject){
				emailPO.setSubject(subject);
			}else{
				emailPO.setSubject("来自于满艺网");
			}
			if(null != context){
				emailPO.setContext(context);
			}else{
				emailPO.setSubject("满艺网后台发送，如有疑问请联系满艺网客服：15311737293");
			}
			flag = send(emailPO);
		}
		return flag;
	}
	
	/**
	 * @Description 发送文本邮件
	 * @param emailNumber 收件人邮箱地址
	 * @param emailNickName 收件人昵称
	 * @param subject 邮件主题
	 * @param context 邮件详情
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年12月5日 上午11:25:23
	 */
	public static boolean send(final String emailNumber, final String emailNickName, final String subject, final String context){
		boolean flag = false;
		flag = send(null, emailNumber, emailNickName, subject, context);
		return flag;
	}
	
	/**
	 * @Description 发送文本邮件
	 * @param subject 邮件主题
	 * @param context 邮件详情
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年12月5日 上午11:26:18
	 */
	public static boolean send(final String subject, final String context){
		boolean flag = false;
		flag = send(null, ConstantUtil.DEFAULT_EMAIL_NAME, ConstantUtil.DEFAULT_EMAIL_NICK_NAME, subject, context);
		return flag;
	}

}
