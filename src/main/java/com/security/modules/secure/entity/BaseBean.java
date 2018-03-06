package com.security.modules.secure.entity;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础实体类信息
 * 
 * @author lixiaoxin
 */
public abstract class BaseBean {
	
	/**
	 * bean转map
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public Map<String, Object> toMap() throws IllegalArgumentException, IllegalAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] declaredFields = this.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			field.setAccessible(true);
			map.put(field.getName(), field.get(this));
		}
		return map;
	}
}
