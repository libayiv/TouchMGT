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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.common.annotation.SysLog;
import com.security.common.exception.TouchException;
import com.security.common.utils.PageUtils;
import com.security.common.utils.Query;
import com.security.common.utils.R;
import com.security.modules.net.entity.GameInfo;
import com.security.modules.net.entity.ProductInfo;
import com.security.modules.net.service.GameService;
import com.security.modules.sys.controller.AbstractController;
import com.security.modules.touch.service.GetSortNumService;




/**
 * App ExchangeLogController
 * 
 * @author lbx
 */
@RestController
@RequestMapping("/touch/game")
public class GameController extends AbstractController {
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private GetSortNumService sortNumService;
	
	private Logger log = LoggerFactory.getLogger(GameController.class);
	
	
	/**
	 * @说明: 积分兑换日志
	 * @author: lbx
	 * @date: 2018年7月16日 下午4:08:22 
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions("touch:game:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<GameInfo> list = null;
		int total = 0;
		try {
			list = gameService.queryPageList(query, new RowBounds(query.getOffset(), query.getLimit()));
			total=gameService.count(params);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取列表失败");
		}
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	


	/**
	 * Banner信息
	 */
	@RequestMapping("/info/{pid}")
	@RequiresPermissions("touch:game:info")
	public R info(@PathVariable("pid") String pid){
		List<GameInfo> game=null;
		try {
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("game_id", pid);
			game = gameService.queryList(params);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取Banner信息异常");
		}
		return R.ok().put("game", game.get(0));
	}
	
	@SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("touch:game:save")
	public R save(@RequestBody GameInfo game){
		
		try {
			gameService.save(game);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("新增异常");
		}
		
		return R.ok();
	}
	
	
	@SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("touch:game:update")
	public R update(@RequestBody GameInfo game){
		try {
			gameService.update(game);
		} catch(TouchException e){
			log.error(e.getMessage(), e);
			return R.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改异常");
		}
		return R.ok();
	}
	
	
	@SysLog("修改序号")
	@RequestMapping("/updateSortNum")
	@RequiresPermissions("touch:game:update")
	public R updateSortNum(GameInfo game){
		try {
			if(Integer.valueOf(game.getOrder_num()) > 100){
				throw new TouchException("序号长度不得大于100");
			}
			gameService.update(game);
		} catch(TouchException e){
			log.error(e.getMessage(), e);
			return R.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改序号异常");
		}
		return R.ok();
	}
	
}
