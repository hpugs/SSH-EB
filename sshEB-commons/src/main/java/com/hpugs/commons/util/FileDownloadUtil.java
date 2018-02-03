package com.hpugs.commons.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * @Description 文件下载工具栏
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2017年10月27日 上午10:44:29
 */
public class FileDownloadUtil {
	
	public static String imageUrlDownload(String urlPath, String filePath, String fileName) throws IOException{
		FileOutputStream fileOutputStream = null;
		DataInputStream dataInputStream = null;
		try{
			if(null != urlPath){
				if(null != filePath){
					if(null != fileName){
						File file1 = new File(filePath);
						if(file1.isDirectory()){
							file1.mkdirs();
						}
						
						File file2 = new File(filePath+fileName);
						if(file2.isDirectory()){
							file2.createNewFile();
						}
						
						fileOutputStream = new FileOutputStream(file2);
						URL url = new URL(urlPath);
						dataInputStream = new DataInputStream(url.openStream());
						byte[] buffer = new byte[1024 * 4];  
		                int length;
		                while ((length = dataInputStream.read(buffer)) > 0) {  
		                    fileOutputStream.write(buffer, 0, length);  
		                }	                
		                return file2.getPath();
					}
				}
			}
		}catch (Exception e) {
			
		}finally {
			if(null != dataInputStream){
				dataInputStream.close();  
			}
			if(null != fileOutputStream){
				fileOutputStream.flush();
	            fileOutputStream.close();
			}
		}
		return null;
	}

}
