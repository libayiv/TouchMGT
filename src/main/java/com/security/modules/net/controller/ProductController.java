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
import com.security.common.validator.ValidatorUtils;
import com.security.common.validator.group.AddGroup;
import com.security.common.validator.group.UpdateGroup;
import com.security.modules.net.entity.ProductInfo;
import com.security.modules.net.service.ProductService;
import com.security.modules.sys.controller.AbstractController;
import com.security.modules.touch.entity.BFBannerInf;
import com.security.modules.touch.service.GetSortNumService;




/**
 * App productLogController
 * 
 * @author lbx
 */
@RestController
@RequestMapping("/touch/JProd")
public class ProductController extends AbstractController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private GetSortNumService sortNumService;
	
	private Logger log = LoggerFactory.getLogger(ProductController.class);
	
	
	/**
	 * @说明: 积分兑换日志
	 * @author: lbx
	 * @date: 2018年7月16日 下午4:08:22 
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions("touch:JProd:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<ProductInfo> list = null;
		int total = 0;
		try {
			list = productService.queryPageList(query, new RowBounds(query.getOffset(), query.getLimit()));
			total=productService.count(params);
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
	@RequiresPermissions("touch:JProd:info")
	public R info(@PathVariable("pid") String pid){
		List<ProductInfo> product=null;
		try {
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("pro_id", pid);
			product = productService.queryList(params);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取信息异常");
		}
		return R.ok().put("product", product.get(0));
	}
	
	
	@SysLog("修改序号")
	@RequestMapping("/updateSortNum")
	@RequiresPermissions("touch:JProd:update")
	public R updateSortNum(ProductInfo product){
		try {
			product.setPro_id(product.getId());
			if(Integer.valueOf(product.getOrder_num()) > 100){
				throw new TouchException("序号长度不得大于100");
			}
			productService.update(product);
		} catch(TouchException e){
			log.error(e.getMessage(), e);
			return R.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改序号异常");
		}
		return R.ok();
	}
	

	
	
	@SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("touch:JProd:save")
	public R save(@RequestBody ProductInfo product){
		
		try {
			productService.save(product);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("新增异常");
		}
		
		return R.ok();
	}
	
	
	@SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("touch:JProd:update")
	public R update(@RequestBody ProductInfo product){
		try {
			productService.update(product);
		} catch(TouchException e){
			log.error(e.getMessage(), e);
			return R.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改异常");
		}
		return R.ok();
	}
}
