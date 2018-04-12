package com.security.modules.touch.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import com.security.common.validator.group.AddGroup;
import com.security.common.validator.group.UpdateGroup;
import com.security.modules.touch.utils.JsonConfigUtils;


/**
 * 活动中心反馈
 * 
 * @author lbx
 */
public class BFActivityFBack extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1676358666323855155L;
	private String id;
	private String user_id;
	private String activity_id;
	private String status;
	private String content;
	private String pubdate;
	private String memb_name;
	private String memb_photo;
	private String title;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getMemb_name() {
		return memb_name;
	}
	public void setMemb_name(String memb_name) {
		this.memb_name = memb_name;
	}
	public String getMemb_photo() {
		return memb_photo;
	}
	public void setMemb_photo(String memb_photo) {
		this.memb_photo = memb_photo;
	}
	
	
	
	
}
