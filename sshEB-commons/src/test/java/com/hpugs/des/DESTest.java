package com.hpugs.des;

import org.junit.Test;

import com.hpugs.commons.util.DESUtil;

/**
 * @Description 加解密测试方法
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2017年8月8日 下午1:20:41
 */
public class DESTest{
	
	/**
	 * @Description 加密
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年8月8日 下午1:38:43
	 */
	@Test
	public void DESEncryptTest(){
		System.out.println(DESUtil.encrypt("abc123"));
	}
	
	/**
	 * @Description 解密
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2018年1月29日 下午3:46:59
	 */
	@Test
	public void DESDecryptTest(){
		System.out.println(DESUtil.decrypt("06C5BFBEEB62A295"));
	}

}
