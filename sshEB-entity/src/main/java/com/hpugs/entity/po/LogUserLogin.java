package com.hpugs.entity.po;

import java.util.Date;

import com.hpugs.commons.entity.BaseEntity;

/**
 * @Description 用户登录日志
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2017年12月24日 下午2:23:33
 */
public class LogUserLogin extends BaseEntity {
	
	private Integer id;          //登录日志id
	private String ip;           //用户登录Ip地址
	private String sessionId;    //登录用户SessionId
	private Integer accountType; //账号类型（1、用户名；2、手机号（默认）；3、邮箱）
	private String loginAccount; //登录账号
	private Date loginTime;      //登录时间
	private String loginResult;  //登录返回：登录成功；登录失败；不存在该用户
	private Integer loginState;  //登录状态：（1：成功；2：失败 ）用于一天内多次登录失败锁定账号
	private Integer source;      //来源（1：PC（默认）；2：H5；3、Android；4、IOS；5、ERP）
	private Integer state;       //是否删除（1、是（默认）；2：否）
	private Integer updateUserId;//修改用户Id(来自ERP）
	private Date gmtCreate;      //创建时间
	private Date gmtModified;    //修改时间
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

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginResult() {
		return loginResult;
	}

	public void setLoginResult(String loginResult) {
		this.loginResult = loginResult;
	}

	public Integer getLoginState() {
		return loginState;
	}

	public void setLoginState(Integer loginState) {
		this.loginState = loginState;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "LogUserLogin [id=" + id + ", ip=" + ip + ", sessionId=" + sessionId + ", accountType=" + accountType
				+ ", loginAccount=" + loginAccount + ", loginTime=" + loginTime + ", loginResult=" + loginResult
				+ ", loginState=" + loginState + ", source=" + source + ", state=" + state + ", updateUserId="
				+ updateUserId + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + ", remark=" + remark
				+ "]";
	}
	
}
