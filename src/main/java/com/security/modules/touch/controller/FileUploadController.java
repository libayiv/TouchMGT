package com.security.modules.touch.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.security.common.utils.FileUploaderUtils;
import com.security.common.utils.R;

/*
 * 文件上传
 * 
 */
@RestController
@RequestMapping("/touch/fileupload")
public class FileUploadController {
	
	private Logger log = LoggerFactory.getLogger(FileUploadController.class);
	
	@Value("${FILE_URL_PATH}")
	private String fileUrlPath;
	
	/**
	 * 单张图片上传
	 */
	@RequestMapping("uploadimg")
	public R uploadImg(@RequestParam("file") MultipartFile file, String modularName){
		log.info("文件上传file：{}", file);
		String fileName = FileUploaderUtils.saveImage(file, modularName);
		Map<String, Object> fileInf = new HashMap<String, Object>();
		fileInf.put("fileName", fileName);
		fileInf.put("oriFileName", file.getOriginalFilename());
		fileInf.put("url", fileUrlPath + fileName);
		fileInf.put("size", file.getSize());
		return R.ok(fileInf);
	}
	
}
