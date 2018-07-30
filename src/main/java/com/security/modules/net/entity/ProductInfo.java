package com.security.modules.net.entity;

/**
 * @ClassName: ProductInfo 
 * @说明: TODO
 * @author: lbx
 * @date: 2018年7月9日 下午2:34:20 
 */
public class ProductInfo {
	private String pro_id;
	private String pro_name;
	private String pro_picture;
	private String pro_integral;
	private String stock;
	private String limit_number;
	private String status;
	private String order_num;
	private String buy_count;
	private String id;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBuy_count() {
		return buy_count;
	}
	public void setBuy_count(String buy_count) {
		this.buy_count = buy_count;
	}
	public String getPro_id() {
		return pro_id;
	}
	public void setPro_id(String pro_id) {
		this.pro_id = pro_id;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public String getPro_picture() {
		return pro_picture;
	}
	public void setPro_picture(String pro_picture) {
		this.pro_picture = pro_picture;
	}
	public String getPro_integral() {
		return pro_integral;
	}
	public void setPro_integral(String pro_integral) {
		this.pro_integral = pro_integral;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getLimit_number() {
		return limit_number;
	}
	public void setLimit_number(String limit_number) {
		this.limit_number = limit_number;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrder_num() {
		return order_num;
	}
	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}

}
