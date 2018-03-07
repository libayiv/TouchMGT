package com.security.modules.touch.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.common.utils.R;
import com.security.modules.touch.utils.JsonConfigUtils;



/**
 * 数据字典controller
 * 
 * @author lbx
 */
@RestController
@RequestMapping("/touch/dict")
public class TouchDictController {
	
	private Logger log = LoggerFactory.getLogger(TouchDictController.class);
	
	@Value("${FILE_URL_PATH}")
	private String fileUrlPath;
	
	/**
	 * 获取数据字典
	 */
	@RequestMapping("/getDict")
	public R getDictList(@RequestParam("dictCode") String dictCode){
		log.info("dictCode:{}", dictCode);
		List<Map<String, String>> dictList = JsonConfigUtils.getDictValue(dictCode);
		log.info("dictList:{}", dictList);
		return R.ok().put("dictList", dictList);
	}
	
	/**
	 * 获取文件请求路径前缀
	 */
	@RequestMapping("/getFlieUrlPath")
	public R getFlieUrlPath(){
		log.debug("获取文件请求路径前缀");
		return R.ok().put("fileUrlPath", fileUrlPath);
	}
	
}
