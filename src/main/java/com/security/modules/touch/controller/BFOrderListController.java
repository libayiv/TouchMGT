package com.security.modules.touch.controller;

import java.net.URLDecoder;
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

import com.qiniu.util.StringUtils;
import com.security.common.annotation.SysLog;
import com.security.common.exception.TouchException;
import com.security.common.utils.PageUtils;
import com.security.common.utils.Query;
import com.security.common.utils.R;
import com.security.common.validator.ValidatorUtils;
import com.security.common.validator.group.AddGroup;
import com.security.common.validator.group.UpdateGroup;
import com.security.modules.sys.controller.AbstractController;
import com.security.modules.touch.entity.BFOrderDetail;
import com.security.modules.touch.entity.BFOrderInf;
import com.security.modules.touch.entity.BFOrderInf;
import com.security.modules.touch.entity.BFOrderInf;
import com.security.modules.touch.service.GetSortNumService;
import com.security.modules.touch.service.impl.BFOrderListServiceImpl;




/**
 * App订单 Controller
 * 
 * @author lbx
 */
@RestController
@RequestMapping("/touch/order")
public class BFOrderListController extends AbstractController {
	
	@Autowired
	private BFOrderListServiceImpl bfOrderListService;
	
	@Autowired
	private GetSortNumService sortNumService;
	
	private Logger log = LoggerFactory.getLogger(BFOrderListController.class);
	
	/**
	 * 所有order列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("touch:order:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<BFOrderInf> orderList = null;
	
		int total = 0;
		try {
			orderList = bfOrderListService.queryPageList(query, new RowBounds(query.getOffset(), query.getLimit()));
			total = bfOrderListService.count(query);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取首页order列表失败");
		}
		PageUtils pageUtil = new PageUtils(orderList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 所有order的商品列表
	 */
	@RequestMapping("/detail")
	@RequiresPermissions("touch:order:list")
	public R detail(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<BFOrderDetail> orderList = null;
		if(params.get("order_id")==null){
			return R.ok().put("page", null);
		}
		int total = 0;
		try {
			orderList = bfOrderListService.queryDetailList(query, new RowBounds(query.getOffset(), query.getLimit()));
			total = bfOrderListService.count(query);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取首页order商品列表失败");
		}
		PageUtils pageUtil = new PageUtils(orderList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	

	

	

	
	/**
	 * order信息
	 */
	@RequestMapping("/info/{pid}")
	@RequiresPermissions("touch:order:info")
	public R info(@PathVariable("pid") String pid){
		BFOrderInf order = null;
		try {
			order = bfOrderListService.queryEntity(pid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取order信息异常");
		}
		return R.ok().put("order", order);
	}
	

	
	


}
