package com.security.modules.touch.entity;

import java.io.Serializable;



public class BFBlackList extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;// 主键
	private String membId;// 人员ID
	private String createDate; //创建时间
	private String modifyDate; //修改时间
	private String status;// 状态  0删除  1 有效
	private String remark;// 备注
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMembId() {
		return membId;
	}
	public void setMembId(String membId) {
		this.membId = membId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
