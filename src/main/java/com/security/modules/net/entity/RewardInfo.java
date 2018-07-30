package com.security.modules.net.entity;

/**
 * @ClassName: GameLog 
 * @说明: TODO
 * @author: lbx
 * @date: 2018年7月9日 下午2:34:20 
 */
public class RewardInfo {
	private String record_id;
	private String user_id;
	private String total_integral;
	private String reward_month;
	private String insert_date;
	private String memb_id;
	private String memb_name;
	private String memb_phone_mobile;

	public String getRecord_id() {
		return record_id;
	}
	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	public String getMemb_id() {
		return memb_id;
	}
	public void setMemb_id(String memb_id) {
		this.memb_id = memb_id;
	}
	public String getMemb_name() {
		return memb_name;
	}
	public void setMemb_name(String memb_name) {
		this.memb_name = memb_name;
	}
	public String getMemb_phone_mobile() {
		return memb_phone_mobile;
	}
	public void setMemb_phone_mobile(String memb_phone_mobile) {
		this.memb_phone_mobile = memb_phone_mobile;
	}
	public String getTotal_integral() {
		return total_integral;
	}
	public void setTotal_integral(String total_integral) {
		this.total_integral = total_integral;
	}
	public String getReward_month() {
		return reward_month;
	}
	public void setReward_month(String reward_month) {
		this.reward_month = reward_month;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public String getInsert_date() {
		return insert_date;
	}
	public void setInsert_date(String insert_date) {
		this.insert_date = insert_date;
	}


}
