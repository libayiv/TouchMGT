package com.security.modules.touch.controller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.codec.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qiniu.util.StringUtils;
import com.security.common.annotation.SysLog;
import com.security.common.utils.PageUtils;
import com.security.common.utils.Query;
import com.security.common.utils.R;
import com.security.common.validator.ValidatorUtils;
import com.security.common.validator.group.UpdateGroup;
import com.security.modules.touch.entity.BFProductInf;
import com.security.modules.touch.service.BFProductInfService;

@RestController
@RequestMapping("/touch/product")
public class BFProductInfController {
	@Autowired
	BFProductInfService productService;
	private Logger log = LoggerFactory.getLogger(BFProductInfController.class);
	
	@Value("${FILE_URL_PATH}")
	private String fileUrlPath;
	/**
	 * 所有产品类型
	 */
	@RequestMapping("/list")
	@RequiresPermissions("touch:product:list")
	public R list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<BFProductInf> productList = null;
		List<BFProductInf>  productImages=null;
		int total = 0;
		try {
			/*FireBaseUtil.pushFCMNotification();*/
			productList = productService.queryPageList(query, new RowBounds(query.getOffset(), query.getLimit()));
			for (BFProductInf bfProductInf : productList) {
				bfProductInf.setIs_coversrc("1");
				productImages=productService.queryImageList(bfProductInf);
				if(productImages.isEmpty()){
					bfProductInf.setProduct_photo("http://www.crc360.cn/upload/thumb/default.jpg");
				}else{
					bfProductInf.setProduct_photo(fileUrlPath+"/image/product/"+productImages.get(0).getProduct_photo()+".JPG");
				}
			}
			total = productService.count(query);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取 所有产品类型 失败");
		}
		PageUtils pageUtil = new PageUtils(productList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 
	 */
	@SysLog("修改产品")
	@RequestMapping("/update")
	@RequiresPermissions("touch:product:update")
	public R update(@RequestBody BFProductInf product){
		ValidatorUtils.validateEntity(product, UpdateGroup.class);
		try {
			byte[] contentByte = Base64.decode(product.getProduct_instruction().getBytes("UTF-8")); 
			byte[] introByte = Base64.decode(product.getProduct_intro().getBytes("UTF-8"));    

			String content = new String(contentByte);
			String intro = new String(introByte);
			
			product.setProduct_intro(intro);
			product.setProduct_instruction(content);
			if(productService.queryDetail(product)>0){
				productService.update(product);
			}else{
				productService.insert(product);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改产品失败");
		}
		return R.ok();
	}
	
	/**
	 * 
	 */
	@SysLog("修改产品web")
	@RequestMapping("/web")
	@RequiresPermissions("touch:product:update")
	public R updateWeb(@RequestBody BFProductInf product){
		try {
			String webContent="<ul class='detail_ul'>%s</ul>";
			String webStr="";
			String webPart="";
			if(!StringUtils.isNullOrEmpty(product.getPart1())){
				byte[] contentByte = Base64.decode(product.getPart1().getBytes("UTF-8"));    
				String part1 = new String(contentByte);
				webStr+="<li data-to-part='part1'>Product Descriptions</li>";
				webPart+="<div id='part1'>"+part1+"</div>";
				product.setPart1(part1);
			}
			if(!StringUtils.isNullOrEmpty(product.getPart2())){
				byte[] contentByte = Base64.decode(product.getPart2().getBytes("UTF-8"));    
				String part2 = new String(contentByte);
				webStr+="<li data-to-part='part2'>Why Us</li>";
				webPart+="<div id='part2'>"+part2+"</div>";
				product.setPart2(part2);
			}
			if(!StringUtils.isNullOrEmpty(product.getPart3())){
				byte[] contentByte = Base64.decode(product.getPart3().getBytes("UTF-8"));    
				String part3 = new String(contentByte);
				webStr+="<li data-to-part='part3'>Supplement Facts</li>";
				webPart+="<div id='part3'>"+part3+"</div>";
				product.setPart3(part3);
			}
			if(!StringUtils.isNullOrEmpty(product.getPart4())){
				byte[] contentByte = Base64.decode(product.getPart4().getBytes("UTF-8"));    
				String part4 = new String(contentByte);
				webStr+="<li data-to-part='part4'>Product Displays</li>";
				webPart+="<div id='part4'>"+part4+"</div>";
				product.setPart4(part4);
			}
			if(!StringUtils.isNullOrEmpty(product.getPart5())){
				byte[] contentByte = Base64.decode(product.getPart5().getBytes("UTF-8"));    
				String part5 = new String(contentByte);
				webStr+="<li data-to-part='part5'>Product Photos</li>";
				webPart+="<div id='part5'>"+part5+"</div>";
				product.setPart5(part5);
			}	
			product.setWeb_content(String.format(webContent, webStr)+webPart);
			if(productService.queryDetail(product)>0){
				productService.updateDetail(product);
			}else{
				productService.insert(product);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("修改产品WEB失败");
		}
		return R.ok();
	}
	/**
	 * 修改产品类型
	 */
	@SysLog("更新为明星产品")
	@RequestMapping("/star")
	@RequiresPermissions("touch:product:update")
	public R updateStar(@RequestBody BFProductInf product){
		try {
			productService.updateStar(product);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("更新为明星产品");
		}
		return R.ok();
	}
	
	/**
	 * 产品详细信息
	 */
	@RequestMapping("/info")
	@RequiresPermissions("touch:product:info")
	public R info(String pid,String code){
		BFProductInf product = new BFProductInf();
		try {
			product.setProduct_id(pid);
			product.setProduct_code(code);
			product = productService.queryEditProduct(product);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取产品信息异常");
		}
		return R.ok().put("product", product);
	}
	
	/**
	 * 产品图片获取
	 */
	@RequestMapping("/image")
	@RequiresPermissions("touch:product:info")
	public R image(String pid,String code){
		BFProductInf product = new BFProductInf();
		List<BFProductInf> list=null;
		try {
			product.setProduct_id(pid);
			product.setProduct_code(code);
			list = productService.queryImageList(product);
			for (BFProductInf bfProductInf : list) {
				bfProductInf.setProduct_photo(fileUrlPath+"/image/product/"+bfProductInf.getProduct_photo()+".JPG");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("获取产品图片异常");
		}
		return R.ok().put("images", list);
	}
	
	/**
	 * 产品图片编辑
	 */
	@RequestMapping("/imgUpdate")
	@RequiresPermissions("touch:product:info")
	public R imgUpdate(@RequestBody BFProductInf product){
		
		try {
			
			productService.updateImage(product);			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("编辑产品图片异常");
		}
		return R.ok();
	}
	
	/**
	 * 产品图片添加
	 */
	@RequestMapping("/imgInsert")
	@RequiresPermissions("touch:product:info")
	public R imgInsert(@RequestBody BFProductInf product){
		
		try {
			productService.saveImage(product);			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error("产品图片添加异常");
		}
		return R.ok();
	}
}
