package com.security.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUploaderUtils {
	
	private static Logger log = LoggerFactory.getLogger(FileUploaderUtils.class);
	
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
	public static final String saveImage(MultipartFile multipartFile, String modularName) {
		String fileName = null;
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
            if(!fileSuffix.equals(".JPG")&&!fileSuffix.equals(".GIF")&&!fileSuffix.equals(".JPEG")&&!fileSuffix.equals(".PNG")&&!fileSuffix.equals(".SWF")){
            	 return fileName;
            } else {
            	fileName = UUID.randomUUID().toString().replaceAll("-", "") + fileSuffix;
            	// 转存文件  
            	multipartFile.transferTo(new File(filePath + fileName));
            	fileName = dirname + "/" + fileName ;
            }
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return fileName;
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
	
}