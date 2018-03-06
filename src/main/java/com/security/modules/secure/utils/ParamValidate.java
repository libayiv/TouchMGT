package com.security.modules.secure.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.security.modules.secure.service.exception.ArgumentNullException;

/**
 * Map中的必需字段效验
 * 
 * @author lixiaoxin
 */
public class ParamValidate {
	private static Logger log = LoggerFactory.getLogger(ParamValidate.class);

	/**
	 * 进行效验数据,异常时抛出ArgumentNullException
	 *
	 * @param param
	 *            需要效验的MAP
	 * @param keys
	 *            参数列表key值.
	 * @throws ArgumentNullException
	 */
	public static void doing(Map<String, Object> param, String... keys) throws ArgumentNullException {
		// log.info("必传参数检查开始.......................");
		for (String key : keys) {
			if (!param.containsKey(key) || param.get(key) == null || param.get(key).toString().trim().equals("")
					|| param.get(key).toString().trim().equals("null")) {
				log.error("数据校验失败,字段[{}]为空.", key);
				throw new ArgumentNullException("字段:[" + key + "]不能为空！");
			}
		}
		// log.info("必传参数检查通过.......................");
	}

	/**
	 * @param parameters
	 * @param keys
	 *            需要重组map的key数组
	 * @return LinkedHashMap 按keys数组中的顺序排列
	 */
	public static Map<String, Object> recombinationMap(Map<String, Object> parameters, String... keys) {
		Map<String, Object> results = new LinkedHashMap<String, Object>();
		for (String key : keys) {
			if (parameters.containsKey(key)) {
				results.put(key, parameters.get(key));
			}
		}
		return results;
	}

}
