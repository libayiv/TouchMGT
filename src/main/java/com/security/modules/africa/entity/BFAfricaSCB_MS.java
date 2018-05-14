package com.security.modules.africa.entity;

import java.io.Serializable;

import com.security.modules.touch.entity.BaseBean;

public class BFAfricaSCB_MS extends BaseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String scb_input_memb_id;
	private String scb_no;
	private String scb_crt_date;
	private String scb_code;
	
	
	public String getScb_code() {
		return scb_code;
	}

	public void setScb_code(String scb_code) {
		this.scb_code = scb_code;
	}

	public String getScb_crt_date() {
		return scb_crt_date;
	}

	public void setScb_crt_date(String scb_crt_date) {
		this.scb_crt_date = scb_crt_date;
	}

	public String getScb_no() {
		return scb_no;
	}

	public void setScb_no(String scb_no) {
		this.scb_no = scb_no;
	}

	public String getScb_input_memb_id() {
		return scb_input_memb_id;
	}

	public void setScb_input_memb_id(String scb_input_memb_id) {
		this.scb_input_memb_id = scb_input_memb_id;
	}


	
}
