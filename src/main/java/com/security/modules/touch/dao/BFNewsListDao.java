package com.security.modules.touch.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.security.modules.sys.dao.BaseDao;
import com.security.modules.touch.entity.BFNewsEntity;
import com.security.modules.touch.entity.BFNewsReply;

/**
 * 新闻咨询Dao
 * 
 * @author lbx
 */
public interface BFNewsListDao extends BaseDao<BFNewsEntity> {
	
	/**
	 * 更新状态
	 * 
	 * @param params 必须含有status（状态），list（java.util.List多个pid列表）
	 */
	int updateStatus(Map<String, Object> params) throws Exception;
	
	int updateReply(Map<String, Object> params) throws Exception;

	List<BFNewsReply> queryReply(Map<String, Object> map, RowBounds rowBoounds);
	
	int queryReplyTotal(Map<String, Object> map);
}
