package com.hpugs.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hpugs.commons.util.ConstantUtil;
import com.hpugs.commons.util.DESUtil;
import com.hpugs.commons.util.Utils;
import com.hpugs.dao.ILogUserLoginDao;
import com.hpugs.dao.IUserAccountDao;
import com.hpugs.entity.po.LogUserLogin;
import com.hpugs.entity.po.UserAccount;
import com.hpugs.service.IUserService;

/**
 * @Description 用户模块
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2018年2月8日 上午9:44:20
 */
public class UserServiceImpl implements IUserService {
	
	private IUserAccountDao userAccountDao;
	private ILogUserLoginDao logUserLoginDao;

	@Override
	public Map<String, Object> loginAccount(Map<String, String> requestMap) {
		//返会操作结果对象
		Map<String, Object> resultMap = Utils.createResultMap();
		
		//获取传入参数
		String account = requestMap.get("account");
		String passwd = requestMap.get("passwd");
		String sessionId = requestMap.get("sessionId");
		String requestIp = requestMap.get("requestIp");
		
		//判断接口参数
		if(Utils.stringIsNotEmpty(account)){
			if(Utils.stringIsNotEmpty(passwd)){
				if(ConstantUtil.MAX_ERROR_LOGIN_NUMBER >= isMaxLoginErrorCount(account)){
					//登录日志
					LogUserLogin loginLog = new LogUserLogin();
					loginLog.setIp(requestIp);//请求IP地址
					loginLog.setSessionId(sessionId);//记录当前访问Session的Id
					loginLog.setAccountType(2);//账号类型（1、用户名；2、手机号（默认）；3、邮箱）
					loginLog.setLoginAccount(account);//登录账号
					loginLog.setLoginTime(new Date());//登录时间
					loginLog.setGmtCreate(new Date());//记录创建时间
					loginLog.setSource(1);//来源
					loginLog.setLoginState(2);//登录状态
					loginLog.setState(1);//记录状态值
					
					//进行账号校验
					String hql = "WHERE account='" + account + "' OR mobile = '" + account + "' ";
					List<UserAccount> userAccounts = userAccountDao.findObjects(hql);
					if(Utils.collectionIsNotEmpty(userAccounts)){
						if(1 == userAccounts.size()){
							if(DESUtil.encrypt(passwd).equals(userAccounts.get(0).getPasswd())){
								if(1 == userAccounts.get(0).getState()){
									//更新登录日志
									loginLog.setLoginState(1);//登录状态
									loginLog.setLoginResult("登录成功");//登录参数返回
									
									//更新返回data参数
									resultMap.put(ConstantUtil.RESULT_STATUS_KEY, ConstantUtil.RESULT_STATUS_SUCCESS_STR);
									resultMap.put(ConstantUtil.RESULT_MSG_KEY, ConstantUtil.RESULT_MSG_LOGIN_SUCCESS);
									resultMap.put(ConstantUtil.RESULT_DATA_KEY, userAccounts.get(0).getId());
								}else{
									loginLog.setLoginResult("账号已删除或冻结");
									//登录参数返回
									resultMap.put(ConstantUtil.RESULT_MSG_KEY, "账号被锁定，请联系客服");
								}
							}else{
								loginLog.setLoginResult("账号或密码错误");
								//登录参数返回
								resultMap.put(ConstantUtil.RESULT_MSG_KEY, "账号或密码错误");
							}
						}else{
							loginLog.setLoginResult("账号异常，账号关联记录大于1条");
							//登录参数返回
							resultMap.put(ConstantUtil.RESULT_MSG_KEY, "账号异常，请联系客服");
						}
					}else{
						loginLog.setLoginResult("账号未注册");
						resultMap.put(ConstantUtil.RESULT_MSG_KEY, "当前未注册，请先注册");
					}
					//保存登录记录
					logUserLoginDao.saveObject(loginLog);
				}else{
					resultMap.put(ConstantUtil.RESULT_MSG_KEY, "24小时内连续登录错误已达上限");
				}
			}else{
				resultMap.put(ConstantUtil.RESULT_MSG_KEY, "密码不能为空");
			}
		}else{
			resultMap.put(ConstantUtil.RESULT_MSG_KEY, "账号不能为空");
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> loginMobile(Map<String, String> requestMap) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @Description 获得用户一天内连续登录错误的次数
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年8月4日 下午8:37:55
	 */
	private Integer isMaxLoginErrorCount(final String account){
		Integer errorCount = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String hql = "WHERE loginAccount = '" + account + "' AND state = 2 AND loginTime > '" + sdf.format(new Date()) + "' ORDER BY id ASC";
		List<LogUserLogin> logUserLogins = logUserLoginDao.findObjects(hql);
		if(null != logUserLogins && 0 < logUserLogins.size()){
			for(LogUserLogin item : logUserLogins){
				if(2 == item.getLoginState()){
					errorCount = 0;
				}else{
					errorCount++;
				}
			}
		}
		return errorCount;
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

	/**
	 * 查询用户信息
	 */
	public Map<String, Object> getUserInfo(final Integer userId){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT a.id AS userId, ")
			.append("a.account AS account, ")
			.append("CONCAT('" + ConstantUtil.ALI_OSS_VISIT_IMG_PREFIX + "', COALESCE(a.avatar, '" + ConstantUtil.USER_AVATAR_DEFAULT + "'), '" + ConstantUtil.USER_HEAD_IMAGE_SUFFIX + "') AS avatar, ")
			.append("a.state AS state, ")
			.append("a.gmtCreate AS gmtCreate ");
		sb.append("WHERE a.id = " + userId);
		List<Map<String, Object>> userListMap = userAccountDao.findListMapByHql2(sb.toString());
		if(null != userListMap && 1 <= userListMap.size()){
			String account = (String) userListMap.get(0).get("account");
			if(null != account){
				userListMap.get(0).put("account", account.substring(0, 3) + "****" + account.substring(account.length()-4));
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Object dateTime = userListMap.get(0).get("gmtCreate");
			if(null != dateTime){
				userListMap.get(0).put("gmtCreate", sdf.format(dateTime));
			}
			return userListMap.get(0);
		}else{
			return null;
		}
	}

	public void setUserAccountDao(IUserAccountDao userAccountDao) {
		this.userAccountDao = userAccountDao;
	}

	public void setLogUserLoginDao(ILogUserLoginDao logUserLoginDao) {
		this.logUserLoginDao = logUserLoginDao;
	}
	
}
