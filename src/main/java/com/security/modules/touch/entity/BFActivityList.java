package com.security.modules.touch.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import com.security.common.validator.group.AddGroup;
import com.security.common.validator.group.UpdateGroup;
import com.security.modules.touch.utils.JsonConfigUtils;


/**
 * 活动中心
 * 
 * @author lbx
 */
public class BFActivityList extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1676358666323855155L;
	private String id;
	private String title;
	private String pubdate;
	private String start_time;
	private String end_time;
	private String rank;
	private String status;
	private String views;
	private String stage;
	private String coversrc;
	private String content;
	private String detail_id;
	
	
	public String getDetail_id() {
		return detail_id;
	}
	public void setDetail_id(String detail_id) {
		this.detail_id = detail_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCoversrc() {
		return coversrc;
	}
	public void setCoversrc(String coversrc) {
		this.coversrc = coversrc;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPubdate() {
		return pubdate;
	}
	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getViews() {
		return views;
	}
	public void setViews(String views) {
		this.views = views;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	
	
}
