package com.security.modules.touch.dao;

import java.util.Map;

/**
 * @author zhuyangru
 * @date 2017/12/27
 */
public interface GetSortNumDao {
	
	/**
	 * 返回表中排序列的数量
	 * 
	 * @param map 序号、平台、表名、状态
	 * @return
	 * @throws Exception
	 */
	int queryTotal(Map<String, Object> map) throws Exception;
}
