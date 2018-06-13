package com.security.modules.touch.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.common.annotation.SysLog;
import com.security.common.utils.R;
import com.security.common.validator.ValidatorUtils;
import com.security.common.validator.group.AddGroup;
import com.security.common.validator.group.UpdateGroup;
import com.security.modules.touch.entity.AppVersionEntity;
import com.security.modules.touch.service.AppVersionService;


/**
 * 
 * @说明 API版本
 * @author lbx

 */
@RestController
@RequestMapping("/touch/app")
public class AppVersionController {

	@Autowired
	private AppVersionService appService;

	private Logger log = LoggerFactory.getLogger(AppVersionController.class);

	
	


	/**
	 * 保存Banner
	 */
	@SysLog("保存推送配置")
	@RequestMapping("/save")
	@RequiresPermissions("touch:app:save")
	public R save(@RequestBody AppVersionEntity app){
		log.info("AppVersionEntity:{}", app);
		ValidatorUtils.validateEntity(app, AddGroup.class);
		
		try {

			appService.save(app);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("保存推送配置失败");
		}

		return R.ok();
	}

	/**
	 * 修改Banner
	 */
	@SysLog("修改推送配置")
	@RequestMapping("/update")
	@RequiresPermissions("touch:app:update")
	public R update(@RequestBody AppVersionEntity app){
		ValidatorUtils.validateEntity(app, UpdateGroup.class);

		try {
		
			appService.update(app);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改推送配置失败");
		}
		return R.ok();
	}
	/**
	 * 上传信息
	 */
	@RequestMapping("/max")
	@RequiresPermissions("touch:app:info")
	public R info(){
		AppVersionEntity app = null;
		try {
			app = appService.queryEntity();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取版本信息异常");
		}
		return R.ok().put("app", app);
	}

	
	

}
