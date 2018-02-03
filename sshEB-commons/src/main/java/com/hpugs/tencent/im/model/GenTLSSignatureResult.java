package com.hpugs.tencent.im.model;

/**
 * @Description 类描述
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2017年8月29日 下午3:30:31
 */
public class GenTLSSignatureResult {
	public String errMessage;
	public String urlSig;
	public int expireTime;
	public int initTime;
	public GenTLSSignatureResult()
	{
		errMessage = "";
		urlSig = "";
	}
}
