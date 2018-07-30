package com.security.modules.net.dao;

import java.util.Map;

import com.security.common.dataSource.AFRICASqlMapper;
import com.security.modules.net.entity.OrderInfo;
import com.security.modules.sys.dao.BaseDao;


/**
 * 积分兑换日志
 * 
 * @author lbx
 */
public interface ExchangeLogDao extends BaseDao<OrderInfo>,AFRICASqlMapper{

	/**
	 * 更新状态
	 * 
	 * @param params 必须含有status（状态），list（java.util.List多个pid列表）
	 */
	int updateStatus(Map<String, Object> params) throws Exception;
	
	
}
