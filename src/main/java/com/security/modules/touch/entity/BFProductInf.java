package com.security.modules.touch.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import com.security.common.validator.group.AddGroup;
import com.security.common.validator.group.UpdateGroup;
import com.security.modules.touch.utils.JsonConfigUtils;


/**
 * order首页banner
 * 
 * @author lbx
 */
public class BFProductInf extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1676358666323855155L;
	private String product_code;
	private String product_photo;
	private String product_cate;
	private String product_name;
	private String product_id;
	private String product_intro;
	private String product_instruction;
	private String star_num;
	private String part1;
	private String part2;
	private String part3;
	private String part4;
	private String part5;
	private String web_content;
	private String is_star;
	private String is_coversrc;
	private String image_id;
	private String rank;
	private String status;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getImage_id() {
		return image_id;
	}
	public void setImage_id(String image_id) {
		this.image_id = image_id;
	}
	public String getIs_coversrc() {
		return is_coversrc;
	}
	public void setIs_coversrc(String is_coversrc) {
		this.is_coversrc = is_coversrc;
	}
	public String getProduct_intro() {
		return product_intro;
	}
	public void setProduct_intro(String product_intro) {
		this.product_intro = product_intro;
	}
	public String getProduct_instruction() {
		return product_instruction;
	}
	public void setProduct_instruction(String product_instruction) {
		this.product_instruction = product_instruction;
	}
	public String getStar_num() {
		return star_num;
	}
	public void setStar_num(String star_num) {
		this.star_num = star_num;
	}
	public String getPart1() {
		return part1;
	}
	public void setPart1(String part1) {
		this.part1 = part1;
	}
	public String getPart2() {
		return part2;
	}
	public void setPart2(String part2) {
		this.part2 = part2;
	}
	public String getPart3() {
		return part3;
	}
	public void setPart3(String part3) {
		this.part3 = part3;
	}
	public String getPart4() {
		return part4;
	}
	public void setPart4(String part4) {
		this.part4 = part4;
	}
	public String getPart5() {
		return part5;
	}
	public void setPart5(String part5) {
		this.part5 = part5;
	}
	public String getWeb_content() {
		return web_content;
	}
	public void setWeb_content(String web_content) {
		this.web_content = web_content;
	}
	public String getIs_star() {
		return is_star;
	}
	public void setIs_star(String is_star) {
		this.is_star = is_star;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getProduct_photo() {
		return product_photo;
	}
	public void setProduct_photo(String product_photo) {
		this.product_photo = product_photo;
	}
	public String getProduct_cate() {
		return product_cate;
	}
	public void setProduct_cate(String product_cate) {
		this.product_cate = product_cate;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	
	

}
