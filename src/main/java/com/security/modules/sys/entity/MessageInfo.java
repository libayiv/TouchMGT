package com.security.modules.sys.entity;

import java.io.Serializable;

/**
 * 
 * @说明 google 消息推送实体类
 * @author hejun
 * @时间 2018年3月6日 下午3:21:03
 */
public class MessageInfo implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2324808184723038113L;
	/**
	 * 
	 */
	private String id;
	/**
	 * 标签
	 */
	private String tag;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 封面图片
	 */
	private String coversrc;
	/**
	 * 简介
	 */
	private String intro;
	/**
	 * 消息内容（富文本） clob
	 */
	private String content;
	/**
	 * 编辑时间
	 */
	private String pubdate;
	/**
	 * 状态 0无效 1有效
	 */
	private int status;
	/**
	 * 记录收件人名单  all为所有人   多个收件人使用,做分割
	 * 范围：国家【UGANDA】 会员等级【6,7】 PV范围【50,52】 GPV范围【13000,14000】
	 */
	private String acceptor;
	
	private String rank;// 排序编号
	
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
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
	public String getAcceptor() {
		return acceptor;
	}
	public void setAcceptor(String acceptor) {
		this.acceptor = acceptor;
	}
	
	
}
