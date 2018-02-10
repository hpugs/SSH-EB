package com.hpugs.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hpugs.commons.util.ConstantUtil;
import com.hpugs.commons.util.DESUtil;
import com.hpugs.commons.util.SmsCodeManager;
import com.hpugs.commons.util.Utils;
import com.hpugs.dao.ILogUserLoginDao;
import com.hpugs.dao.IUserAccountDao;
import com.hpugs.dao.IUserThirdLoginDao;
import com.hpugs.email.tencent.SendEmailUtil;
import com.hpugs.entity.po.LogUserLogin;
import com.hpugs.entity.po.UserAccount;
import com.hpugs.entity.po.UserThirdLogin;
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
	private IUserThirdLoginDao userThirdLoginDao;

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
							SendEmailUtil.send("账户密码登录", "1、账号关联多个；2、账号："+account);
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
		//返会操作结果对象
		Map<String, Object> resultMap = Utils.createResultMap();
		
		//获取传入参数
		String account = requestMap.get("account");
		String smsCode = requestMap.get("smsCode");
		String smsCodeType = requestMap.get("smsCodeType");
		
		if(Utils.stringIsNotEmpty(account) && 11 == account.length()){
			 if(Utils.stringIsNotEmpty(smsCode) && ConstantUtil.SMS_CODE_LENGTH == smsCode.length()) {
				 Map<String, String> smsCodeMap = SmsCodeManager.getSmsCodeMapById(account);
				 if(null != smsCodeMap){
					 String smsCodeRedis = smsCodeMap.get(smsCodeType);
						if(Utils.stringIsNotEmpty(smsCodeRedis)){
							String [] codes = DESUtil.decrypt(smsCodeRedis).split(":");
							long outTime = 0;
							try{
								outTime = (new Date().getTime() - ConstantUtil.SMS_CODE_DATE_SDF.parse(codes[3]).getTime()) / 1000 / 60;
							}catch (Exception e) {
								SendEmailUtil.send("手机验证码登录", "1、引起错误字段："+codes[3]+"；2、错误详情："+e.toString());
							}
							if(ConstantUtil.MAX_ERROR_SMS_CODE_DATE >= outTime){//短信验证码是否有效
								if(account.equals(codes[0]) && smsCode.equals(codes[2])){//防止获取到验证码后，修改手机号
									//查询登录信息
									String hql = "WHERE mobile = '" + account + "'";
									List<UserAccount> listUserAccount = userAccountDao.findObjects(hql);
									if(Utils.collectionIsNotEmpty(listUserAccount)){
										if(1 == listUserAccount.size()){
											if(2 == listUserAccount.get(0).getState()){
												resultMap.put(ConstantUtil.RESULT_STATUS_KEY, "您的账号已被删除，请联系满艺网客服。谢谢");
											}else if(3 == listUserAccount.get(0).getState()){
												resultMap.put(ConstantUtil.RESULT_STATUS_KEY, "您的账号已被冻结，请联系满艺网客服。谢谢");
											}else{
												//移除注册验证码
												smsCodeMap.remove(smsCodeType);
												SmsCodeManager.saveSmsCode(account, smsCodeMap);
												
												//更新返回data参数
												resultMap.put(ConstantUtil.RESULT_STATUS_KEY, ConstantUtil.RESULT_STATUS_SUCCESS_STR);
												resultMap.put(ConstantUtil.RESULT_MSG_KEY, ConstantUtil.RESULT_MSG_LOGIN_SUCCESS);
												resultMap.put(ConstantUtil.RESULT_DATA_KEY, listUserAccount.get(0).getId());
											}
										}else{
											SendEmailUtil.send("手机号验证码登录", "1、手机号关联多个账号；2、手机号："+account);
											resultMap.put(ConstantUtil.RESULT_MSG_KEY, "账户异常，请联系客服");
										}
									}else{
										resultMap.put(ConstantUtil.RESULT_MSG_KEY, "账户信息不存在");
									}
								}else{
									resultMap.put(ConstantUtil.RESULT_MSG_KEY, "短信验证码错误");
								}
							}else{
								resultMap.put(ConstantUtil.RESULT_MSG_KEY, "短信验证码已超时，请重新获取");
							}
					}else{
						resultMap.put(ConstantUtil.RESULT_MSG_KEY, "请重新获取短信验证码");
					}
				}else{
					resultMap.put(ConstantUtil.RESULT_MSG_KEY, "请获取验证码");
				}
			}else{
				resultMap.put(ConstantUtil.RESULT_MSG_KEY, "验证码非法");
			}
		}else{
			resultMap.put(ConstantUtil.RESULT_MSG_KEY, "手机号非法");
		}
		return resultMap;
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
		//返会操作结果对象
		Map<String, Object> resultMap = Utils.createResultMap();
		
		//获取传入参数
		String account = requestMap.get("account");
		String passwd = requestMap.get("passwd");
		String smsCode = requestMap.get("smsCode");
		String smsCodeType = requestMap.get("smsCodeType");
		
		if(Utils.stringIsNotEmpty(account) && 11 == account.length()){
			if(Utils.stringIsNotEmpty(passwd) && 6 <= passwd.length() && 20 >= passwd.length()){
				 if(Utils.stringIsNotEmpty(smsCode) && ConstantUtil.SMS_CODE_LENGTH == smsCode.length()) {
					 Map<String, String> smsCodeMap = SmsCodeManager.getSmsCodeMapById(account);
					 if(null != smsCodeMap){
						 String smsCodeRedis = smsCodeMap.get(smsCodeType);
							if(Utils.stringIsNotEmpty(smsCodeRedis)){
								String [] codes = DESUtil.decrypt(smsCodeRedis).split(":");
								long outTime = 0;
								try{
									outTime = (new Date().getTime() - ConstantUtil.SMS_CODE_DATE_SDF.parse(codes[3]).getTime()) / 1000 / 60;
								}catch (Exception e) {
									SendEmailUtil.send("账号注册", "1、引起错误字段："+codes[3]+"；2、错误详情："+e.toString());
								}
								if(ConstantUtil.MAX_ERROR_SMS_CODE_DATE >= outTime){//短信验证码是否有效
									if(account.equals(codes[0]) && smsCode.equals(codes[2])){//防止获取到验证码后，修改手机号
										//查询账号信息
										String hql = "WHERE account='" + account + "' OR mobile = '" + account + "' ";
										List<UserAccount> listUserAccount = userAccountDao.findObjects(hql);
										if(!Utils.collectionIsNotEmpty(listUserAccount)){
											UserAccount userAccount = new UserAccount();
											userAccount.setAccount(requestMap.get("account"));//账号
											userAccount.setMobile(requestMap.get("account"));//手机号
											userAccount.setPasswd(DESUtil.encrypt(requestMap.get("passwd")));//密码
											userAccount.setState(1);
											userAccount.setGmtCreate(new Date());//创建时间
											userAccount = userAccountDao.saveObject(userAccount);
											if(null != userAccount && null != userAccount.getId()){
												//移除注册验证码
												smsCodeMap.remove(smsCodeType);
												SmsCodeManager.saveSmsCode(account, smsCodeMap);
												
												//更新返回data参数
												resultMap.put(ConstantUtil.RESULT_STATUS_KEY, ConstantUtil.RESULT_STATUS_SUCCESS_STR);
												resultMap.put(ConstantUtil.RESULT_MSG_KEY, ConstantUtil.RESULT_MSG_SUCCESS);
											}else{
												resultMap.put(ConstantUtil.RESULT_MSG_KEY, "账号创建失败");
											}
										}else{
											resultMap.put(ConstantUtil.RESULT_MSG_KEY, "账户已注册");
										}
									}else{
										resultMap.put(ConstantUtil.RESULT_MSG_KEY, "短信验证码错误");
									}
								}else{
									resultMap.put(ConstantUtil.RESULT_MSG_KEY, "短信验证码已超时，请重新获取");
								}
						}else{
							resultMap.put(ConstantUtil.RESULT_MSG_KEY, "请重新获取短信验证码");
						}
					}else{
						resultMap.put(ConstantUtil.RESULT_MSG_KEY, "请获取验证码");
					}
				}else{
					resultMap.put(ConstantUtil.RESULT_MSG_KEY, "验证码格式非法");
				}
			}else{
				resultMap.put(ConstantUtil.RESULT_MSG_KEY, "密码格式非法");
			}
		}else{
			resultMap.put(ConstantUtil.RESULT_MSG_KEY, "手机号格式非法");
		}
		return resultMap;
	}
	
	@Override
	public Map<String, Object> updatePasswd(Map<String, String> requestMap) {
		//返会操作结果对象
		Map<String, Object> resultMap = Utils.createResultMap();
		
		//获取传入参数
		String account = requestMap.get("account");
		String passwd = requestMap.get("passwd");
		String smsCode = requestMap.get("smsCode");
		String smsCodeType = requestMap.get("smsCodeType");
		
		if(Utils.stringIsNotEmpty(account) && 11 == account.length()){
			if(Utils.stringIsNotEmpty(passwd) && 6 <= passwd.length() && 20 >= passwd.length()){
				 if(Utils.stringIsNotEmpty(smsCode) && ConstantUtil.SMS_CODE_LENGTH == smsCode.length()) {
					 Map<String, String> smsCodeMap = SmsCodeManager.getSmsCodeMapById(requestMap.get("account"));
					 if(null != smsCodeMap){
						 String smsCodeRedis = smsCodeMap.get(smsCodeType);
							if(Utils.stringIsNotEmpty(smsCodeRedis)){
								String [] codes = DESUtil.decrypt(smsCodeRedis).split(":");
								long outTime = 0;
								try{
									outTime = (new Date().getTime() - ConstantUtil.SMS_CODE_DATE_SDF.parse(codes[3]).getTime()) / 1000 / 60;
								}catch (Exception e) {
									SendEmailUtil.send("重置密码", "1、引起错误字段："+codes[3]+"；2、错误详情："+e.toString());
								}
								if(ConstantUtil.MAX_ERROR_SMS_CODE_DATE >= outTime){//短信验证码是否有效
									if(account.equals(codes[0]) && smsCode.equals(codes[2])){//防止获取到验证码后，修改手机号
										//查询登录信息
										String hql = "WHERE mobile = '" + account + "'";
										List<UserAccount> listUserAccount = userAccountDao.findObjects(hql);
										if(Utils.collectionIsNotEmpty(listUserAccount)){
											if(1 == listUserAccount.size()){
												UserAccount userAccount = listUserAccount.get(0);
												if(2 == userAccount.getState()){
													resultMap.put(ConstantUtil.RESULT_STATUS_KEY, "您的账号已被删除，请联系满艺网客服。谢谢");
												}else if(3 == listUserAccount.get(0).getState()){
													resultMap.put(ConstantUtil.RESULT_STATUS_KEY, "您的账号已被冻结，请联系满艺网客服。谢谢");
												}else{
													userAccount.setPasswd(DESUtil.encrypt(passwd));
													userAccount.setGmtModified(new Date());
													userAccount = userAccountDao.saveOrUpdateObject(userAccount);
													if(null != userAccount){
														//移除注册验证码
														smsCodeMap.remove(smsCodeType);
														SmsCodeManager.saveSmsCode(account, smsCodeMap);
														
														//更新返回data参数
														resultMap.put(ConstantUtil.RESULT_STATUS_KEY, ConstantUtil.RESULT_STATUS_SUCCESS_STR);
														resultMap.put(ConstantUtil.RESULT_MSG_KEY, ConstantUtil.RESULT_MSG_SUCCESS);
													}
												}
											}else{
												SendEmailUtil.send("修改密码", "1、手机号关联多个账号；2、手机号："+account);
												resultMap.put(ConstantUtil.RESULT_MSG_KEY, "账户异常，请联系客服");
											}
										}else{
											resultMap.put(ConstantUtil.RESULT_MSG_KEY, "账户信息不存在");
										}
									}else{
										resultMap.put(ConstantUtil.RESULT_MSG_KEY, "短信验证码错误");
									}
								}else{
									resultMap.put(ConstantUtil.RESULT_MSG_KEY, "短信验证码已超时，请重新获取");
								}
						}else{
							resultMap.put(ConstantUtil.RESULT_MSG_KEY, "请重新获取短信验证码");
						}
					}else{
						resultMap.put(ConstantUtil.RESULT_MSG_KEY, "请获取短信验证码");
					}
				}else{
					resultMap.put(ConstantUtil.RESULT_MSG_KEY, "验证码格式非法");
				}
			}else{
				resultMap.put(ConstantUtil.RESULT_MSG_KEY, "密码格式非法");
			}
		}else{
			resultMap.put(ConstantUtil.RESULT_MSG_KEY, "手机号格式非法");
		}
		return resultMap;
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
		sb.append("FROM UserAccount a ");
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
	
	@Override
	public Map<String, Object> saveThirdAccount(Map<String, String> requestParams) {
		//返会操作结果对象
		Map<String, Object> resultMap = Utils.createResultMap();
		
		//获取传入参数
		String mobile = requestParams.get("mobile");
		String passwd = requestParams.get("passwd");
		String smsCode = requestParams.get("smsCode");
		String smsCodeType = requestParams.get("smsCodeType");
		String thirdType = requestParams.get("thirdType");
		String thirdOpenId = requestParams.get("thirdOpenId");
		
		if(null != mobile && 11 == mobile.length()){
			if(null != passwd && 6 <= passwd.length() && 20 >= passwd.length()){
				if(Utils.stringIsNotEmpty(smsCode) && ConstantUtil.SMS_CODE_LENGTH == smsCode.length()) {
					 Map<String, String> smsCodeMap = SmsCodeManager.getSmsCodeMapById(mobile);
					 if(null != smsCodeMap){
						String smsCodeRedis = smsCodeMap.get(smsCodeType);
						if(Utils.stringIsNotEmpty(smsCodeRedis)){
							String [] codes = DESUtil.decrypt(smsCodeRedis).split(":");
							long outTime = 0;
							try{
								outTime = (new Date().getTime() - ConstantUtil.SMS_CODE_DATE_SDF.parse(codes[3]).getTime()) / 1000 / 60;
							}catch (Exception e) {
								SendEmailUtil.send("重置密码", "1、引起错误字段："+codes[3]+"；2、错误详情："+e.toString());
							}
							if(ConstantUtil.MAX_ERROR_SMS_CODE_DATE >= outTime){//短信验证码是否有效
								if(mobile.equals(codes[0]) && smsCode.equals(codes[2])){//防止获取到验证码后，修改手机号
									if(Utils.stringIsNotEmpty(thirdType) && Utils.stringIsNotEmpty(thirdOpenId)){
										UserThirdLogin userThirdLogin = userThirdLoginDao.get("WHERE type = '" + thirdType + "' AND openId = '" + thirdOpenId + "' ");
										if(null != userThirdLogin){
											//查询账号信息
											String hql = "WHERE account='" + mobile + "' OR mobile = '" + mobile + "' ";
											List<UserAccount> listUserAccount = userAccountDao.findObjects(hql);
											if(!Utils.collectionIsNotEmpty(listUserAccount)){
												UserAccount userAccount = new UserAccount();
												userAccount.setAccount(mobile);//账号
												userAccount.setMobile(mobile);//手机号
												userAccount.setPasswd(DESUtil.encrypt(passwd));//密码
												userAccount.setState(1);
												userAccount.setGmtCreate(new Date());//创建时间
												userAccount = userAccountDao.saveObject(userAccount);
												if(null != userAccount && null != userAccount.getId()){
													//移除注册验证码
													smsCodeMap.remove(smsCodeType);
													SmsCodeManager.saveSmsCode(mobile, smsCodeMap);
													
													//更新返回data参数
													resultMap.put(ConstantUtil.RESULT_STATUS_KEY, ConstantUtil.RESULT_STATUS_SUCCESS_STR);
													resultMap.put(ConstantUtil.RESULT_MSG_KEY, ConstantUtil.RESULT_MSG_SUCCESS);
												}else{
													resultMap.put(ConstantUtil.RESULT_MSG_KEY, "账号绑定失败");
												}
											}else{
												SendEmailUtil.send("账户绑定", "1、手机号关联多个账号；2、手机号："+mobile);
												resultMap.put(ConstantUtil.RESULT_MSG_KEY, "账户绑定异常，请联系客服");
											}
										}else{
											resultMap.put(ConstantUtil.RESULT_MSG_KEY, "绑定信息不存在，请联系客服");
										}
									}else{
										resultMap.put(ConstantUtil.RESULT_MSG_KEY, "缺少必要绑定信息");
									}
								}else{
									resultMap.put(ConstantUtil.RESULT_MSG_KEY, "请输入正确的短信验证码");
								}
							}else{
								resultMap.put(ConstantUtil.RESULT_MSG_KEY, "验证码已失效，请重新获取");
							}
						}else{
							resultMap.put(ConstantUtil.RESULT_MSG_KEY, "请重新获取短信验证码");
						}
					 }else{
						 resultMap.put(ConstantUtil.RESULT_MSG_KEY, "请获取短信验证码");
					 }
				}else{
					resultMap.put(ConstantUtil.RESULT_MSG_KEY, "验证码输入非法");
				}
			}else{
				resultMap.put(ConstantUtil.RESULT_MSG_KEY, "请输入合法的密码");
			}
		}else{
			resultMap.put(ConstantUtil.RESULT_MSG_KEY, "请输入合法的手机号");
		}
		return resultMap;
	}
	
	@Override
	public UserAccount getUserAccountById(String id) {
		return userAccountDao.getObjectById(id);
	}

	public void setUserAccountDao(IUserAccountDao userAccountDao) {
		this.userAccountDao = userAccountDao;
	}

	public void setLogUserLoginDao(ILogUserLoginDao logUserLoginDao) {
		this.logUserLoginDao = logUserLoginDao;
	}

	public void setUserThirdLoginDao(IUserThirdLoginDao userThirdLoginDao) {
		this.userThirdLoginDao = userThirdLoginDao;
	}

}
