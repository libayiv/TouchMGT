package com.security.modules.sys.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.common.annotation.SysLog;
import com.security.common.utils.PageUtils;
import com.security.common.utils.Query;
import com.security.common.utils.R;
import com.security.common.validator.ValidatorUtils;
import com.security.common.validator.group.AddGroup;
import com.security.common.validator.group.UpdateGroup;
import com.security.modules.sys.entity.SendEntity;
import com.security.modules.sys.entity.SysUserEntity;
import com.security.modules.sys.service.SendControlService;
import com.security.modules.sys.shiro.ShiroUtils;


/**
 * 
 * @说明 推送配置
 * @author lbx

 */
@RestController
@RequestMapping("/sys/send")
public class SendCtrlController {

	@Autowired
	private SendControlService sendService;

	private Logger log = LoggerFactory.getLogger(SendCtrlController.class);

	
	@RequestMapping("/list")
	@RequiresPermissions("sys:send:all")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<SendEntity> sendList=null;
		int total=0;
		try {
			sendList = sendService.queryList(params);
			total = sendService.queryTotal(query);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取上传列表失败");
		}
		PageUtils pageUtil = new PageUtils(sendList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}
	/**
	 * 删除
	 */
	@SysLog("删除上传文件")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:send:all")
	public R delete(@RequestBody String[] ids){
		sendService.deleteBatch(ids);

		return R.ok();
	}

	/**
	 * 保存Banner
	 */
	@SysLog("保存推送配置")
	@RequestMapping("/save")
	@RequiresPermissions("sys:send:all")
	public R save(@RequestBody SendEntity send){
		log.info("SendEntity:{}", send);
		ValidatorUtils.validateEntity(send, AddGroup.class);
		SysUserEntity user = (SysUserEntity)ShiroUtils.getSubject().getPrincipal();
		
		try {
			send.setOperation_id(user.getUserId().toString());
			send.setOperation_name(user.getUsername());
			sendService.save(send);
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
	@RequiresPermissions("sys:send:all")
	public R update(@RequestBody SendEntity send){
		ValidatorUtils.validateEntity(send, UpdateGroup.class);
		SysUserEntity user = (SysUserEntity)ShiroUtils.getSubject().getPrincipal();

		try {
			send.setOperation_id(user.getUserId().toString());
			send.setOperation_name(user.getUsername());
			sendService.update(send);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改推送配置失败");
		}
		return R.ok();
	}
	/**
	 * 上传信息
	 */
	@RequestMapping("/info/{pid}")
	@RequiresPermissions("sys:send:all")
	public R info(@PathVariable("pid") String pid){
		SendEntity send = null;
		try {
			send = sendService.queryEntity(pid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取上传信息异常");
		}
		return R.ok().put("send", send);
	}

	@SysLog("修改文件状态")
	@RequestMapping("/updateStatus")
	@RequiresPermissions("sys:send:all")
	public R updateStatus(@RequestBody Map<String, Object> params){
		try {
			log.info("params:{}", params);
			String pids = params.get("pids").toString();
			String status = params.get("status").toString();
			sendService.updateStatus(pids, status);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("删除Banner异常");
		}
		return R.ok();
	}
	
	

}
