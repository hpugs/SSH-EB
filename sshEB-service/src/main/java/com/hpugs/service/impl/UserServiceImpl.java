package com.hpugs.service.impl;

import java.util.Map;

import com.hpugs.dao.IUserDao;
import com.hpugs.service.IUserService;

/**
 * @Description 用户模块
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2018年2月8日 上午9:44:20
 */
public class UserServiceImpl implements IUserService {
	
	private IUserDao userDao;

	@Override
	public Map<String, Object> loginAccount(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> loginMobile(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> saveUser(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String, Object> updatePasswd(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getUserInfo(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

}
