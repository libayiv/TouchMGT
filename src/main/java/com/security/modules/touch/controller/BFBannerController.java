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
import com.security.modules.touch.entity.BFBannerInf;
import com.security.modules.touch.service.BFBannerService;
import com.security.modules.touch.service.GetSortNumService;




/**
 * App首页banner Controller
 * 
 * @author lbx
 */
@RestController
@RequestMapping("/touch/banner")
public class BFBannerController extends AbstractController {
	
	@Autowired
	private BFBannerService bfBannerService;
	
	@Autowired
	private GetSortNumService sortNumService;
	
	private Logger log = LoggerFactory.getLogger(BFBannerController.class);
	
	/**
	 * 所有Banner列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("touch:banner:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<BFBannerInf> bannerList = null;
		int total = 0;
		try {
			bannerList = bfBannerService.queryPageList(query, new RowBounds(query.getOffset(), query.getLimit()));
			total = bfBannerService.count(query);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取首页Banner列表失败");
		}
		PageUtils pageUtil = new PageUtils(bannerList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 保存Banner
	 */
	@SysLog("保存Banner")
	@RequestMapping("/save")
	@RequiresPermissions("touch:banner:save")
	public R save(@RequestBody BFBannerInf banner){
		log.info("banner:{}", banner);
		ValidatorUtils.validateEntity(banner, AddGroup.class);
		log.info("添加图片banner:{}", banner);
		
		try {
			bfBannerService.save(banner);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("添加首页Banner失败");
		}
		
		return R.ok();
	}
	
	/**
	 * 修改Banner
	 */
	@SysLog("修改Banner")
	@RequestMapping("/update")
	@RequiresPermissions("touch:banner:update")
	public R update(@RequestBody BFBannerInf banner){
		ValidatorUtils.validateEntity(banner, UpdateGroup.class);
		try {
			checkSortNum(banner);
			bfBannerService.update(banner);
		} catch(TouchException e){
			log.error(e.getMessage(), e);
			return R.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改Banner");
		}
		return R.ok();
	}
	
	/**
	 * 修改Banner序号
	 */
	@SysLog("修改Banner序号")
	@RequestMapping("/updateSortNum")
	@RequiresPermissions("touch:banner:update")
	public R updateSortNum(BFBannerInf banner){
		try {
			/*Map<String, Object> map = new HashMap<String, Object>();
			map.put("tableName", "HYBM_banner_INF"); //表名
			map.put("sortNum", banner.getSortNum()); //序号
			map.put("platform", banner.getPlatform()); //所属平台	
			Map<String, Object> repeatMap = sortNumService.repeat(map);
			if((int)repeatMap.get("code") != 0){
				return R.error(repeatMap.get("msg").toString());
			}*/
			checkSortNum(banner);	
			bfBannerService.update(banner);
		} catch(TouchException e){
			log.error(e.getMessage(), e);
			return R.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改Banner序号异常");
		}
		return R.ok();
	}
	
	/**
	 * 校验序号  无效将抛出异常
	 */
	private void checkSortNum(BFBannerInf banner) throws TouchException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", "BFCMS_BANNER_SETTING"); //表名
		map.put("rank", banner.getRank()); //序号
		map.put("pc_valid", banner.getPc_valid()); //所属平台
		map.put("id", banner.getId());
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
	}
	
	/**
	 * 删除Banner
	 */
	@SysLog("删除Banner")
	@RequestMapping("/delete")
	@RequiresPermissions("touch:banner:delete")
	public R delete(@RequestBody String[] pids){
		try {
			bfBannerService.deleteBatch(pids);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("删除Banner异常");
		}
		return R.ok();
	}
	
	/**
	 * Banner信息
	 */
	@RequestMapping("/info/{pid}")
	@RequiresPermissions("touch:banner:info")
	public R info(@PathVariable("pid") String pid){
		BFBannerInf banner = null;
		try {
			banner = bfBannerService.queryEntity(pid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取Banner信息异常");
		}
		return R.ok().put("banner", banner);
	}
	
	/**
	 * 修改Banner状态
	 */
	@SysLog("修改Banner状态")
	@RequestMapping("/updateStatus")
	@RequiresPermissions("touch:banner:updateStatus")
	public R updateStatus(@RequestBody Map<String, Object> params){
		try {
			log.info("params:{}", params);
			String pids = params.get("pids").toString();
			String status = params.get("status").toString();
			bfBannerService.updateStatus(pids, status);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("删除Banner异常");
		}
		return R.ok();
	}

}
