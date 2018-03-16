package com.security.modules.touch.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.security.common.annotation.SysLog;
import com.security.common.utils.CommonUtils;
import com.security.common.utils.PageUtils;
import com.security.common.utils.Query;
import com.security.common.utils.R;
import com.security.common.validator.ValidatorUtils;
import com.security.common.validator.group.UpdateGroup;
import com.security.modules.sys.entity.MessageInfo;
import com.security.modules.touch.entity.BFBannerConfig;
import com.security.modules.touch.entity.BFBannerInf;
import com.security.modules.touch.entity.BFBusCenter;
import com.security.modules.touch.entity.BFNewsEntity;
import com.security.modules.touch.service.BFBusCenterService;

/**
 * 
 * @说明 直销事业中心
 * @author hejun
 * @时间 2018年3月13日 下午3:47:31
 */
@RestController
@RequestMapping("/touch/buscenter")
public class BFBusCenterController {
	@Autowired
	private BFBusCenterService busCenterService;
	private Logger log = LoggerFactory.getLogger(BFBannerController.class);
	/**
	 * 获取直销事业中心的信息
	 */
	@RequestMapping("/getInfo")
	@RequiresPermissions("touch:buscenter:getInfo")
	public R getInfo(String type){
		BFBusCenter busCenter = null;
		try {
			busCenter = busCenterService.getInfo(type);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取直销事业中心 "+ type +" 信息异常");
		}
		return R.ok().put("outlets", busCenter);
	}
	
	/**
	 * 修改推送消息
	 */
	@SysLog("修改事业中心信息")
	@RequestMapping("/update")
	@RequiresPermissions("touch:buscenter:update")
	public R update(@RequestBody Map<String,Object> paramsMap){
		BFBusCenter busCenter =  JSONObject.toJavaObject(JSONObject.parseObject(paramsMap.get("outlets").toString()),
				BFBusCenter.class);
		ValidatorUtils.validateEntity(busCenter, UpdateGroup.class);
		try {
			//checkSortNum(message)
			String contentBase64 = busCenter.getContent();
			String content = CommonUtils.decodeBase64(contentBase64);
			busCenter.setContent(content);
			busCenterService.update(busCenter);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改事业中心信息");
		}
		return R.ok();
	}
}
