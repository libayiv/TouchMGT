package com.security.modules.net.entity;

/**
 * @ClassName: OrderLog 
 * @说明: TODO
 * @author: lbx
 * @date: 2018年7月9日 下午2:34:20 
 */
public class OrderLog {
	private String record_id;
	private String order_id;
	private String status;
	private String insert_date;
	
	public String getRecord_id() {
		return record_id;
	}
	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInsert_date() {
		return insert_date;
	}
	public void setInsert_date(String insert_date) {
		this.insert_date = insert_date;
	}
	
	

}
