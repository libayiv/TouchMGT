package com.security.modules.touch.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.security.modules.sys.dao.BaseDao;
import com.security.modules.touch.entity.BFBlackList;
import com.security.modules.touch.entity.BFNewsEntity;
import com.security.modules.touch.entity.BFNewsReply;

/**
 * 黑名单Dao
 * 
 * @author wg
 */
public interface BFBlackListDao extends BaseDao<BFBlackList> {
	
	/**
	 * 更新状态
	 * 
	 * @param params 必须含有status（状态），list（java.util.List多个pid列表）
	 */
	void updateBatch(Map<String, Object> params);

	//查找是否存在该membId
	int queryByMmebId(String membId);

}
