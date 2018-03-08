package com.security.modules.touch.dao;

import java.util.Map;

import com.security.modules.sys.dao.BaseDao;
import com.security.modules.touch.entity.BFNewsEntity;

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
	
	
}
