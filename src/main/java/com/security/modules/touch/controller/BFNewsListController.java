package com.security.modules.touch.controller;

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
import com.security.common.utils.PageUtils;
import com.security.common.utils.Query;
import com.security.common.utils.R;
import com.security.common.validator.ValidatorUtils;
import com.security.common.validator.group.AddGroup;
import com.security.common.validator.group.UpdateGroup;
import com.security.modules.sys.controller.AbstractController;
import com.security.modules.touch.entity.BFBannerConfig;
import com.security.modules.touch.entity.BFNewsEntity;
import com.security.modules.touch.service.BFBannerService;
import com.security.modules.touch.service.BFNewsListService;
import com.security.modules.touch.service.GetSortNumService;




/**
 * App新闻 Controller
 * 
 * @author lbx
 */
@RestController
@RequestMapping("/touch/news")
public class BFNewsListController extends AbstractController {
	
	@Autowired
	private BFNewsListService bfNewsService;
	
	@Autowired
	private GetSortNumService sortNumService;
	
	private Logger log = LoggerFactory.getLogger(BFNewsListController.class);
	
	/**
	 * 所有news列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("touch:news:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<BFNewsEntity> newsList = null;
	
		int total = 0;
		try {
			newsList = bfNewsService.queryPageList(query, new RowBounds(query.getOffset(), query.getLimit()));
			total = bfNewsService.count(query);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取首页news列表失败");
		}
		PageUtils pageUtil = new PageUtils(newsList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}

	
	/**
	 * 保存news
	 */
	@SysLog("保存news")
	@RequestMapping("/save")
	@RequiresPermissions("touch:news:save")
	public R save(@RequestBody BFNewsEntity news){
		log.info("news:{}", news);
		ValidatorUtils.validateEntity(news, AddGroup.class);
		log.info("添加图片news:{}", news);
		
		try {
			bfNewsService.save(news);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("添加首页news失败");
		}
		
		return R.ok();
	}
	
	/**
	 * 修改news
	 */
	@SysLog("修改news")
	@RequestMapping("/update")
	@RequiresPermissions("touch:news:update")
	public R update(@RequestBody BFNewsEntity news){
		ValidatorUtils.validateEntity(news, UpdateGroup.class);
		try {
			bfNewsService.update(news);
		} catch(TouchException e){
			log.error(e.getMessage(), e);
			return R.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改news");
		}
		return R.ok();
	}
	
	/**
	 * 修改news序号
	 */
	@SysLog("修改news序号")
	@RequestMapping("/updateSortNum")
	@RequiresPermissions("touch:news:update")
	public R updateSortNum(BFNewsEntity news){
		try {
		
			bfNewsService.update(news);
		} catch(TouchException e){
			log.error(e.getMessage(), e);
			return R.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改news序号异常");
		}
		return R.ok();
	}
	
	/**
	 * 删除news
	 */
	@SysLog("删除news")
	@RequestMapping("/delete")
	@RequiresPermissions("touch:news:delete")
	public R delete(@RequestBody String[] pids){
		try {
			bfNewsService.deleteBatch(pids);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("删除news异常");
		}
		return R.ok();
	}
	
	/**
	 * news信息
	 */
	@RequestMapping("/info/{pid}")
	@RequiresPermissions("touch:news:info")
	public R info(@PathVariable("pid") String pid){
		BFNewsEntity news = null;
		try {
			news = bfNewsService.queryEntity(pid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取news信息异常");
		}
		return R.ok().put("news", news);
	}
	
	/**
	 * 修改news状态
	 */
	@SysLog("修改news状态")
	@RequestMapping("/updateStatus")
	@RequiresPermissions("touch:news:updateStatus")
	public R updateStatus(@RequestBody Map<String, Object> params){
		try {
			log.info("params:{}", params);
			String pids = params.get("pids").toString();
			String status = params.get("status").toString();
			bfNewsService.updateStatus(pids, status);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("删除news异常");
		}
		return R.ok();
	}

}
