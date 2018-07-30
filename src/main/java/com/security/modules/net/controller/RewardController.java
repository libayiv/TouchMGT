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
import com.security.common.annotation.SysLog;
import com.security.common.exception.TouchException;
import com.security.common.utils.ExportUtils;
import com.security.common.utils.PageUtils;
import com.security.common.utils.Query;
import com.security.common.utils.R;
import com.security.modules.net.entity.MembInfo;
import com.security.modules.net.entity.RewardInfo;
import com.security.modules.net.service.RewardService;
import com.security.modules.sys.controller.AbstractController;
import com.security.modules.touch.service.GetSortNumService;




/**
 * App rewardLogController
 * 
 * @author lbx
 */
@RestController
@RequestMapping("/touch/reward")
public class RewardController extends AbstractController {
	
	@Autowired
	private RewardService rewardService;
	
	@Autowired
	private GetSortNumService sortNumService;
	
	private Logger log = LoggerFactory.getLogger(RewardController.class);
	
	
	/**
	 * @说明: 积分兑换日志
	 * @author: lbx
	 * @date: 2018年7月16日 下午4:08:22 
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions("touch:reward:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<RewardInfo> list = null;
		int total = 0;
		try {
			list = rewardService.queryPageList(query, new RowBounds(query.getOffset(), query.getLimit()));
			total=rewardService.count(params);
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
	@RequestMapping("/info/{pid}")
	@RequiresPermissions("touch:reward:info")
	public R info(@PathVariable("pid") String pid){
		MembInfo memb=null;

		try {
			
			memb=rewardService.queryMemb(pid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取信息异常");
		}
		return R.ok().put("memb", memb);
	}
	
	
	@SysLog("导出")
	@RequestMapping("/export")
	@RequiresPermissions("touch:reward:export")
	public R export(@RequestBody Map<String,Object> params){
		List<String> columns=(List) params.get("colList");
		JSONObject reJson=new JSONObject();
		try {
			List<RewardInfo> data=rewardService.queryList(null);
			List<Map<String,String>> list=new ArrayList<Map<String,String>>();
			for (RewardInfo reward : data) {
				Map<String,String> map=new HashMap<String,String>();
				for (String str : columns) {
					switch(str){
					case "Member account":
						map.put(str, reward.getMemb_id());
						 break;
					case "Member name":
						map.put(str, reward.getMemb_name());
						 break;
					case "Member contacts":
						map.put(str, reward.getMemb_phone_mobile());
						 break;
					case "Total Credits":
						map.put(str, reward.getTotal_integral());
						 break;
					case "Complete time":
						map.put(str, reward.getInsert_date());
						 break;
					default:
						 break;
					}		
				}
				list.add(map);
			}
			reJson=ExportUtils.export("reward", list);
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
