package com.security.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.SequenceInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	private static Logger log = LoggerFactory.getLogger(FileUtil.class);

	private static String savePath;
	
	static{
		InputStream in = FileUploaderUtils.class.getClassLoader()  
                .getResourceAsStream("config.properties");
		Properties prop = new  Properties(); 
		try {
			prop.load(in);
			savePath = prop.getProperty("FILE_SAVE_PATH");
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 图片保存操作
	 * 
	 * @return 返回图片保存相对路径
	 */
	public static final Map<String,String> saveImage(MultipartFile multipartFile, String modularName) {
		String fileName = null;
		Map<String,String> map=new HashMap<String,String>();
	
		try {
            // 文件保存路径  
			String dirname = null;
			if(modularName == null || "".equals(modularName.trim())){
				dirname="/image";
			} else {
				dirname="/image/" + modularName;
			}
            String filePath = savePath + dirname + "/";  
            File file = new File(filePath);
    		if (!file.exists()) {
    			file.mkdirs();
    		}
            String fileSuffix= getSuffixByFilename(multipartFile.getOriginalFilename()).toUpperCase();
            String uploadName=UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uploadName+ fileSuffix;
            // 转存文件  
            multipartFile.transferTo(new File(filePath + fileName));
            
            fileName = dirname + "/" + fileName ;
            map.put("fileName", fileName);
            map.put("uploadName", uploadName+ fileSuffix);
            
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return map;
    }
	/**
     * 根据给定的文件名,获取其后缀信息
     * 
     * @param filename
     * @return
     */
    private static String getSuffixByFilename(String filename){
        return filename.substring(filename.lastIndexOf(".")).toLowerCase();
    }
	

	public static void deleteFile(HttpServletRequest request, List<String> filePath) {
		String realPath = request.getSession().getServletContext().getRealPath(File.separator);
		realPath = realPath.substring(0, realPath.length() - 1);
		int aString = realPath.lastIndexOf(File.separator);
		realPath = realPath.substring(0, aString);

		if (filePath != null && !filePath.isEmpty()) {
			for (String path : filePath) {
				File file = new File(realPath + path);
				System.out.println("path==" + realPath + path);
				if (file.exists()) {
					file.delete();
					System.out.println("成功删除文件");
				}
			}
		}

	}

	/**
	 * @Description: 取得tomcat中的webapps目录 如： /home/gzxiaoi/apache-tomcat-8.0.45/webapps
	 * @param request
	 * @return
	 */
	public static String getRealPath(HttpServletRequest request) {
		/*String realPath = request.getSession().getServletContext().getRealPath(File.separator);
		realPath = realPath.substring(0, realPath.length() - 1);
		int aString = realPath.lastIndexOf(File.separator);
		realPath = realPath.substring(0, aString);*/
		return savePath;
	}

	public static boolean saveFile(String savePath, String fileFullName, MultipartFile file, HttpServletRequest request)
			throws Exception {
		File uploadDirectory = new File(savePath);
		byte[] data = readInputStream(file.getInputStream());
		// new一个文件对象用来保存图片，默认保存当前工程根目录
		File uploadFile = new File(savePath + fileFullName);
		// 判断文件夹是否存在，不存在就创建一个
		File fileDirectory = new File(savePath);
		synchronized (uploadDirectory) {
			if (!uploadDirectory.exists()) {
				if (!uploadDirectory.mkdir()) {
					throw new Exception("保存文件的父文件夹创建失败！路径为：" + savePath);
				}
			}
			if (!fileDirectory.exists()) {
				if (!fileDirectory.mkdir()) {
					throw new Exception("文件夹创建失败！路径为：" + savePath);
				}
			}
		}

		// 创建输出流
		try (FileOutputStream outStream = new FileOutputStream(uploadFile)) {// 写入数据
			outStream.write(data);
			outStream.flush();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return uploadFile.exists();
	}

	private static byte[] readInputStream(InputStream inStream) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len;
		// 使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 关闭输入流
		inStream.close();
		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	}

	private final static List<UploadInfo> uploadInfoList = new ArrayList<>();

	public static String Uploaded(String md5, String guid, String chunk, String chunks, String uploadFolderPath,
			String fileName, String ext, HttpServletRequest request) throws Exception {
		synchronized (uploadInfoList) {
			if ((md5!=null&&!md5.equals(""))&&(chunks!=null&&!chunks.equals(""))&&!isNotExist(md5,chunk)) {
				uploadInfoList.add(new UploadInfo(md5, chunks, chunk, uploadFolderPath, fileName, ext));
			}
		}
		boolean allUploaded = isAllUploaded(md5, chunks);
		//boolean allUploaded = true;
		int chunksNumber = Integer.parseInt(chunks);
		String mergeName=null;
		if (allUploaded) {
			mergeName=mergeFile(chunksNumber, ext, guid, uploadFolderPath, request);
			// fileService.save(new
			// com.zhangzhihao.FileUpload.Java.Model.File(guid + ext, md5, new
			// Date()));
			
		}
		return mergeName;
	}

	//判断在uploadInfoList是否有存在MD5和chunk都相同的元素
	private static boolean isNotExist(final String md5, final String chunk) {
		boolean flag =false;
		for (UploadInfo uploadInfo : uploadInfoList) {
			if (uploadInfo.getChunk().equals(chunk)&&uploadInfo.getMd5().equals(md5)) {
				//若md5和chunk都相同，则认为两条记录相同，返回true
				flag=true;
			}
		}
		return flag;
	}

	private static boolean isAllUploaded(final String md5, String chunks) {
		int size = uploadInfoList.stream().filter(new Predicate<UploadInfo>() {
			@Override
			public boolean test(UploadInfo item) {
				return item.getMd5().equals(md5);
			}
		}).distinct().collect(Collectors.toList()).size();
		boolean bool = (size == Integer.parseInt(chunks));
		if (bool) {
			synchronized (uploadInfoList) {
				uploadInfoList.removeIf(new Predicate<UploadInfo>() {
					@Override
					public boolean test(UploadInfo item) {
						return Objects.equals(item.getMd5(), md5);
					}
				});
			}
		}
		return bool;
	}

	@SuppressWarnings("resource")
	private static String mergeFile(int chunksNumber, String ext, String guid, String uploadFolderPath,
			HttpServletRequest request) {
		/* 合并输入流 */
		String mergePath = uploadFolderPath;

		String destPath = getRealPath(null) +"/image/bigFile/";// 文件路径
		String newName = System.currentTimeMillis() + ext;// 文件新名称
		SequenceInputStream s;
		InputStream s1;
		try {
			s1 = new FileInputStream(mergePath + 0 + ext);
			String tempFilePath;
			InputStream s2 = new FileInputStream(mergePath + 1 + ext);
			s = new SequenceInputStream(s1, s2);
			for (int i = 2; i < chunksNumber; i++) {
				tempFilePath = mergePath + i + ext;
				InputStream s3 = new FileInputStream(tempFilePath);
				s = new SequenceInputStream(s, s3);
			}
			// 通过输出流向文件写入数据(转移正式文件到目标目录)
			// uploadFolderPath + guid + ext
			saveStreamToFile(s, destPath, newName);
			// 删除保存分块文件的文件夹
			deleteFolder(mergePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return newName;
	}

	private static boolean deleteFolder(String mergePath) {
		File dir = new File(mergePath);
		File[] files = dir.listFiles();
		if (files != null) {
			for (File file : files) {
				try {
					file.delete();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return dir.delete();
	}

	private static void saveStreamToFile(SequenceInputStream inputStream, String filePath, String newName)
			throws Exception {
		File fileDirectory = new File(filePath);
		synchronized (fileDirectory) {
			if (!fileDirectory.exists()) {
				if (!fileDirectory.mkdir()) {
					throw new Exception("保存文件的父文件夹创建失败！路径为：" + fileDirectory);
				}
			}
			if (!fileDirectory.exists()) {
				if (!fileDirectory.mkdir()) {
					throw new Exception("文件夹创建失败！路径为：" + fileDirectory);
				}
			}
		}
		/* 创建输出流，写入数据，合并分块 */
		OutputStream outputStream = new FileOutputStream(filePath + newName);
		byte[] buffer = new byte[1024];
		int len = 0;
		try {
			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
				outputStream.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);

			throw e;
		} finally {
			outputStream.close();
			inputStream.close();
		}
	}
	
	/**
	 * @Description: 分片文件追加
	 * @param request
	 * @param sliceFile  分片文件
	 * @param name   文件名称
	 * @param dirType  文件夹类型 如video/audio
	 * @param fileExt  文件扩展名 如.mp4/.avi  ./mp3
	 * @return
	 */
	public static String randomWrite(HttpServletRequest request, byte[] sliceFile, String name, String dirType,String fileExt) {
		try {
			
			/** 以读写的方式建立一个RandomAccessFile对象 **/
			 //获取相对路径/home/gzxiaoi/apache-tomcat-8.0.45/webapps
			String realPath=getRealPath(request); 
			//拼接文件保存路径 /fileDate/video/2017/08/09  如果没有该文件夹，则创建
			String savePath=getSavePath(realPath,dirType);			
			// String realName = UUID.randomUUID().toString().replace("-", "");
			String realName = name;
			String saveFile =realPath+ savePath + realName+fileExt;
			RandomAccessFile raf = new RandomAccessFile(saveFile, "rw");
			// 将记录指针移动到文件最后
			raf.seek(raf.length());
			raf.write(sliceFile);
			return savePath;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * @Description: 获取文件保存的路径，如果没有该目录，则创建
	 * @param realPath 相对路径 ，如   /home/gzxiaoi/apache-tomcat-8.0.45/webapps
	 * @param fileType  文件类型 如： images/video/audio用于拼接文件保存路径，区分音视频
	 * @return
	 */
	public static String getSavePath(String realPath, String fileType) {
		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		SimpleDateFormat m = new SimpleDateFormat("MM");
		SimpleDateFormat d = new SimpleDateFormat("dd");
		Date date = new Date();
		String sp=File.separator + "fileDate" + File.separator +fileType + File.separator + year.format(date) + File.separator
				+ m.format(date) + File.separator + d.format(date) + File.separator;
		String savePath = realPath+ sp;
		File folder = new File(savePath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		return sp;
	}
	
	public static String extTrans(String ext) {
		String sp=null;
		ext=ext.substring(1, ext.length());
		String imgExt = "jpg|jpeg|png|bmp|GIF|JPG|PNG|JPEG";
		String vidExt="mp4|rmvb|flv|mpeg|avi|wmv";
		String textExt="txt|doc|docx";
        if(imgExt .contains(ext)){
        	sp="image";
        }else if(vidExt.contains(ext)){
        	sp="video";
        }else if(textExt.contains(ext)){
        	sp="word";
        }else if(ext.indexOf("ppt") != -1){
        	sp="ppt";
        }else if(ext.indexOf("xls") != -1){
        	sp="xls";
        }else if(ext.indexOf("pdf") != -1){
        	sp="pdf";
        }else{
        	sp="other";
        }
		return sp;
	}

}
