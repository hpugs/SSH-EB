package com.hpugs.email.tencent;

import java.util.Date;

import javax.mail.Multipart;

/**
 * @Description 邮件实体类
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2017年11月19日 下午3:45:12
 */
public class EmailPO {
	
	private String sendEmailUserNickName;//发件人别名（便于客户端查看）
	private String getEmailUserNumber;//收件人邮箱
	private String getEmailUserNickName;//收件人别名（便于客户端查看）
	private String subject;//邮件的主题
	private String context;//邮件的正文
	private Date sentDate;//邮件发送时间
	private Multipart multipart;//容器类，可以包含多个MimeBodyPart对象
	
	public String getSendEmailUserNickName() {
		return sendEmailUserNickName;
	}
	public void setSendEmailUserNickName(String sendEmailUserNickName) {
		this.sendEmailUserNickName = sendEmailUserNickName;
	}
	public String getGetEmailUserNumber() {
		return getEmailUserNumber;
	}
	public void setGetEmailUserNumber(String getEmailUserNumber) {
		this.getEmailUserNumber = getEmailUserNumber;
	}
	public String getGetEmailUserNickName() {
		return getEmailUserNickName;
	}
	public void setGetEmailUserNickName(String getEmailUserNickName) {
		this.getEmailUserNickName = getEmailUserNickName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Date getSentDate() {
		return sentDate;
	}
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
	public Multipart getMultipart() {
		return multipart;
	}
	public void setMultipart(Multipart multipart) {
		this.multipart = multipart;
	}
	
}
