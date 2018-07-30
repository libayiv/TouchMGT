package com.security.modules.net.entity;

/**
 * @ClassName: SignLog 
 * @说明: TODO
 * @author: lbx
 * @date: 2018年7月9日 下午2:34:20 
 */
public class SignLog {
	private String record_id;
	private String user_id;
	private String is_continuity;
	private String continuity_days;
	private String insert_date;
	
	public String getRecord_id() {
		return record_id;
	}
	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getIs_continuity() {
		return is_continuity;
	}
	public void setIs_continuity(String is_continuity) {
		this.is_continuity = is_continuity;
	}
	public String getContinuity_days() {
		return continuity_days;
	}
	public void setContinuity_days(String continuity_days) {
		this.continuity_days = continuity_days;
	}
	public String getInsert_date() {
		return insert_date;
	}
	public void setInsert_date(String insert_date) {
		this.insert_date = insert_date;
	}
	
	

}
