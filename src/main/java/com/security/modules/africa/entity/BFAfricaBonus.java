package com.security.modules.africa.entity;

import java.io.Serializable;

import com.security.modules.touch.entity.BaseBean;

public class BFAfricaBonus extends BaseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String bns_code;
	private String end_info;
	private String send_status;
	private String job_bonus_id;
	private String calculate_date;
	
	public String getBns_code() {
		return bns_code;
	}
	public void setBns_code(String bns_code) {
		this.bns_code = bns_code;
	}
	public String getEnd_info() {
		return end_info;
	}
	public void setEnd_info(String end_info) {
		this.end_info = end_info;
	}
	public String getSend_status() {
		return send_status;
	}
	public void setSend_status(String send_status) {
		this.send_status = send_status;
	}
	public String getJob_bonus_id() {
		return job_bonus_id;
	}
	public void setJob_bonus_id(String job_bonus_id) {
		this.job_bonus_id = job_bonus_id;
	}
	public String getCalculate_date() {
		return calculate_date;
	}
	public void setCalculate_date(String calculate_date) {
		this.calculate_date = calculate_date;
	}
	
	
	

	
}
