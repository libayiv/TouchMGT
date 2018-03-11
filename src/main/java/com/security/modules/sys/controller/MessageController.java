package com.security.modules.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
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
import com.security.common.exception.TouchException;
import com.security.common.utils.FireBaseUtil;
import com.security.common.utils.PageUtils;
import com.security.common.utils.Query;
import com.security.common.utils.R;
import com.security.common.validator.ValidatorUtils;
import com.security.common.validator.group.AddGroup;
import com.security.common.validator.group.UpdateGroup;
import com.security.modules.sys.entity.MessageInfo;
import com.security.modules.sys.entity.SysConfigEntity;
import com.security.modules.sys.service.MessageService;
import com.security.modules.sys.service.SysConfigService;
import com.security.modules.touch.controller.BFBannerController;
import com.security.modules.touch.entity.BFBannerInf;
import com.security.modules.touch.service.GetSortNumService;

/**
 * 
 * @说明 google消息 配置controller
 * @author hejun
 * @时间 2018年3月6日 下午3:43:02
 */
@RestController
@RequestMapping("/sys/message")
public class MessageController {

	@Autowired
	private MessageService messageService;
	@Autowired
	private GetSortNumService sortNumService;
	private Logger log = LoggerFactory.getLogger(MessageController.class);

	/**
	 * 所有消息配置列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:message:list")
	public R list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<MessageInfo> messageList = null;
		int total = 0;
		try {
			/*FireBaseUtil.pushFCMNotification();*/
			messageList = messageService.queryPageList(query, new RowBounds(query.getOffset(), query.getLimit()));
			total = messageService.count(query);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取 所有消息配置列表 失败");
		}
		PageUtils pageUtil = new PageUtils(messageList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 保存推送消息
	 */
	@SysLog("保存推送消息")
	@RequestMapping("/save")
	@RequiresPermissions("sys:message:save")
	public R save(@RequestBody MessageInfo message){
		//log.info("message:{}", message);
		ValidatorUtils.validateEntity(message, AddGroup.class);
		log.info("添加message:{}", message);
		
		try {
			messageService.save(message);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("添加 推送消息 失败");
		}
		
		return R.ok();
	}
	
	/**
	 * 修改推送消息
	 */
	@SysLog("修改推送消息")
	@RequestMapping("/update")
	@RequiresPermissions("sys:message:update")
	public R update(@RequestBody MessageInfo message){
		ValidatorUtils.validateEntity(message, UpdateGroup.class);
		try {
			//checkSortNum(message);
			messageService.update(message);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改Banner");
		}
		return R.ok();
	}
	
	/**
	 * 校验序号  无效将抛出异常
	 */
/*	private void checkSortNum(MessageInfo message) throws TouchException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", "BFCMS_MESSAGE_INFO"); //表名
		map.put("rank", message.getRank()); //序号
		map.put("id", message.getId());
		Map<String, Object> repeatMap = null;
		try {
			repeatMap = sortNumService.repeat(map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TouchException("校验序号异常");
		}
		if((int)repeatMap.get("code") != 0){
			throw new TouchException(repeatMap.get("msg").toString());
		}
	}*/
	
	/**
	 * 修改Message序号
	 */
/*	@SysLog("修改Message序号")
	@RequestMapping("/updateSortNum")
	@RequiresPermissions("sys:message:update")
	public R updateSortNum(MessageInfo message){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tableName", "HYBM_banner_INF"); //表名
			map.put("sortNum", banner.getSortNum()); //序号
			map.put("platform", banner.getPlatform()); //所属平台	
			Map<String, Object> repeatMap = sortNumService.repeat(map);
			if((int)repeatMap.get("code") != 0){
				return R.error(repeatMap.get("msg").toString());
			}
			checkSortNum(message);	
			messageService.update(message);
		} catch(TouchException e){
			log.error(e.getMessage(), e);
			return R.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改Message序号异常");
		}
		return R.ok();
	}*/
	
	/**
	 * 删除Message
	 */
	@SysLog("删除Message")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:message:delete")
	public R delete(@RequestBody String[] pids){
		try {
			messageService.deleteBatch(pids);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("删除Message异常");
		}
		return R.ok();
	}
	
	/**
	 * Message详细信息
	 */
	@RequestMapping("/info/{pid}")
	@RequiresPermissions("sys:message:info")
	public R info(@PathVariable("pid") String pid){
		MessageInfo message = null;
		try {
			message = messageService.queryEntity(pid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取Message信息异常");
		}
		return R.ok().put("message", message);
	}
	
	/**
	 * 
	 */
	@SysLog("发送Message")
	@RequestMapping("/sendGoogle")
	@RequiresPermissions("sys:message:sendGoogle")
	public R sendGoogle(@RequestBody String[] pids){
		try {
			messageService.sendMsg(pids);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("发送Message异常");
		}
		return R.ok();
	}
}
