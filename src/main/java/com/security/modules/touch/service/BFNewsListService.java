package com.security.modules.touch.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.security.modules.touch.entity.BFBannerConfig;
import com.security.modules.touch.entity.BFNewsEntity;
import com.security.modules.touch.entity.BFNewsReply;

/**
 * 新闻咨询service
 * 
 * @author lbx
 */
public interface BFNewsListService {

	BFNewsEntity queryEntity(String pid) throws Exception;
	
	
	List<BFNewsEntity> queryList(Map<String, Object> map) throws Exception;
	
	
	List<BFNewsEntity> queryPageList(Map<String, Object> map, RowBounds rowBounds) throws Exception;
	
	List<BFNewsReply> queryReplyList(Map<String, Object> map, RowBounds rowBounds) throws Exception;
	
	/**
	 * 统计回复数量
	 */
	int Replycount(Map<String, Object> map) throws Exception;
	/**
	 * 统计数量
	 */
	int count(Map<String, Object> map) throws Exception;
	
	/**
	 * 添加
	 */
	void save(BFNewsEntity bannerInf) throws Exception;
	
	/**
	 * 更新
	 */
	void update(BFNewsEntity bannerInf) throws Exception;
	
	/**
	 * 批量删除
	 */
	void deleteBatch(String[] pids) throws Exception;
	
	/**
	 * 修改状态
	 * 
	 * @param pids  多个pid
	 * @param status  状态
	 */
	void updateStatus(String pids,String type,String status) throws Exception;
	
	void updateReply(String pids,String status) throws Exception;

}
