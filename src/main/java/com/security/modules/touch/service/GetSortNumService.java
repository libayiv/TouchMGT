package com.security.modules.touch.service;

import java.util.Map;

/**
 * @author zhuyangru
 * @date 2017/12/27
 */
public interface GetSortNumService {
	/**
	 * 返回表中排序列是否重复
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> repeat(Map<String, Object> map) throws Exception;
}
