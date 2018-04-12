package com.security.modules.touch.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.security.modules.sys.dao.BaseDao;
import com.security.modules.touch.entity.BFBannerConfig;
import com.security.modules.touch.entity.BFBannerInf;
import com.security.modules.touch.entity.BFOrderDetail;
import com.security.modules.touch.entity.BFOrderInf;


/**
 * order首页bannerDao
 * 
 * @author lbx
 */
public interface BFOrderListDao extends BaseDao<BFOrderInf> {
	/**
	 * 更新状态
	 * 
	 * @param params 必须含有status（状态），list（java.util.List多个pid列表）
	 */
	int updateStatus(Map<String, Object> params) throws Exception;
	
	List<BFOrderDetail> queryDetailList(Map<String, Object> map, RowBounds rowBoounds);
	
}
