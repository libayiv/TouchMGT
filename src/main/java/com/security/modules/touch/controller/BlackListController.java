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

import com.security.common.utils.PageUtils;
import com.security.common.utils.Query;
import com.security.common.utils.R;
import com.security.common.validator.ValidatorUtils;
import com.security.common.validator.group.AddGroup;
import com.security.modules.sys.controller.AbstractController;
import com.security.modules.touch.entity.BFBlackList;
import com.security.modules.touch.service.BFBlackListService;


@RestController
@RequestMapping("/touch/blacklist")
public class BlackListController extends AbstractController {
	
	@Autowired
	private BFBlackListService bfBlackListService;
	
	private Logger log = LoggerFactory.getLogger(BlackListController.class);
	
	/**
	 * 所有blacklist列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("touch:blacklist:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<BFBlackList> blackList = null;
	
		int total = 0;
		try {
			query.put("status", 1);
			blackList = bfBlackListService.queryPageList(query, new RowBounds(query.getOffset(), query.getLimit()));
			total = bfBlackListService.count(query);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取首页BlackList列表失败");
		}
		PageUtils pageUtil = new PageUtils(blackList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}

	// 保存
	@RequestMapping("/save")
	@RequiresPermissions("touch:blacklist:save")
	public R save(@RequestBody BFBlackList bfBlackList) {
		ValidatorUtils.validateEntity(bfBlackList, AddGroup.class);		
		try {
			//查询是否存在membid
			bfBlackList.setMembId(bfBlackList.getMembId().toUpperCase());
			int count = bfBlackListService.queryByMmebId(bfBlackList.getMembId().toUpperCase());
			if(count == 0){
				//保存数据
				bfBlackListService.save(bfBlackList);
			}else{
				return R.error("人员已经存在黑名单中");
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			R.error("保存BlackList失败");
		}

		return R.ok();
	}

	/*// 获取单个客户信息
	@RequestMapping("/info/{pid}")
	@RequiresPermissions("hyb:customer:info")
	public R info(@PathVariable("pid") String pid) {
		CustomerInfo customerInfo = hybCustomerService.queryObject(pid);
		return R.ok().put("customer", customerInfo);
	}*/


	// 删除黑名单信息
	@RequestMapping("/updateStatus")
	@RequiresPermissions("touch:blacklist:updateStatus")
	public R updateStatus(@RequestBody Map<String, Object> params) {
		try {
			bfBlackListService.updateBatch(params);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("操作异常");
		}
		return R.ok();
	}

}
