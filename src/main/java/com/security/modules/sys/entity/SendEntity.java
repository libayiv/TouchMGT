package com.security.modules.sys.entity;

import java.io.Serializable;

public class SendEntity implements Serializable{
	private static final long serialVersionUID = 2324808184723038113L;
	private String id;
	private String operation_id;
	private String operation_name;
	private String create_date;
	private Integer status;
	private String remark;
	private String auth_code;
	
	
	public String getAuth_code() {
		return auth_code;
	}
	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOperation_id() {
		return operation_id;
	}
	public void setOperation_id(String operation_id) {
		this.operation_id = operation_id;
	}
	public String getOperation_name() {
		return operation_name;
	}
	public void setOperation_name(String operation_name) {
		this.operation_name = operation_name;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	


}
