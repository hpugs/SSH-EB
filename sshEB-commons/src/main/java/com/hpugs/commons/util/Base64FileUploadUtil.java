package com.hpugs.commons.util;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import com.hpugs.ali.util.AliOssUtil;
import com.hpugs.email.tencent.SendEmailUtil;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @Description Base64格式的照片保存
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2017年9月18日 下午2:39:18
 */
public class Base64FileUploadUtil {
	
	private static final BASE64Encoder encoder = new BASE64Encoder();
	private static final BASE64Decoder decoder = new BASE64Decoder();
	
	public static String saveFile(String imgBase64, String filePath) {
		String fileName = filePath + ConstantUtil.ALI_OSS_FILE_PATH.format(new Date()) + UUID.randomUUID().toString();
        byte[] imageByte = null;
		try {
			imageByte = decoder.decodeBuffer(imgBase64);
		} catch (IOException e) {
			SendEmailUtil.send("Base64格式的照片保存异常", "1、Base64格式转byte数组异常；2、图片Base64详情："+ imgBase64 +"；3、异常信息：" + e.toString());
		}
		if(null != imageByte){
			 boolean flag = AliOssUtil.postByteOss(ConstantUtil.ALI_OSS_BUCKET_NAME, fileName, imageByte);
			 if(flag){
				 return ConstantUtil.ALI_OSS_VISIT_IMG_PREFIX + fileName;
			 }
		}
		return null;
	}
	
}
