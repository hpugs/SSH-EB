package com.hpugs.tencent.im.model;

/**
 * @Description 类描述
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2017年8月29日 下午3:30:43
 */
public class CheckTLSSignatureResult {
	public String errMessage;
	public boolean verifyResult;
	public int expireTime;
	public int initTime;
	public CheckTLSSignatureResult()
	{
		errMessage = "";
		verifyResult = false;
	}
}
