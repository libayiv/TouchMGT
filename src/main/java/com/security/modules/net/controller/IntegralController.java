package com.security.modules.net.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.common.utils.PageUtils;
import com.security.common.utils.Query;
import com.security.common.utils.R;
import com.security.modules.net.entity.IntegralLog;
import com.security.modules.net.entity.MembInfo;
import com.security.modules.net.service.IntegralService;
import com.security.modules.sys.controller.AbstractController;
import com.security.modules.touch.service.GetSortNumService;




/**
 * App integralLogController
 * 
 * @author lbx
 */
@RestController
@RequestMapping("/touch/integral")
public class IntegralController extends AbstractController {
	
	@Autowired
	private IntegralService integralService;
	
	@Autowired
	private GetSortNumService sortNumService;
	
	private Logger log = LoggerFactory.getLogger(IntegralController.class);
	
	
	/**
	 * @说明: 积分兑换日志
	 * @author: lbx
	 * @date: 2018年7月16日 下午4:08:22 
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions("touch:integral:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<IntegralLog> list = null;
		int total = 0;
		try {
			list = integralService.queryPageList(query, new RowBounds(query.getOffset(), query.getLimit()));
			total=integralService.count(params);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取首页列表失败");
		}
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	


	/**
	 * Banner信息
	 */
	@RequestMapping("/info/{pid}/{uid}")
	@RequiresPermissions("touch:integral:info")
	public R info(@PathVariable("pid") String pid,@PathVariable("uid") String uid){
		List<IntegralLog> integral=null;
		MembInfo memb=null;
		try {
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("integral_id", pid);
			integral = integralService.queryList(params);
			memb=integralService.queryMemb(uid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取信息异常");
		}
		return R.ok().put("integral", integral.get(0)).put("memb", memb);
	}
	
	
}
