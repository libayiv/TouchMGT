package com.security.modules.net.entity;

/**
 * @ClassName: GameInfo 
 * @说明: 积分表
 * @author: lbx
 * @date: 2018年7月9日 下午2:34:20 
 */
public class IntegralInfo {
	private String uid;
	private String totalIntegral;
	private String insert_date;
	private String continuity_days;

	
	
	public String getContinuity_days() {
		return continuity_days;
	}
	public void setContinuity_days(String continuity_days) {
		this.continuity_days = continuity_days;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getTotalIntegral() {
		return totalIntegral;
	}
	public void setTotalIntegral(String totalIntegral) {
		this.totalIntegral = totalIntegral;
	}
	public String getInsert_date() {
		return insert_date;
	}
	public void setInsert_date(String insert_date) {
		this.insert_date = insert_date;
	}
	
	

}
