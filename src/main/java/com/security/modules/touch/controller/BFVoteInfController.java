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

import com.security.common.annotation.SysLog;
import com.security.common.exception.TouchException;
import com.security.common.utils.PageUtils;
import com.security.common.utils.Query;
import com.security.common.utils.R;
import com.security.common.validator.ValidatorUtils;
import com.security.common.validator.group.AddGroup;
import com.security.common.validator.group.UpdateGroup;
import com.security.modules.sys.controller.AbstractController;
import com.security.modules.touch.entity.BFVoteDetail;
import com.security.modules.touch.entity.BFVoteInf;
import com.security.modules.touch.service.BFVoteListService;
import com.security.modules.touch.service.GetSortNumService;




/**
 * 投票 Controller
 * 
 * @author lbx
 */
@RestController
@RequestMapping("/touch/vote")
public class BFVoteInfController extends AbstractController {
	
	@Autowired
	private BFVoteListService bfVoteListService;
	
	@Autowired
	private GetSortNumService sortNumService;
	
	private Logger log = LoggerFactory.getLogger(BFVoteInfController.class);
	
	/**
	 * 所有投票列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("touch:vote:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<BFVoteInf> voteList = null;
		int total = 0;
		try {
			voteList = bfVoteListService.queryPageList(query, new RowBounds(query.getOffset(), query.getLimit()));
			total = bfVoteListService.count(query);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取投票列表失败");
		}
		PageUtils pageUtil = new PageUtils(voteList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 所有投票结果列表
	 */
	@RequestMapping("/result")
	@RequiresPermissions("touch:vote:list")
	public R result(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<BFVoteDetail> voteList = null;
		if(params.get("vote_id")==null){
			return R.ok().put("page", null);
		}
		int total = 0;
		try {
			voteList = bfVoteListService.queryDetail(query, new RowBounds(query.getOffset(), query.getLimit()));
			total = bfVoteListService.detailCount(query);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取投票列表失败");
		}
		PageUtils pageUtil = new PageUtils(voteList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 所有用户结果列表
	 */
	@RequestMapping("/user")
	@RequiresPermissions("touch:vote:list")
	public R user(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<BFVoteDetail> voteList = null;
		if(params.get("detail_id")==null){
			return R.ok().put("page", null);
		}
		int total = 0;
		try {
			voteList = bfVoteListService.queryUserDetail(query, new RowBounds(query.getOffset(), query.getLimit()));
			total = bfVoteListService.userCount(query);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取投票列表失败");
		}
		PageUtils pageUtil = new PageUtils(voteList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	/**
	 * 保存投票
	 */
	@SysLog("保存投票")
	@RequestMapping("/save")
	@RequiresPermissions("touch:vote:save")
	public R save(@RequestBody BFVoteInf vote){
		log.info("vote:{}", vote);
		ValidatorUtils.validateEntity(vote, AddGroup.class);
		log.info("添加投票:{}", vote);
		
		try {
			checkSortNum(vote);	
			bfVoteListService.save(vote);
		} catch(TouchException e){
			log.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("添加投票失败");
		}
		
		return R.ok();
	}
	
	/**
	 * 保存投票
	 */
	@SysLog("保存投票选项")
	@RequestMapping("/saveDetail")
	@RequiresPermissions("touch:vote:save")
	public R saveDetail(@RequestBody BFVoteDetail vote){
		log.info("添加投票选项:{}", vote);		
		try {
			byte[] contentByte = Base64.decode(vote.getContent().getBytes("UTF-8"));    
			String content = new String(contentByte);
			vote.setContent(content);
			bfVoteListService.saveDetail(vote);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("添加投票选项失败");
		}
		
		return R.ok();
	}
	
	
	/**
	 * 修改投票
	 */
	@SysLog("修改投票")
	@RequestMapping("/update")
	@RequiresPermissions("touch:vote:update")
	public R update(@RequestBody BFVoteInf vote){
		ValidatorUtils.validateEntity(vote, UpdateGroup.class);
		try {
			checkSortNum(vote);
			bfVoteListService.update(vote);
		} catch(TouchException e){
			log.error(e.getMessage(), e);
			return R.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改投票异常");
		}
		return R.ok();
	}
	
	/**
	 * 修改投票
	 */
	@SysLog("修改投票选项")
	@RequestMapping("/updateDetail")
	@RequiresPermissions("touch:vote:update")
	public R updateDetail(@RequestBody BFVoteDetail vote){
		ValidatorUtils.validateEntity(vote, UpdateGroup.class);
		try {
			byte[] contentByte = Base64.decode(vote.getContent().getBytes("UTF-8"));    
			String content = new String(contentByte);
			vote.setContent(content);
			bfVoteListService.updateDetail(vote);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改投票选项异常");
		}
		return R.ok();
	}
	/**
	 * 修改投票序号
	 */
	@SysLog("修改投票序号")
	@RequestMapping("/updateSortNum")
	@RequiresPermissions("touch:vote:update")
	public R updateSortNum(BFVoteInf vote){
		try {
		
			checkSortNum(vote);	
			bfVoteListService.update(vote);
		} catch(TouchException e){
			log.error(e.getMessage(), e);
			return R.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改投票序号异常");
		}
		return R.ok();
	}
	
	/**
	 * 校验序号  无效将抛出异常
	 */
	private void checkSortNum(BFVoteInf vote) throws TouchException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", "BFCMS_VOTE_LIST"); //表名
		map.put("rank", vote.getRank()); //序号
		map.put("id", vote.getId());
		Map<String, Object> repeatMap = null;
		if(Integer.valueOf(vote.getRank()) > 100){
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
	 * 删除投票
	 */
	@SysLog("删除投票")
	@RequestMapping("/delete")
	@RequiresPermissions("touch:vote:delete")
	public R delete(@RequestBody String[] pids){
		try {
			bfVoteListService.deleteBatch(pids);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("删除投票异常");
		}
		return R.ok();
	}
	
	/**
	 * 投票信息
	 */
	@RequestMapping("/info/{pid}")
	@RequiresPermissions("touch:vote:info")
	public R info(@PathVariable("pid") String pid){
		BFVoteInf vote = null;
		try {
			vote = bfVoteListService.queryEntity(pid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取投票信息异常");
		}
		return R.ok().put("vote", vote);
	}
	
	/**
	 * 修改投票状态
	 */
	@SysLog("修改投票状态")
	@RequestMapping("/updateStatus")
	@RequiresPermissions("touch:vote:updateStatus")
	public R updateStatus(@RequestBody Map<String, Object> params){
		try {
			log.info("params:{}", params);
			String pids = params.get("pids").toString();
			String status = params.get("status").toString();
			bfVoteListService.updateStatus(pids, status);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改投票状态异常");
		}
		return R.ok();
	}
	/**
	 * 修改投票选项状态
	 */
	@SysLog("修改投票选项状态")
	@RequestMapping("/detailStatus")
	@RequiresPermissions("touch:vote:updateStatus")
	public R detailStatus(@RequestBody Map<String, Object> params){
		try {
			log.info("params:{}", params);
			String pids = params.get("pids").toString();
			String status = params.get("status").toString();
			bfVoteListService.updateDetailStatus(pids, status);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改投票状态异常");
		}
		return R.ok();
	}
	/**
	 * 投票选项信息
	 */
	@RequestMapping("/detail/{pid}")
	@RequiresPermissions("touch:vote:info")
	public R detail(@PathVariable("pid") String pid){
		BFVoteDetail vote = null;
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			map.put("id", pid);
			vote = bfVoteListService.queryDetail(map).get(0);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取投票信息异常");
		}
		return R.ok().put("detail", vote);
	}
}
