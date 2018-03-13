package com.security.modules.sys.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.security.common.utils.CommonUtils;
import com.security.common.utils.FileUploaderUtils;
import com.security.common.utils.ueditor.ActionEnter;

/**
 * 用于处理关于ueditor插件相关的请求
 * @author hejun
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/sys/ueditor")
public class UeditorController {
	InputStream in = FileUploaderUtils.class.getClassLoader()  
            .getResourceAsStream("config.properties");
	Properties prop = new  Properties(); 
	
	@RequestMapping(value = "/exec")
	@ResponseBody
	public String exec(HttpServletRequest request) throws UnsupportedEncodingException{ 
		System.out.println("刁颖");
		request.setCharacterEncoding("utf-8");
		String rootPath = request.getRealPath("/");
		System.out.println("调用ueditor------- " + rootPath);
		return new ActionEnter( request, rootPath ).exec();
	}
/*	
    @RequestMapping(value = "/image")
    @ResponseBody
    public Map<String, Object> images (MultipartFile upfile, HttpServletRequest request, HttpServletResponse response){  
        Map<String, Object> params = new HashMap<String, Object>();  
        try{  
        	 prop.load(in);;
             String basePath = prop.getProperty("FILE_SAVE_PATH");  
             String visitUrl = prop.getProperty("FILE_URL_PATH").concat("/");  
             if(basePath == null || "".equals(basePath)){  
                 basePath = "C:/touch/ueditor";  //与properties文件中lyz.uploading.url相同，未读取到文件数据时为basePath赋默认值  
             }  
             if(visitUrl == null || "".equals(visitUrl)){  
                 visitUrl = "/upload/"; //与properties文件中lyz.visit.url相同，未读取到文件数据时为visitUrl赋默认值  
             }  
             String ext = CommonUtils.getExt(upfile.getOriginalFilename());  
             String fileName = String.valueOf(System.currentTimeMillis()).concat("_").concat(CommonUtils.getRandom(6)).concat(".").concat(ext);  
             StringBuilder sb = new StringBuilder();  
             //拼接保存路径  
             sb.append(basePath).append("/").append(fileName);  
             visitUrl = visitUrl.concat(fileName);  
             File f = new File(sb.toString());  
             if(!f.exists()){  
                 f.getParentFile().mkdirs();  
             }  
             OutputStream out = new FileOutputStream(f);  
             FileCopyUtils.copy(upfile.getInputStream(), out);  
             params.put("state", "SUCCESS");  
             params.put("url", visitUrl);  
             params.put("size", upfile.getSize());  
             params.put("original", fileName);  
             params.put("type", upfile.getContentType());  
        } catch (Exception e){  
             params.put("state", "ERROR");  
        }  
         return params;  
    }*/
}
