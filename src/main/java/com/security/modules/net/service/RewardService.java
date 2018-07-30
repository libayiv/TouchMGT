package com.security.modules.net.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.security.modules.net.entity.MembInfo;
import com.security.modules.net.entity.RewardInfo;



public interface RewardService {

	/**
	 * 查询积分兑换日志
	 */
	RewardInfo queryEntity(String pid) throws Exception;
	
	MembInfo queryMemb(String userId) throws Exception;

	
	/**
	 * 获取积分兑换日志
	 */
	List<RewardInfo> queryList(Map<String, Object> map) throws Exception;
	
	/**
	 * 分页积分兑换日志
	 */
	List<RewardInfo> queryPageList(Map<String, Object> map, RowBounds rowBounds) throws Exception;
	
	/**
	 * 统计数量
	 */
	int count(Map<String, Object> map) throws Exception;
	
	/**
	 * 添加
	 */
	void save(RewardInfo reward) throws Exception;
	
	/**
	 * 更新
	 */
	void update(RewardInfo reward) throws Exception;
	

	
	/**
	 * 修改状态
	 * 
	 * @param pids  多个pid
	 * @param status  状态
	 */
	void updateStatus(String pids, String status) throws Exception;
	
	

}
