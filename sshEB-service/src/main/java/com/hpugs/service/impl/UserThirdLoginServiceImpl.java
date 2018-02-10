package com.hpugs.service.impl;

import java.util.Date;
import java.util.Map;

import com.hpugs.commons.util.ConstantUtil;
import com.hpugs.commons.util.Utils;
import com.hpugs.dao.IUserThirdLoginDao;
import com.hpugs.entity.po.UserThirdLogin;
import com.hpugs.service.IUserThirdLoginService;

/**
 * @Description 第三方授权登录
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2018年2月10日 下午3:31:26
 */
public class UserThirdLoginServiceImpl implements IUserThirdLoginService {
	
	private IUserThirdLoginDao userThirdLoginDao;

	@Override
	public Map<String, Object> saveOrUpdateOauthInfo(Map<String, String> requestParams) {
		//返会操作结果对象
		Map<String, Object> resultMap = Utils.createResultMap();
		
		//获取传入参数
		String thirdType = requestParams.get("thirdType");
		String thirdOpenId = requestParams.get("thirdOpenId");
		String thirdAvatar = requestParams.get("thirdAvatar");
		String thirdNickName = requestParams.get("thirdNickName");
		String thirdGender = requestParams.get("thirdGender");
		String thirdCountry = requestParams.get("thirdCountry");
		String thirdProvince = requestParams.get("thirdProvince");
		String thirdCity = requestParams.get("thirdCity");
		
		if(Utils.stringIsNotEmpty(thirdType) && Utils.stringIsNotEmpty(thirdOpenId)){
			UserThirdLogin userThirdLogin = userThirdLoginDao.get("WHERE type = '" + thirdType + "' AND openId = '" + thirdOpenId + "' ");
			if(null == userThirdLogin){//判断第三方账号是否保存过
				userThirdLogin = new UserThirdLogin();
				userThirdLogin.setType(Integer.parseInt(thirdType));
				userThirdLogin.setOpenId(thirdOpenId);
				userThirdLogin.setGmtCreate(new Date());
			}
			userThirdLogin.setAvatar(thirdAvatar);
			userThirdLogin.setNickName(thirdNickName);
			if(Utils.stringIsNotEmpty(thirdGender) && "女".equals(thirdGender) && "f".equals(thirdGender) && "0".equals(thirdGender)){
				userThirdLogin.setGender("1");
			}else{
				userThirdLogin.setGender("2");
			}
			userThirdLogin.setCountry(thirdCountry);
			userThirdLogin.setProvince(thirdProvince);
			userThirdLogin.setCity(thirdCity);
			userThirdLogin.setGmtModified(new Date());
			if(null == userThirdLogin.getUserAccountId()){
				if(null != userThirdLoginDao.saveOrUpdateObject(userThirdLogin)){
					resultMap.put(ConstantUtil.RESULT_STATUS_KEY, ConstantUtil.RESULT_STATUS_SUCCESS_STR);
					resultMap.put(ConstantUtil.RESULT_MSG_KEY, ConstantUtil.RESULT_MSG_SUCCESS);
				}
			}else{
				Map<String, Object> dataMap = (Map<String, Object>) resultMap.get(ConstantUtil.RESULT_DATA_KEY);
				dataMap.put("userId", userThirdLogin.getUserAccountId());
			}
		}
		
		return resultMap;
	}
	
	public void setUserThirdLoginDao(IUserThirdLoginDao userThirdLoginDao) {
		this.userThirdLoginDao = userThirdLoginDao;
	}

}
