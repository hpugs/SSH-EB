package com.hpugs.email.tencent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.hpugs.commons.util.ConstantUtil;
import com.sun.mail.util.MailSSLSocketFactory;

/**
 * @Description 邮件管理类
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2017年11月19日 下午3:25:05
 */
public class EmailManageUtil {
	
	private static class MyAuthenricator extends Authenticator{  
        String u = null;  
        String p = null;  
        public MyAuthenricator(final String u,final String p){  
            this.u=u;  
            this.p=p;  
        }  
        @Override  
        protected PasswordAuthentication getPasswordAuthentication() {  
            return new PasswordAuthentication(u,p);  
        }  
    }
	
	private static Properties prop = new Properties();
	/**
	 * @Description 邮件发送初始化
	 * @param userName
	 * @param password
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年11月19日 下午4:37:53
	 */
	private static void initSendProp(){
		//协议
        prop.setProperty("mail.transport.protocol", ConstantUtil.SEND_EMAIL_TRANSPORT_PROTOCOL);
        //服务器
        prop.setProperty("mail.smtp.host", ConstantUtil.SEND_EMAIL_SMTP_HOST);
        //端口
        prop.setProperty("mail.smtp.port", ConstantUtil.SEND_EMAIL_SMTP_PORT);
        //使用smtp身份验证
        prop.setProperty("mail.smtp.auth", "true");
        //使用SSL，企业邮箱必需！
        //开启安全协议
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e1) {
            e1.printStackTrace();
        }
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);
	}
	
	/**
	 * @Description 发送电子邮件
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年11月19日 下午4:05:45
	 */
	public static boolean sendEmail(final EmailPO emailPO){
		boolean flag = false;
		initSendProp();
        Session session = Session.getDefaultInstance(prop, new MyAuthenricator(ConstantUtil.EMAIL_NAME, ConstantUtil.EMAIL_PASSWORD));
        session.setDebug(false);
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            //发件人
        	if(null != emailPO.getSendEmailUserNickName()){
        		mimeMessage.setFrom(new InternetAddress(ConstantUtil.EMAIL_NAME, emailPO.getSendEmailUserNickName()));//第一个参数为发送方电子邮箱地址；第二个参数为发送方邮箱地址的标签
        	}else{
        		mimeMessage.setFrom(new InternetAddress(ConstantUtil.EMAIL_NAME));
        	}
            //收件人
        	if(null != emailPO.getGetEmailUserNickName()){
        		mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailPO.getGetEmailUserNumber(), emailPO.getGetEmailUserNickName()));//第一个参数为接收方电子邮箱地址；第二个参数为接收方邮箱地址的标签
        	}else{
        		mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailPO.getGetEmailUserNumber(), emailPO.getGetEmailUserNickName()));
        	}
            //主题
            mimeMessage.setSubject(emailPO.getSubject());
            //时间
            if(null != emailPO.getSentDate()){
            	mimeMessage.setSentDate(emailPO.getSentDate());
            }else{
            	mimeMessage.setSentDate(new Date());
            }
            //设置邮件内容
            if(null != emailPO.getMultipart()){//判断邮件容器是否为空
            	//包含富文本消息
                mimeMessage.setContent(emailPO.getMultipart());
            }else{
            	//仅仅发送文本
            	mimeMessage.setText(emailPO.getContext());
            }
            mimeMessage.saveChanges();
            Transport.send(mimeMessage);//发送邮件
            flag = true;
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return flag;
	}
	
	/**
	 * @Description 邮件接收初始化
	 * @param userName
	 * @param password
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年11月19日 下午4:37:53
	 */
	private static void initGetProp(){
		//协议
        prop.setProperty("mail.transport.protocol", ConstantUtil.GET_EMAIL_TRANSPORT_PROTOCOL);
        //服务器
        prop.setProperty("mail.smtp.host", ConstantUtil.GET_EMAIL_SMTP_HOST);
        //端口
        prop.setProperty("mail.smtp.port", ConstantUtil.GET_EMAIL_SMTP_PORT);
        //使用imap身份验证
        prop.setProperty("mail.smtp.auth", "true");
        //使用SSL，企业邮箱必需！
        //开启安全协议
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e1) {
            e1.printStackTrace();
        }
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);
	}
	
	/**
	 * @Description 接收电子邮件
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @throws MessagingException 
	 * @throws IOException 
	 * @date 创建时间：2017年11月19日 下午4:06:04
	 */
	public static List<EmailPO> getEmail() throws MessagingException, IOException{
		
		// 获取连接
		Session session = Session.getDefaultInstance(prop);
		session.setDebug(false);
		
		// 获取Store对象
		Store store = session.getStore(ConstantUtil.GET_EMAIL_TRANSPORT_PROTOCOL);
		store.connect(ConstantUtil.GET_EMAIL_SMTP_HOST, ConstantUtil.EMAIL_NAME, ConstantUtil.EMAIL_PASSWORD); // POP3服务器的登陆认证
		
		// 通过POP3协议获得Store对象调用这个方法时，邮件夹名称只能指定为"INBOX"
		Folder folder = store.getFolder("INBOX");// 获得用户的邮件帐户
		folder.open(Folder.READ_WRITE); // 设置对邮件帐户的访问权限
		
		Message[] messages = folder.getMessages();// 得到邮箱帐户中的所有邮件
		
		List<EmailPO> emailPOs = new ArrayList<EmailPO>();
		for (Message message : messages) {
			EmailPO emailPO = new EmailPO();
			emailPO.setSubject(message.getSubject());// 获得邮件主题
			Address from = (Address) message.getFrom()[0];// 获得发送者地址
			String fromEmail = from.toString();
			emailPO.setGetEmailUserNumber(fromEmail.substring(fromEmail.indexOf("<")+1, fromEmail.lastIndexOf(">")));
//			message.writeTo(System.out);// 输出邮件内容到控制台
			emailPOs.add(emailPO);
		}
		
		folder.close(false);// 关闭邮件夹对象
		store.close(); // 关闭连接对象
		return emailPOs;
	}

}
