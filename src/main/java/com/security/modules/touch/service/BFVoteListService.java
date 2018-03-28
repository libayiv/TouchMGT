package com.security.modules.touch.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.security.modules.touch.entity.BFVoteDetail;
import com.security.modules.touch.entity.BFVoteInf;

/**
 * 投票service
 * 
 * @author lbx
 */
public interface BFVoteListService {

	BFVoteInf queryEntity(String pid) throws Exception;
	
	
	List<BFVoteInf> queryList(Map<String, Object> map) throws Exception;
	
	
	List<BFVoteInf> queryPageList(Map<String, Object> map, RowBounds rowBounds) throws Exception;
		
	List<BFVoteDetail> queryDetail(Map<String, Object> map, RowBounds rowBounds) throws Exception;

	List<BFVoteDetail> queryDetail(Map<String, Object> map) throws Exception;

	
	List<BFVoteDetail> queryUserDetail(Map<String, Object> map, RowBounds rowBounds) throws Exception;

	/**
	 * 添加
	 */
	void save(BFVoteInf voteInf) throws Exception;
	
	/**
	 * 更新
	 */
	void update(BFVoteInf voteInf) throws Exception;
	
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
	void updateStatus(String pids,String status) throws Exception;
	
	/**
	 * 统计数量
	 */
	int count(Map<String, Object> map) throws Exception;
	
	/**
	 * 统计数量
	 */
	int detailCount(Map<String, Object> map) throws Exception;
	
	int userCount(Map<String, Object> map) throws Exception;
	
	
	void saveDetail(BFVoteDetail bfVoteDetail);
	
	void updateDetail(BFVoteDetail bfVoteDetail) throws Exception;
	
	/**
	 * 修改状态
	 * 
	 * @param pids  多个pid
	 * @param status  状态
	 */
	void updateDetailStatus(String pids,String status) throws Exception;
	
}
