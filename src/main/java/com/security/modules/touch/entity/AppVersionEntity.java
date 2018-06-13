package com.security.modules.touch.entity;

import java.io.Serializable;

public class AppVersionEntity  extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2613914308138895901L;
	
	
	private String	id;
	private String  app_type;
	private String  version_code;
	private String  url;
	private String  intro;
	private String  pubdate;
	private int  status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApp_type() {
		return app_type;
	}
	public void setApp_type(String app_type) {
		this.app_type = app_type;
	}
	public String getVersion_code() {
		return version_code;
	}
	public void setVersion_code(String version_code) {
		this.version_code = version_code;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getPubdate() {
		return pubdate;
	}
	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	

}
