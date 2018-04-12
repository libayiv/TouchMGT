package com.security.modules.touch.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.security.modules.sys.dao.BaseDao;
import com.security.modules.touch.entity.BFActivityApplication;
import com.security.modules.touch.entity.BFActivityFBack;
import com.security.modules.touch.entity.BFActivityList;


/**
 * 活动列表
 * 
 * @author lbx
 */
public interface BFActivityDao extends BaseDao<BFActivityList> {

	/**
	 * 更新状态
	 * 
	 * @param params 必须含有status（状态），list（java.util.List多个pid列表）
	 */
	int updateStatus(Map<String, Object> params) throws Exception;
	
	void saveDetail(BFActivityList activity);
	
	void updateDetail(BFActivityList activity);
	
	List<BFActivityFBack> queryFeedBackList(Map<String, Object> map, RowBounds rowBoounds);
	
	List<BFActivityApplication> queryApplicationList(Map<String, Object> map, RowBounds rowBoounds);

	int queryFTotal(Map<String, Object> map);
	
	int queryAppTotal(Map<String, Object> map);
	
	int updateFeedStatus(Map<String, Object> params) throws Exception;



}
