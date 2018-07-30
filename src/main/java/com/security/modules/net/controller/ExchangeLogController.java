package com.security.modules.net.controller;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONObject;
import com.qiniu.util.StringUtils;
import com.security.common.annotation.SysLog;
import com.security.common.exception.TouchException;
import com.security.common.utils.ExportUtils;
import com.security.common.utils.PageUtils;
import com.security.common.utils.Query;
import com.security.common.utils.R;
import com.security.modules.net.entity.GameInfo;
import com.security.modules.net.entity.OrderInfo;
import com.security.modules.net.service.ExchangeLogService;
import com.security.modules.sys.controller.AbstractController;
import com.security.modules.touch.entity.BFBannerConfig;
import com.security.modules.touch.entity.BFBannerInf;
import com.security.modules.touch.service.GetSortNumService;




/**
 * App ExchangeLogController
 * 
 * @author lbx
 */
@RestController
@RequestMapping("/touch/exchange")
public class ExchangeLogController extends AbstractController {
	
	@Autowired
	private ExchangeLogService exchangeService;
	
	@Autowired
	private GetSortNumService sortNumService;
	
	private Logger log = LoggerFactory.getLogger(ExchangeLogController.class);
	
	
	/**
	 * @说明: 积分兑换日志
	 * @author: lbx
	 * @date: 2018年7月16日 下午4:08:22 
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions("touch:exchange:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<OrderInfo> list = null;
		int total = 0;
		try {
			list = exchangeService.queryPageList(query, new RowBounds(query.getOffset(), query.getLimit()));
			total=exchangeService.count(params);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取首页Banner列表失败");
		}
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	


	/**
	 * Banner信息
	 */
	@RequestMapping("/info/{pid}")
	@RequiresPermissions("touch:exchange:info")
	public R info(@PathVariable("pid") String pid){
		
		OrderInfo order=null;
		try {
			
			 order = exchangeService.queryEntity(pid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取信息异常");
		}
		return R.ok().put("order",order);
	}
	
	
	@SysLog("信息推送")
	@RequestMapping("/send")
	@RequiresPermissions("touch:exchange:send")
	public R update(@RequestBody OrderInfo order){
		try {
			if(StringUtils.isNullOrEmpty(order.getOrder_id())){
				return R.error("获取订单编号失败...");
			}

			order = exchangeService.queryEntity(order.getOrder_id());
			if(StringUtils.isNullOrEmpty(order.getUser_id())){
				return R.error("获取memb_id失败...");
			}
			 exchangeService.sendOrder(order);
		} catch(TouchException e){
			log.error(e.getMessage(), e);
			return R.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("信息推送异常");
		}
		return R.ok();
	}
	
	@SysLog("导出")
	@RequestMapping("/export")
	@RequiresPermissions("touch:exchange:export")
	public R export(@RequestBody Map<String,Object> params){
		List<String> columns=(List) params.get("colList");
		JSONObject reJson=new JSONObject();
		try {
			List<OrderInfo> data=exchangeService.queryList(null);
			List<Map<String,String>> list=new ArrayList<Map<String,String>>();
			for (OrderInfo order : data) {
				Map<String,String> map=new HashMap<String,String>();
				for (String str : columns) {
					switch(str){
					case "Order number":
						map.put(str, order.getOrder_id());
						 break;
					case "Country":
						map.put(str, order.getCounrty());
						 break;
					case "Shop name":
						map.put(str, order.getShopname());
						 break;
					case "Member account":
						map.put(str, order.getUser_id());
						 break;
					case "Member name":
						map.put(str, order.getUser_name());
						 break;
					case "Purchase quantity":
						map.put(str, order.getBuy_count());
						 break;
					case "Total Credits":
						map.put(str, order.getTotal_integral());
						 break;
					case "Order status":
						map.put(str, order.getStatus().equals("1")? "Submitted" : "Others");
						 break;
					case "Order time":
						map.put(str, order.getInsert_date());
						 break;
					default:
						 break;
					}		
				}
				list.add(map);
			}
			reJson=ExportUtils.export("exchange", list);
		} catch(TouchException e){
			log.error(e.getMessage(), e);
			return R.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("导出异常");
		}
		return R.ok().put("result", reJson);
	}
}
