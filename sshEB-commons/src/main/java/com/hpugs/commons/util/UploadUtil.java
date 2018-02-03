package com.hpugs.commons.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.hpugs.ali.util.AliOssUtil;

import net.coobird.thumbnailator.Thumbnails;

/**
 * @Description 文件相关工具类
 * @author 唐鹏鹏
 * @version 1.0
 * @date 创建时间：2017年8月29日 下午2:06:51
 */
public class UploadUtil {

	/**
	 * 文件上传
	 * 
	 * @param upload
	 * @param uploadFileName
	 * @return
	 * @author 唐鹏鹏
	 * @version 1.0
	 * @date 创建时间：2017年9月27日 上午10:31:37
	 */
	public static String uploadFile(File upload, String uploadFileName) {
		// 保存到磁盘
		uploadFileName = UUID.randomUUID().toString() + uploadFileName.substring(uploadFileName.lastIndexOf("."));
		// 服务器文件路径
		File ofile = new File(ConstantUtil.UPLOAD_LOCAL_PATH);
		// 数据库保存路径,即文件访问路径
		String url = ConstantUtil.UPLOAD_NETWORK_PATH + uploadFileName;

		// 判断ofile是否存在，不存在就创建出一个文件目录
		if (!ofile.exists()) {
			ofile.mkdirs();
		}
		File ifile = new File(ofile, uploadFileName);
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(upload);
			outputStream = new FileOutputStream(ifile);
			byte[] data = new byte[1024];
			int length = 0;
			while ((length = inputStream.read(data)) != -1) {
				outputStream.write(data, 0, length);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return url;
	}

	/**
	 * 文件上传并压缩（固定宽高等比压缩）
	 * 
	 * @param upload
	 * @param uploadFileName
	 * @return
	 * @throws IOException
	 * @author 唐鹏鹏
	 * @version 1.0
	 * @date 创建时间：2017年9月27日 下午3:37:56
	 */
	public static List<String> uploadImage(File upload, String uploadFileName) throws IOException {
		// 保存原图，pc缩略图文件路径（顺序为原图>pc缩略图）
		List<String> urlList = new ArrayList<String>();
		// 生成uuid
		String uuid = UUID.randomUUID().toString();
		// 文件后缀名
		String suffix = uploadFileName.substring(uploadFileName.lastIndexOf("."));
		// 保存到磁盘原图文件名
		uploadFileName = uuid + suffix;
		// pc缩略图文件名
		String uploadFileThumName = uuid + ConstantUtil.THUM_PC_NAME + suffix;
		// 获取当前时间的年月日
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = "";
		if ((calendar.get(Calendar.MONTH) + 1) < 10) {
			month = "0" + (calendar.get(Calendar.MONTH) + 1);
		} else {
			month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		}
		String day = String.valueOf(calendar.get(Calendar.DATE));
		// 服务器文件路径
		String serverPath = ConstantUtil.UPLOAD_LOCAL_PATH + "/" + year + "/" + month + "/" + day;
		// 数据库保存路径,即原图文件访问路径
		String url = ConstantUtil.UPLOAD_NETWORK_PATH + "/" + year + "/" + month + "/" + day + "/" + uploadFileName;
		// 数据库保存路径,即pc缩略图文件访问路径
		String urlThum = ConstantUtil.UPLOAD_NETWORK_PATH + "/" + year + "/" + month + "/" + day + "/"
				+ uploadFileThumName;
		urlList.add(url);

		// 获取当前文件的宽高
		BufferedImage image = ImageIO.read(upload);
		int width = image.getWidth();
		int height = image.getHeight();

		File ofile = new File(serverPath);
		// 判断ofile是否存在，不存在就创建出一个文件目录
		if (!ofile.exists()) {
			ofile.mkdirs();
		}
		File ifile = new File(ofile, uploadFileName);
		File thumfile = new File(ofile, uploadFileThumName);
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(upload);
			outputStream = new FileOutputStream(ifile);
			byte[] data = new byte[1024];
			int length = 0;
			while ((length = inputStream.read(data)) != -1) {
				outputStream.write(data, 0, length);
			}
			if (width > ConstantUtil.IMAGE_THUM_WIDTH || height > ConstantUtil.IMAGE_THUM_HEIGHT) {
				Thumbnails.of(upload).size(ConstantUtil.IMAGE_THUM_WIDTH, ConstantUtil.IMAGE_THUM_HEIGHT)
						.toFile(thumfile);
				urlList.add(urlThum);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return urlList;
	}

	/**
	 * 文件上传并压缩（等比例压缩）
	 * 
	 * @param upload
	 * @param uploadFileName
	 * @return
	 * @throws IOException
	 * @author 唐鹏鹏
	 * @version 1.0
	 * @date 创建时间：2017年9月27日 下午3:37:56
	 */
	public static List<String> uploadImageOfScale(File upload, String uploadFileName) throws IOException {
		// 保存原图，pc缩略图文件路径（顺序为原图>pc缩略图）
		List<String> urlList = new ArrayList<String>();
		// 生成uuid
		String uuid = UUID.randomUUID().toString();
		// 文件后缀名
		String suffix = uploadFileName.substring(uploadFileName.lastIndexOf("."));
		String suffix1 = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
		// 保存到磁盘原图文件名
		uploadFileName = uuid + suffix;
		// pc缩略图文件名
		String uploadFileThumName = uuid + ConstantUtil.THUM_PC_NAME + suffix;
		// 获取当前时间的年月日
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = "";
		if ((calendar.get(Calendar.MONTH) + 1) < 10) {
			month = "0" + (calendar.get(Calendar.MONTH) + 1);
		} else {
			month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		}
		String day = String.valueOf(calendar.get(Calendar.DATE));
		// 服务器文件路径
		String serverPath = ConstantUtil.UPLOAD_LOCAL_PATH + "/" + year + "/" + month + "/" + day;
		// 数据库保存路径,即原图文件访问路径
		String url = ConstantUtil.UPLOAD_NETWORK_PATH + "/" + year + "/" + month + "/" + day + "/" + uploadFileName;
		// 数据库保存路径,即pc缩略图文件访问路径
		String urlThum = ConstantUtil.UPLOAD_NETWORK_PATH + "/" + year + "/" + month + "/" + day + "/"
				+ uploadFileThumName;
		urlList.add(url);

		File ofile = new File(serverPath);
		// 判断ofile是否存在，不存在就创建出一个文件目录
		if (!ofile.exists()) {
			ofile.mkdirs();
		}
		File ifile = new File(ofile, uploadFileName);
		File thumfile = new File(ofile, uploadFileThumName);
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(upload);
			outputStream = new FileOutputStream(ifile);
			byte[] data = new byte[1024];
			int length = 0;
			while ((length = inputStream.read(data)) != -1) {
				outputStream.write(data, 0, length);
			}
			Thumbnails.of(upload).scale(0.6f).outputFormat(suffix1).toFile(thumfile);
			urlList.add(urlThum);
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return urlList;
	}

	/**
	 * 文件上传并压缩（质量压缩）
	 * 
	 * @param upload
	 * @param uploadFileName
	 * @return
	 * @throws IOException
	 * @author 唐鹏鹏
	 * @version 1.0
	 * @date 创建时间：2017年9月27日 下午3:37:56
	 */
	public static List<String> uploadImageOfSize(File upload, String uploadFileName) throws IOException {
		// 保存原图，pc缩略图文件路径（顺序为原图>pc缩略图）
		List<String> urlList = new ArrayList<String>();
		// 生成uuid
		String uuid = UUID.randomUUID().toString();
		// 文件后缀名
		String suffix = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
		// 保存到磁盘原图文件名
		uploadFileName = uuid + "." + suffix;
		// pc缩略图文件名
		String uploadFileThumName = uuid + ConstantUtil.THUM_PC_NAME + suffix;
		// 获取当前时间的年月日
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = "";
		if ((calendar.get(Calendar.MONTH) + 1) < 10) {
			month = "0" + (calendar.get(Calendar.MONTH) + 1);
		} else {
			month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		}
		String day = String.valueOf(calendar.get(Calendar.DATE));
		// 服务器文件路径
		String serverPath = ConstantUtil.UPLOAD_LOCAL_PATH + "/" + year + "/" + month + "/" + day;
		// 数据库保存路径,即原图文件访问路径
		String url = ConstantUtil.UPLOAD_NETWORK_PATH + "/" + year + "/" + month + "/" + day + "/" + uploadFileName;
		// 数据库保存路径,即pc缩略图文件访问路径
		String urlThum = ConstantUtil.UPLOAD_NETWORK_PATH + "/" + year + "/" + month + "/" + day + "/"
				+ uploadFileThumName;
		urlList.add(url);

		// 获取当前文件的宽高
		/* BufferedImage image = ImageIO.read(upload); */
		/*
		 * BufferedImage image = ImageIO.read(upload); int width =
		 * image.getWidth(); int height = image.getHeight();
		 */

		File ofile = new File(serverPath);
		// 判断ofile是否存在，不存在就创建出一个文件目录
		if (!ofile.exists()) {
			ofile.mkdirs();
		}
		double scale = 1.0d;
		long size = upload.length();
		if (size >= 200 * 1024) {
			scale = (200 * 1024f) / size;
		}
		scale = 0.5d;
		File ifile = new File(ofile, uploadFileName);
		File thumfile = new File(ofile, uploadFileThumName);
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(upload);
			outputStream = new FileOutputStream(ifile);
			byte[] data = new byte[1024];
			int length = 0;
			while ((length = inputStream.read(data)) != -1) {
				outputStream.write(data, 0, length);
			}
			/*
			 * if(width > ConstantUtil.IMAGE_THUM_WIDTH || height >
			 * ConstantUtil.IMAGE_THUM_HEIGHT){
			 */
			/*
			 * Thumbnails.of(upload).size(ConstantUtil.IMAGE_THUM_WIDTH,
			 * ConstantUtil.IMAGE_THUM_HEIGHT).toFile(thumfile);
			 */
			Thumbnails.of(upload).scale(1f).outputQuality(scale).outputFormat(suffix).toFile(thumfile);
			urlList.add(urlThum);
			/* } */
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return urlList;
	}

	/**
	 * 文件上传并压缩（等比例压缩）
	 * 
	 * @param upload
	 * @param uploadFileName
	 * @return
	 * @throws IOException
	 * @author 唐鹏鹏
	 * @version 1.0
	 * @param scale2
	 * @param scale1
	 * @param path
	 * @date 创建时间：2017年9月27日 下午3:37:56
	 */
	public static String uploadImageOfScheme(File upload, String uploadFileName, String path, float scale1,
			float scale2) throws IOException {
		// 保存原图，pc缩略图文件路径（顺序为原图>pc缩略图）
		String suffix = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
		// 服务器文件路径
		String serverPath = ConstantUtil.UPLOAD_LOCAL_PATH + "/单品/" + path + "/100-200";
		String serverPath1 = ConstantUtil.UPLOAD_LOCAL_PATH + "/单品/" + path + "/50以内";
		// 数据库保存路径,即pc缩略图文件访问路径
		// String urlThum = ConstantUtil.UPLOAD_NETWORK_PATH + "/单品/6/100-200" +
		// uploadFileName;
		File ofile = new File(serverPath);
		File ofile1 = new File(serverPath1);

		// 判断ofile是否存在，不存在就创建出一个文件目录
		if (!ofile.exists()) {
			ofile.mkdirs();
		}
		if (!ofile1.exists()) {
			ofile1.mkdirs();
		}
		File thumfile = new File(ofile, uploadFileName);
		File thumfile1 = new File(ofile1, uploadFileName);
		try {
			Thumbnails.of(upload).scale(scale1).outputFormat(suffix).toFile(thumfile);
			Thumbnails.of(upload).scale(scale2).outputFormat(suffix).toFile(thumfile1);
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
		return "success";
	}

	/**
	 * 
	 * @Description 将图片保存到Oss上
	 * @param directoryKey
	 *            保存的基准位置:在ConstantUtil.IMAGE_PATH_MAP中找
	 * @param upload
	 *            上传的图片
	 * @param uploadFileName
	 *            上传图片的原始名字
	 * @return 成功返回 保存的url 失败返回null
	 *
	 * @author 付雄
	 * @version 1.0
	 * @date 创建时间：2017年11月2日 下午2:20:20
	 */
	public static String uploadImageByOss(String directoryKey, File upload, String uploadFileName) {
		// 生成uuid
		String uuid = UUID.randomUUID().toString();
		// 文件后缀名
		String suffix = uploadFileName.substring(uploadFileName.lastIndexOf("."));
		// 生成的文件名
		uploadFileName = uuid + suffix;
		// 获取当前时间的年月日
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = "";
		if ((calendar.get(Calendar.MONTH) + 1) < 10) {
			month = "0" + (calendar.get(Calendar.MONTH) + 1);
		} else {
			month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		}
		String day = String.valueOf(calendar.get(Calendar.DATE));
		String dir = ConstantUtil.IMAGE_PATH_MAP.get(directoryKey);
		if (dir == null || "".equals(dir)) {
			return null;
		}
		String path = dir + year + "/" + month + "/" + day + "/" + uploadFileName;
		try {
			boolean flag = AliOssUtil.postFileInputStreamOss(ConstantUtil.ALI_OSS_BUCKET_NAME, path,
					new FileInputStream(upload));
			if (flag) {
				String url = ConstantUtil.ALI_OSS_VISIT_IMG_PREFIX + path;
				return url;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
