package com.security.modules.net.entity;

/**
 * @ClassName: GameInfo 
 * @说明: 积分详情表
 * @author: lbx
 * @date: 2018年7月9日 下午2:34:20 
 */
public class IntegralLog {	
	private String insertDate;
	private String recordId;     
	private String  associatedId ;
	private String userId;       
	private String  intType ;      
	private String  intEvent;      
	private String  intEventType; 
	private String  intFront;     
	private String  intInfo;      
	private String  intBack;
	private String detailed;
	private String integral;
	private String memb_name;
	private String time;
	
	
	public String getMemb_name() {
		return memb_name;
	}
	public void setMemb_name(String memb_name) {
		this.memb_name = memb_name;
	}
	public String getDetailed() {
		return detailed;
	}
	public void setDetailed(String detailed) {
		this.detailed = detailed;
	}
	public String getIntegral() {
		return integral;
	}
	public void setIntegral(String integral) {
		this.integral = integral;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getAssociatedId() {
		return associatedId;
	}
	public void setAssociatedId(String associatedId) {
		this.associatedId = associatedId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIntType() {
		return intType;
	}
	public void setIntType(String intType) {
		this.intType = intType;
	}
	public String getIntEvent() {
		return intEvent;
	}
	public void setIntEvent(String intEvent) {
		this.intEvent = intEvent;
	}
	public String getIntEventType() {
		return intEventType;
	}
	public void setIntEventType(String intEventType) {
		this.intEventType = intEventType;
	}
	public String getIntFront() {
		return intFront;
	}
	public void setIntFront(String intFront) {
		this.intFront = intFront;
	}
	public String getIntInfo() {
		return intInfo;
	}
	public void setIntInfo(String intInfo) {
		this.intInfo = intInfo;
	}
	public String getIntBack() {
		return intBack;
	}
	public void setIntBack(String intBack) {
		this.intBack = intBack;
	}      
	

}
