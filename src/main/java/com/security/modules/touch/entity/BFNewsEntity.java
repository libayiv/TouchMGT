package com.security.modules.touch.entity;

import java.io.Serializable;
import java.util.List;

import com.security.modules.touch.utils.JsonConfigUtils;

/**
 * 新闻咨询实体类
 * 
 * @author lbx
 */
public class BFNewsEntity  extends BaseBean implements Serializable  {
	
	private static final long serialVersionUID = 3009469211233735826L;
	private String id;// 新闻编号
	private String title;//标题
	private String coversrc;//新闻所用图片封面
	private String intro;//新闻简介
	private String content;//新闻详情
	private String views;//新闻阅读量
	private String good;//点赞数
	private String store;//收藏数
	private String reply;//回复数
	private String pubdate;//发布时间（时间戳）
	private String status;// 状态 0=无效 1=有效
	private String is_hot;//是否推荐至首页
	private String tag;//标签 hot
	private String pc_valid;//是否推荐到PC 0=PC  1=APP 2=APP+PC
	private String is_activity;// 是否为活动新闻  0=不是 1是
	
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
	public String getCoversrc() {
		return coversrc;
	}
	public void setCoversrc(String coversrc) {
		this.coversrc = coversrc;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getViews() {
		return views;
	}
	public void setViews(String views) {
		this.views = views;
	}
	public String getGood() {
		return good;
	}
	public void setGood(String good) {
		this.good = good;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public String getPubdate() {
		return pubdate;
	}
	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIs_hot() {
		return is_hot;
	}
	public void setIs_hot(String is_hot) {
		this.is_hot = is_hot;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getPc_valid() {
		return pc_valid;
	}
	public void setPc_valid(String pc_valid) {
		this.pc_valid = pc_valid;
	}
	public String getIs_activity() {
		return is_activity;
	}
	public void setIs_activity(String is_activity) {
		this.is_activity = is_activity;
	}

	
	
	
}
