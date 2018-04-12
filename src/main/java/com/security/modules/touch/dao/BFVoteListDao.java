package com.security.modules.touch.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.security.modules.sys.dao.BaseDao;
import com.security.modules.touch.entity.BFVoteDetail;
import com.security.modules.touch.entity.BFVoteInf;

/**
 * 新闻咨询Dao
 * 
 * @author lbx
 */
public interface BFVoteListDao extends BaseDao<BFVoteInf> {
	
	/**
	 * 更新状态
	 * 
	 * @param params 必须含有status（状态），list（java.util.List多个pid列表）
	 */
	int updateStatus(Map<String, Object> params) throws Exception;
	
	List<BFVoteDetail> queryDetail(Map<String, Object> map, RowBounds rowBounds) throws Exception;
	
	List<BFVoteDetail> queryDetail(Map<String, Object> map) throws Exception;
	
	int queryDetailTotal(Map<String, Object> map)throws Exception;
	
	List<BFVoteDetail> queryUserDetail(Map<String, Object> map, RowBounds rowBounds) throws Exception;
	
	int queryUserTotal(Map<String, Object> map)throws Exception;
	
	void saveDetail(BFVoteDetail bfVoteDetail);
	
	int updateDetail(BFVoteDetail bfVoteDetail) throws Exception;
	
	int updateDetailStatus(Map<String, Object> params) throws Exception;
}
