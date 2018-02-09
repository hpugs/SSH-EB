package com.hpugs.service.impl;

import com.hpugs.dao.ILogSmsSendDao;
import com.hpugs.dao.ILogUserLoginDao;
import com.hpugs.service.ILogService;

/**
 * @Description 日志服务
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2018年2月9日 上午10:17:23
 */
public class LogServiceImpl implements ILogService {
	
	private ILogUserLoginDao logUserLoginDao;
	private ILogSmsSendDao logSmsSendDao;
	
	
	
	public void setLogUserLoginDao(ILogUserLoginDao logUserLoginDao) {
		this.logUserLoginDao = logUserLoginDao;
	}
	public void setLogSmsSendDao(ILogSmsSendDao logSmsSendDao) {
		this.logSmsSendDao = logSmsSendDao;
	}
	
}
