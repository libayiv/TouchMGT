package com.security.modules.secure.entity;

import java.io.Serializable;

import com.security.modules.secure.utils.JsonConfigUtils;

/**
 * 好医保二维码实体类
 * 
 * @author lbx
 */
public class QrcodeMng  extends BaseBean implements Serializable  {
	
	private static final long serialVersionUID = 3009469211233735826L;
	private String pId;// id
	private String sortNum;//排序
	private String title;//标题
	private String qrcodeImg;//图片地址
	private String platform;//所属平台
	private String status;//状态
	private String qrcodeDesc;//描述
	private String operationId;//操作人ID
	private String operationTime;//操作时间
	private String createId;//创建人ID
	private String createTime;//创建时间
	private String platformStr;// 所属平台  中文

	
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getSortNum() {
		return sortNum;
	}
	public void setSortNum(String sortNum) {
		this.sortNum = sortNum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getQrcodeImg() {
		return qrcodeImg;
	}
	public void setQrcodeImg(String qrcodeImg) {
		this.qrcodeImg = qrcodeImg;
	}
	
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
		this.platformStr = JsonConfigUtils.get("PLATFORM", platform);

	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getQrcodeDesc() {
		return qrcodeDesc;
	}
	public void setQrcodeDesc(String qrcodeDesc) {
		this.qrcodeDesc = qrcodeDesc;
	}
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	public String getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getPlatformStr() {
		return platformStr;
	}

	public void setPlatformStr(String platformStr) {
		this.platformStr = platformStr;
	}

	
}
