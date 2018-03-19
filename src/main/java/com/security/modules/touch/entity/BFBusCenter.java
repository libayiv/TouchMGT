package com.security.modules.touch.entity;

import java.io.Serializable;

public class BFBusCenter extends BaseBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6800641383859773906L;
	private String type;
	private String title;
	private String content;
	private String pubdate;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPubdate() {
		return pubdate;
	}
	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}
	
	
}
