package com.security.modules.touch.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.security.modules.touch.entity.BFActivityApplication;
import com.security.modules.touch.entity.BFActivityFBack;
import com.security.modules.touch.entity.BFActivityList;


/**
 * 活动中心
 * 
 * @author lbx
 */
public interface BFActivityService {

	BFActivityList queryEntity(String pid) throws Exception;
	
	
	List<BFActivityList> queryList(Map<String, Object> map) throws Exception;
	
	
	List<BFActivityList> queryPageList(Map<String, Object> map, RowBounds rowBounds) throws Exception;
	
	
	int count(Map<String, Object> map) throws Exception;
	
	
	void save(BFActivityList activity) throws Exception;
	
	
	void update(BFActivityList activity) throws Exception;
	
	void updateSortNum(BFActivityList activity) throws Exception;
	
	
	void deleteBatch(String[] pids) throws Exception;
	
	
	void updateStatus(String pids, String status) throws Exception;

	List<BFActivityFBack> queryFeedBackList(Map<String, Object> map, RowBounds rowBounds) throws Exception;
	
	List<BFActivityApplication> queryApplicationList(Map<String, Object> map, RowBounds rowBounds) throws Exception;

	int feedCount(Map<String, Object> map) throws Exception;
	
	int appCount(Map<String, Object> map) throws Exception;
	
	void updateFeedStatus(String pids, String status) throws Exception;

}
