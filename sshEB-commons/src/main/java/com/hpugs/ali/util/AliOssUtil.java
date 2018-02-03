package com.hpugs.ali.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CopyObjectResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.SimplifiedObjectMeta;
import com.hpugs.commons.util.ConstantUtil;

/**
 * @Description 阿里OSS管理
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2017年9月22日 下午3:17:28
 */
public class AliOssUtil {
	
	//声明全局OSS连接
	private static OSSClient ossClient = null;
	
	// 创建OSSClient实例
	static{
		if(null == ossClient){
			ossClient = new OSSClient(ConstantUtil.endpoint_OSS, ConstantUtil.accessKeyId_OSS, ConstantUtil.accessKeySecret_OSS);
		}
	}
	
	/**
	 * @Description 上传String类型格式的文件
	 * @param bucketName
	 * @param key
	 * @param content
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年9月22日 下午4:01:55
	 */
	public static boolean postStringOss(String bucketName, String key, String content){
		boolean flag = false;
		if(null != bucketName && 0 < bucketName.length()){
			if(null != content){
				key = key != null ? key : UUID.randomUUID().toString();
				PutObjectResult putObjectResult = ossClient.putObject(bucketName, key, new ByteArrayInputStream(content.getBytes()));
				if(null != putObjectResult){
					flag = true;
				}
			}
		}
		return flag;
	}
	
	/**
	 * @Description 上传byte格式数据
	 * @param bucketName oss上bucket
	 * @param key 文件在oss上的路径和文件名
	 * @param bytes 文件byte格式数据
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年9月22日 下午3:58:56
	 */
	public static boolean postByteOss(String bucketName, String key, byte[] bytes){
		boolean flag = false;
		if(null != bucketName && 0 < bucketName.length()){
			if(null != bytes){
				key = key != null ? key : UUID.randomUUID().toString();
				PutObjectResult putObjectResult = ossClient.putObject(bucketName, key, new ByteArrayInputStream(bytes));
				if(null != putObjectResult){
					flag = true;
				}
			}
		}
		return flag;
	}
	
	/**
	 * @Description 网络流
	 * @param bucketName oss上bucket
	 * @param key 文件在oss上的路径和文件名
	 * @param urlPath 网络路径地址
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @date 创建时间：2017年9月22日 下午3:58:56
	 */
	public static boolean postUrlOss(String bucketName, String key, String urlPath) throws MalformedURLException, IOException{
		boolean flag = false;
		if(null != bucketName && 0 < bucketName.length()){
			if(null != urlPath && (urlPath.indexOf("http://") == 0 || urlPath.indexOf("https://") == 0)){
				InputStream inputStream = new URL(urlPath).openStream();
				key = key != null ? key : UUID.randomUUID().toString();
				PutObjectResult putObjectResult = ossClient.putObject(bucketName, key, inputStream);
				if(null != putObjectResult){
					flag = true;
				}
			}
		}
		return flag;
	}
	
	/**
	 * @Description 文件流上传
	 * @param bucketName oss上bucket
	 * @param key 文件在oss上的路径和文件名
	 * @param filePath 文件路径地址
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @date 创建时间：2017年9月22日 下午3:58:56
	 */
	public static boolean postFileInputStreamOss(String bucketName, String key, InputStream fileInputStream) throws MalformedURLException, IOException{
		boolean flag = false;
		if(null != bucketName && 0 < bucketName.length()){
			if(null != fileInputStream){
				key = key != null ? key : UUID.randomUUID().toString();
				PutObjectResult putObjectResult = ossClient.putObject(bucketName, key, fileInputStream);
				if(null != putObjectResult){
					flag = true;
				}
			}
		}
		return flag;
	}
	
	/**
	 * @Description 本地文件流上传
	 * @param bucketName oss上bucket
	 * @param key 文件在oss上的路径和文件名
	 * @param filePath 文件路径地址
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @date 创建时间：2017年9月22日 下午3:58:56
	 */
	public static boolean postFileOss(String bucketName, String key, String filePath) throws MalformedURLException, IOException{
		boolean flag = false;
		if(null != bucketName && 0 < bucketName.length()){
			if(null != filePath){
				File file = new File(filePath);
				if(file.isFile()){
					key = key != null ? key : UUID.randomUUID().toString();
					PutObjectResult putObjectResult = ossClient.putObject(bucketName, key, file);
					if(null != putObjectResult){
						flag = true;
					}
				}
			}
		}
		return flag;
	}
	
	/**
	 * @Description 判断文件是否存在
	 * @param key
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年11月10日 上午10:20:41
	 */
	public static boolean findFileOssByKey(String bucketName, String key){
		boolean flag = false;
		if(null != bucketName && 0 < bucketName.length()){
			flag = ossClient.doesObjectExist(bucketName, key);
		}
		return flag;
	}
	
	/**
	 * @Description 删除文件
	 * @param key
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年11月10日 上午10:23:09
	 */
	public static boolean deleteFileOssByKey(String bucketName, String key){
		boolean flag = false;
		if(null != bucketName && 0 < bucketName.length()){
			ossClient.deleteObject(bucketName, key);
			flag = true;
		}
		return flag;
	}
	
	/**
	 * @Description 简单拷贝
	 * @param bucketName
	 * @param key
	 * @param destBucketName
	 * @param destKey
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年12月28日 下午2:10:07
	 */
	public static boolean copyObject(String bucketName, String key, String destBucketName, String destKey){
		boolean flag = false;
		if(findFileOssByKey(bucketName, key)){
			// 拷贝Object
			CopyObjectResult result = ossClient.copyObject(bucketName, key, destBucketName, destKey);
			if(null != result){
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * @Description 获取文件的部分元信息
	 * @param key
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年11月10日 上午11:55:17
	 */
	public static SimplifiedObjectMeta getOssFileSimplifiedObjectMetaByKey(String bucketName, String key){
		SimplifiedObjectMeta simplifiedObjectMeta = null;
		if(null != bucketName && 0 < bucketName.length()){
			simplifiedObjectMeta = ossClient.getSimplifiedObjectMeta(bucketName, key);
		}
		return simplifiedObjectMeta;
	}
	
	/**
	 * @Description 获取文件的全部元信息
	 * @param key
	 * @return
	 *
	 * @author 高尚
	 * @version 1.0
	 * @date 创建时间：2017年11月10日 上午11:56:41
	 */
	public static ObjectMetadata  getOssFileObjectMetadataByKey(String bucketName, String key){
		ObjectMetadata objectMetadata = null;
		if(null != bucketName && 0 < bucketName.length()){
			objectMetadata = ossClient.getObjectMetadata(bucketName, key);
		}
		return objectMetadata;
	}
	
	// 关闭client
	public static void close(){
		if(null != ossClient){
			ossClient.shutdown();
			ossClient = null;
		}
	}
}
