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
import com.security.common.utils.PageUtils;
import com.security.common.utils.Query;
import com.security.common.utils.R;
import com.security.common.validator.ValidatorUtils;
import com.security.common.validator.group.AddGroup;
import com.security.common.validator.group.UpdateGroup;
import com.security.modules.touch.entity.QrcodeMng;
import com.security.modules.touch.service.QrcodeMngService;


/**
 * 好医保二维码Controller
 * 
 * @author lbx
 */
@RestController
@RequestMapping("/hyb/qrcode")
public class QrcodeMngController {
	
	@Autowired
	private QrcodeMngService qrcodeMngService;
	

	
	private Logger log = LoggerFactory.getLogger(QrcodeMngController.class);
	
	/**
	 * 所有二维码列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("hyb:qrcode:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<QrcodeMng> qrList = null;
		int total = 0;
		try {
			qrList = qrcodeMngService.queryList(query,new RowBounds(query.getOffset(), query.getLimit()));
			total = qrcodeMngService.count(query);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			R.error("获取二维码列表失败");
		}
		PageUtils pageUtil = new PageUtils(qrList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
		
	}
	
	/**
	 * 查询单条数据
	 */
	@RequestMapping("/info/{pId}")
	@RequiresPermissions("hyb:qrcode:info")
	public R info(@PathVariable("pId") String pId){
		QrcodeMng qrcode = null;
		try {
			qrcode=qrcodeMngService.queryEntity(pId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			R.error("获取该数据失败");
		}
		return R.ok().put("qrcode", qrcode);
		
	}
	
	/**
	 * 保存二维码
	 * @throws Exception 
	 */
	@SysLog("保存二维码")
	@RequestMapping("/save")
	@RequiresPermissions("hyb:qrcode:save")
	public R save(@RequestBody QrcodeMng qrcode){
		ValidatorUtils.validateEntity(qrcode, AddGroup.class);
		Map<String, Object> map = new HashMap<String, Object>();//序号查重传入参数		
		map.put("tableName", "QRCODE_INFO"); //表名
		map.put("sortNum", qrcode.getSortNum()); //序号
		map.put("platform", qrcode.getPlatform()); //所属平台	
		try {
			Map<String, Object> repeatMap = new HashMap<String, Object>();
			if("0".equals(repeatMap.get("code").toString())){
				qrcodeMngService.save(qrcode);
			}else{
				return R.error(repeatMap.get("msg").toString());
			}		
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			R.error("增加二维码失败");
		}	
		return R.ok();
	}
	
	/**
	 * 修改二维码
	 * @throws Exception 
	 */
	@SysLog("修改二维码")
	@RequestMapping("/update")
	@RequiresPermissions("hyb:qrcode:update")
	public R update(@RequestBody QrcodeMng qrcode){
		ValidatorUtils.validateEntity(qrcode, UpdateGroup.class);
		Map<String, Object> map = new HashMap<String, Object>();//序号查重传入参数		
		map.put("tableName", "QRCODE_INFO"); //表名
		map.put("sortNum", qrcode.getSortNum()); //序号
		map.put("platform", qrcode.getPlatform()); //所属平台
		try {
			QrcodeMng qrcodeMng = qrcodeMngService.queryEntity(qrcode.getpId());
			if(!qrcodeMng.getSortNum().equals(qrcode.getSortNum()) ){
				Map<String, Object> repeatMap = new HashMap<String, Object>();
				log.info("repeatMap" + repeatMap.toString());
				if("0".equals(repeatMap.get("code").toString())){ //code：0为不存在重复	
					qrcodeMngService.update(qrcode);
				}else{
					return R.error(repeatMap.get("msg").toString());
				}				
			}else{
				qrcodeMngService.update(qrcode);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			R.error("修改二维码失败");
		}	
		return R.ok();
	}
	/**
	 * 修改序号
	 * @throws Exception 
	 */
	@SysLog("修改序号")
	@RequestMapping("/updateNum")
	public R password(QrcodeMng qrcode){
		Map<String, Object> map = new HashMap<String, Object>();//序号查重传入参数		
		map.put("tableName", "QRCODE_INFO"); //表名
		map.put("sortNum", qrcode.getSortNum()); //序号
		map.put("platform", qrcode.getPlatform()); //所属平台
		
		try {
			Map<String, Object> repeatMap = new HashMap<String, Object>();
			if("0".equals(repeatMap.get("code").toString())){ //code：0为不存在重复	
				qrcodeMngService.update(qrcode);
			}else{
				return R.error(repeatMap.get("msg").toString());
			}		
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			R.error("修改二维码失败");
		}
		return R.ok();
	}
	/**
	 * 删除二维码
	 * @throws Exception 
	 */
	@SysLog("删除二维码")
	@RequestMapping("/delete")
	@RequiresPermissions("hyb:qrcode:delete")
	public R delete(@RequestBody String[] pIds) throws Exception{
		qrcodeMngService.deleteByStatus(pIds);
		return R.ok();
	}
	
	/**
	 * 修改状态
	 */
	@SysLog("修改状态")
	@RequestMapping("/updateStatus")
	@RequiresPermissions("hyb:qrcode:update")
	public R updateStatus(@RequestBody Map<String, Object> params){		
		try {
			QrcodeMng qrcodeMng=new QrcodeMng();
			String status = params.get("status").toString();
			String pids = params.get("pId").toString();
			qrcodeMng.setStatus(status);
			qrcodeMng.setpId(pids);
			//由不显示转显示时，校验序号是否重复
			if("1".equals(status)){
				Map<String, Object> map = new HashMap<String, Object>();//序号查重传入参数		
				map.put("tableName", "QRCODE_INFO"); //表名
				map.put("sortNum", params.get("sortNum").toString()); //序号
				map.put("platform", params.get("platform").toString()); //所属平台
				
				Map<String, Object> repeatMap = new HashMap<String, Object>();
				if("0".equals(repeatMap.get("code").toString())){ //code：0为不存在重复				
					
					qrcodeMngService.update(qrcodeMng);				
				}else{
					return R.error(repeatMap.get("msg").toString());
				}
			}else{
				qrcodeMngService.update(qrcodeMng);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改二维码异常");
		}
		return R.ok();
	}	

}
