package com.hpugs.entity.po;

import java.util.Date;

import com.hpugs.commons.entity.BaseEntity;

/**
 * @Description 第三方登录实体类
 *
 * @author 高尚 
 * @version 1.0
 * @date 创建时间：2018年2月10日 下午3:26:33
 */
public class UserThirdLogin extends BaseEntity{
	private static final long serialVersionUID = 1L;
	private Integer id;// 第三方登录表id
	private Integer userAccountId;// 用户表id
	private Integer type;// 类型(1/2/3) 1:QQ 2:微信 3:新浪
	private String openId;
	private String msg; // 返回信息
	private String avatar; // 第三方头像
	private String nickName; // 第三方昵称
	private String gender; // 性别
	private String country; // 国家
	private String province; // 省份
	private String city; // 城市
	private Date gmtCreate; // 创建时间
	private Date gmtModified; // 记录最后一次修改时间
	private String remark; // 备注

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(Integer userAccountId) {
		this.userAccountId = userAccountId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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
		return "UserThirdLogin [id=" + id + ", userAccountId=" + userAccountId + ", type=" + type + ", openId=" + openId
				+ ", msg=" + msg + ", avatar=" + avatar + ", nickName=" + nickName + ", gender=" + gender + ", country="
				+ country + ", province=" + province + ", city=" + city + ", gmtCreate=" + gmtCreate + ", gmtModified="
				+ gmtModified + ", remark=" + remark + "]";
	}

}
