package com.security.modules.touch.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.codec.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.jdbc.util.Base64Decoder;
import com.security.common.annotation.SysLog;
import com.security.common.exception.TouchException;
import com.security.common.utils.PageUtils;
import com.security.common.utils.Query;
import com.security.common.utils.R;
import com.security.common.validator.ValidatorUtils;
import com.security.common.validator.group.AddGroup;
import com.security.common.validator.group.UpdateGroup;
import com.security.modules.sys.controller.AbstractController;
import com.security.modules.touch.entity.BFActivityApplication;
import com.security.modules.touch.entity.BFActivityFBack;
import com.security.modules.touch.entity.BFActivityList;
import com.security.modules.touch.service.BFActivityService;
import com.security.modules.touch.service.GetSortNumService;




/**
 * App首页activity Controller
 * 
 * @author lbx
 */
@RestController
@RequestMapping("/touch/activity")
public class BFActivityController extends AbstractController {
	
	@Autowired
	private BFActivityService bfActivityService;
	
	@Autowired
	private GetSortNumService sortNumService;
	
	private Logger log = LoggerFactory.getLogger(BFActivityController.class);
	
	/**
	 * 所有Activity列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("touch:activity:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<BFActivityList> activityList = null;
		int total = 0;
		try {
			activityList = bfActivityService.queryPageList(query, new RowBounds(query.getOffset(), query.getLimit()));
			total = bfActivityService.count(query);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取首页Activity列表失败");
		}
		PageUtils pageUtil = new PageUtils(activityList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}

	
	/**
	 * 保存Activity
	 */
	@SysLog("保存Activity")
	@RequestMapping("/save")
	@RequiresPermissions("touch:activity:save")
	public R save(@RequestBody BFActivityList activity){
		log.info("activity:{}", activity);
		ValidatorUtils.validateEntity(activity, AddGroup.class);
		log.info("添加图片activity:{}", activity);

		try {
			checkSortNum(activity);
			byte[] contentByte = Base64.decode(activity.getContent().getBytes("UTF-8"));    
			String content = new String(contentByte);
			activity.setContent(content);
			bfActivityService.save(activity);
		}catch (TouchException e1) {
			log.error(e1.getMessage(), e1);
			return R.error(e1.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("添加首页Activity失败");
		}
		
		return R.ok();
	}
	
	/**
	 * 修改Activity
	 */
	@SysLog("修改Activity")
	@RequestMapping("/update")
	@RequiresPermissions("touch:activity:update")
	public R update(@RequestBody BFActivityList activity){
		ValidatorUtils.validateEntity(activity, UpdateGroup.class);
		try {
			byte[] contentByte = Base64.decode(activity.getContent().getBytes("UTF-8"));    
			String content = new String(contentByte);
			activity.setContent(content);
			checkSortNum(activity);
			bfActivityService.update(activity);
		} catch(TouchException e){
			log.error(e.getMessage(), e);
			return R.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改Activity");
		}
		return R.ok();
	}
	
	/**
	 * 修改Activity序号
	 */
	@SysLog("修改Activity序号")
	@RequestMapping("/updateSortNum")
	@RequiresPermissions("touch:activity:update")
	public R updateSortNum(BFActivityList activity){
		try {

			checkSortNum(activity);	
			bfActivityService.updateSortNum(activity);
		} catch(TouchException e){
			log.error(e.getMessage(), e);
			return R.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改Activity序号异常");
		}
		return R.ok();
	}
	
	/**
	 * 校验序号  无效将抛出异常
	 */
	private void checkSortNum(BFActivityList activity) throws TouchException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", "BFCMS_ACTIVITY_LIST"); //表名
		map.put("rank", activity.getRank()); //序号
	//	map.put("pc_valid", activity.getPc_valid()); //所属平台
		map.put("id", activity.getId());
		Map<String, Object> repeatMap = null;
		if(Integer.valueOf(activity.getRank()) > 100){
			throw new TouchException("序号长度不得大于100");
		}
		try {
			repeatMap = sortNumService.repeat(map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new TouchException("校验序号异常");
		}
		if((int)repeatMap.get("code") != 0){
			throw new TouchException(repeatMap.get("msg").toString());
		}
		
		
	}
	
	/**
	 * 删除Activity
	 */
	@SysLog("删除Activity")
	@RequestMapping("/delete")
	@RequiresPermissions("touch:activity:delete")
	public R delete(@RequestBody String[] pids){
		try {
			bfActivityService.deleteBatch(pids);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("删除Activity异常");
		}
		return R.ok();
	}
	
	/**
	 * Activity信息
	 */
	@RequestMapping("/info/{pid}")
	@RequiresPermissions("touch:activity:info")
	public R info(@PathVariable("pid") String pid){
		BFActivityList activity = null;
		try {
			activity = bfActivityService.queryEntity(pid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取Activity信息异常");
		}
		return R.ok().put("activity", activity);
	}
	
	/**
	 * 修改Activity状态
	 */
	@SysLog("修改Activity状态")
	@RequestMapping("/updateStatus")
	@RequiresPermissions("touch:activity:updateStatus")
	public R updateStatus(@RequestBody Map<String, Object> params){
		try {
			log.info("params:{}", params);
			String pids = params.get("pids").toString();
			String status = params.get("status").toString();
			bfActivityService.updateStatus(pids, status);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("删除Activity异常");
		}
		return R.ok();
	}

	/**
	 * 反馈列表
	 */
	@RequestMapping("/feedback")
	@RequiresPermissions("touch:activity:list")
	public R feedbacklist(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<BFActivityFBack> activityList = null;
		int total = 0;
		try {
			if(params.get("activity_id")==null){
				return R.ok().put("page", null);
			}
			activityList = bfActivityService.queryFeedBackList(query, new RowBounds(query.getOffset(), query.getLimit()));
			total = bfActivityService.feedCount(query);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取反馈列表失败");
		}
		PageUtils pageUtil = new PageUtils(activityList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 报名列表
	 */
	@RequestMapping("/app")
	@RequiresPermissions("touch:activity:list")
	public R applist(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<BFActivityApplication> activityList = null;
		int total = 0;
		try {
			if(params.get("activity_id")==null){
				return R.ok().put("page", null);
			}
			activityList = bfActivityService.queryApplicationList(query, new RowBounds(query.getOffset(), query.getLimit()));
			total = bfActivityService.appCount(query);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取报名列表失败");
		}
		PageUtils pageUtil = new PageUtils(activityList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 修改反馈列表状态
	 */
	@RequestMapping("/updateFeedStatus")
	@RequiresPermissions("touch:activity:updateStatus")
	public R updateFeedStatus(@RequestBody Map<String, Object> params){
		try {
			log.info("params:{}", params);
			String pids = params.get("pids").toString();
			String status = params.get("status").toString();
			bfActivityService.updateFeedStatus(pids, status);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改反馈列表状态");
		}
		return R.ok();
	}
}
