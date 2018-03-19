package com.security.modules.touch.entity;

import java.io.Serializable;

public class ProdCatEntity  extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2613914308138895901L;
	
	private int cate_id;
	private String cate_name;
	private String cate_coversrc;
	
	public int getCate_id() {
		return cate_id;
	}
	public void setCate_id(int cate_id) {
		this.cate_id = cate_id;
	}
	public String getCate_name() {
		return cate_name;
	}
	public void setCate_name(String cate_name) {
		this.cate_name = cate_name;
	}
	public String getCate_coversrc() {
		return cate_coversrc;
	}
	public void setCate_coversrc(String cate_coversrc) {
		this.cate_coversrc = cate_coversrc;
	}
	

}
