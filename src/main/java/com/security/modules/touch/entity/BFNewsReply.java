package com.security.modules.touch.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import com.security.common.validator.group.AddGroup;
import com.security.common.validator.group.UpdateGroup;
import com.security.modules.touch.utils.JsonConfigUtils;


/**
 * 新闻回复
 * 
 * @author lbx
 */
public class BFNewsReply extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1676358666323855155L;
	private String id;// 编号
	private String news_id;// 新闻编号
	private String user_id;// 用户id
	private String content;// 回复内容
	private String good;//点赞数
	private String pubdate;//发布时间（时间戳）
	private String status;// 状态 0=无效 1=有效
	private String memb_name;
	private String memb_photo;




	

	@Override
	public String toString() {
		return "BFNewsReply [id=" + id + ", news_id=" + news_id + ", user_id=" + user_id + ", content=" + content
				+ ", good=" + good + ", pubdate=" + pubdate + ", status=" + status + ", memb_name=" + memb_name + ", memb_photo=" + memb_photo + "]";
	}







	public String getMemb_photo() {
		return memb_photo;
	}







	public void setMemb_photo(String memb_photo) {
		this.memb_photo = memb_photo;
	}







	public String getId() {
		return id;
	}







	public void setId(String id) {
		this.id = id;
	}







	public String getNews_id() {
		return news_id;
	}







	public void setNews_id(String news_id) {
		this.news_id = news_id;
	}







	public String getUser_id() {
		return user_id;
	}







	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}







	public String getContent() {
		return content;
	}







	public void setContent(String content) {
		this.content = content;
	}







	public String getGood() {
		return good;
	}







	public void setGood(String good) {
		this.good = good;
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







	public String getMemb_name() {
		return memb_name;
	}







	public void setMemb_name(String memb_name) {
		this.memb_name = memb_name;
	}

}
