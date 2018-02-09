package com.hpugs.entity.po;

import java.util.Date;

import com.hpugs.commons.entity.BaseEntity;

/**
 * @Description 短信发送日志
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2017年12月24日 下午2:17:19
 */
public class LogSmsSend extends BaseEntity {
	
	private Integer id;          //登录日志id
	private String ip;           //用户Ip地址
	private String phone;        //用户手机号
	private String content;      //短信详情
	private Integer type;        //短信类型 (1：验证码；2：通知)
	private String autograph;    //短信签名（阿里云短信后台提供）
	private String template;     //短信模板（阿里云短信后台提供）
	private String responseCode; //短信发送状态
	private String responseMsg;  //短信发送日志
	private String requestId;    //请求id
	private String bizId;        //状态id
	private Date responseSubTime;//短信发送时间 
	private Integer source;      //来源（1：PC（默认）；2：H5；3、Android；4、IOS；5、ERP）
	private Integer enabled;     //是否有效 （1：是；2：否）
	private Date gmtCreate;      //创建时间
	private String remark;       //备注
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAutograph() {
		return autograph;
	}

	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public Date getResponseSubTime() {
		return responseSubTime;
	}

	public void setResponseSubTime(Date responseSubTime) {
		this.responseSubTime = responseSubTime;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "LogSmsSend [id=" + id + ", ip=" + ip + ", phone=" + phone + ", content=" + content + ", type=" + type
				+ ", autograph=" + autograph + ", template=" + template + ", responseCode=" + responseCode
				+ ", responseMsg=" + responseMsg + ", requestId=" + requestId + ", bizId=" + bizId
				+ ", responseSubTime=" + responseSubTime + ", source=" + source + ", enabled=" + enabled
				+ ", gmtCreate=" + gmtCreate + ", remark=" + remark + "]";
	}
	
}
