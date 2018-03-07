package com.security.modules.touch.dao;

import java.util.List;
import java.util.Map;

import com.security.modules.sys.dao.BaseDao;
import com.security.modules.touch.entity.BFBannerConfig;
import com.security.modules.touch.entity.BFBannerInf;


/**
 * app首页bannerDao
 * 
 * @author lbx
 */
public interface BFBannerDao extends BaseDao<BFBannerInf> {

	/**
	 * 更新状态
	 * 
	 * @param params 必须含有status（状态），list（java.util.List多个pid列表）
	 */
	int updateStatus(Map<String, Object> params) throws Exception;
	
	List<BFBannerConfig> queryConfig(Map<String, String> params) throws Exception;
	
	BFBannerInf querySingle() throws Exception;
}
