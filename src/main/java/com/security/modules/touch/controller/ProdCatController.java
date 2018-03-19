package com.security.modules.touch.controller;

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
import com.security.common.utils.CommonUtils;
import com.security.common.utils.PageUtils;
import com.security.common.utils.Query;
import com.security.common.utils.R;
import com.security.common.validator.ValidatorUtils;
import com.security.common.validator.group.UpdateGroup;
import com.security.modules.sys.controller.MessageController;
import com.security.modules.sys.entity.MessageInfo;
import com.security.modules.touch.entity.ProdCatEntity;
import com.security.modules.touch.service.ProdCatService;

@RestController
@RequestMapping("/touch/category")
public class ProdCatController {
	@Autowired
	ProdCatService categoryService;
	private Logger log = LoggerFactory.getLogger(ProdCatController.class);
	/**
	 * 所有产品类型
	 */
	@RequestMapping("/list")
	@RequiresPermissions("touch:category:list")
	public R list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<ProdCatEntity> cateList = null;
		int total = 0;
		try {
			/*FireBaseUtil.pushFCMNotification();*/
			cateList = categoryService.queryPageList(query, new RowBounds(query.getOffset(), query.getLimit()));
			total = categoryService.count(query);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取 所有产品类型 失败");
		}
		PageUtils pageUtil = new PageUtils(cateList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 修改产品类型
	 */
	@SysLog("修改产品类型")
	@RequestMapping("/update")
	@RequiresPermissions("touch:category:update")
	public R update(@RequestBody ProdCatEntity category){
		ValidatorUtils.validateEntity(category, UpdateGroup.class);
		try {
			categoryService.update(category);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改Banner");
		}
		return R.ok();
	}
	
	/**
	 * 产品类型详细信息
	 */
	@RequestMapping("/info/{pid}")
	@RequiresPermissions("touch:category:info")
	public R info(@PathVariable("pid") String pid){
		ProdCatEntity category = null;
		try {
			category = categoryService.queryEntity(pid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取产品类型信息异常");
		}
		return R.ok().put("cat", category);
	}
}
