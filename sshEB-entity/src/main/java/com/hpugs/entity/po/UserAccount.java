package com.hpugs.entity.po;

import java.util.Date;

import com.hpugs.commons.entity.BaseEntity;

/**
 * @Description 用户账号
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2018年2月3日 下午4:46:14
 */
public class UserAccount extends BaseEntity {
	
	private Integer id;      //账号Id
	private String account;  //账号
	private String mobile;   //手机号
	private String passwd;   //密码
	private String avatar;   //用户头像
	private Integer state;   //状态（1、正常（默认）；2、删除、3、冻结）
	private Date gmtCreate;  //创建时间
	private Date gmtModified;//修改时间
	private String remark;   //备注
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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
		return "UserAccount [id=" + id + ", account=" + account + ", mobile=" + mobile + ", passwd=" + passwd
				+ ", avatar=" + avatar + ", state=" + state + ", gmtCreate=" + gmtCreate + ", gmtModified="
				+ gmtModified + ", remark=" + remark + "]";
	}

}
