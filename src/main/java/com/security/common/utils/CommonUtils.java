package com.security.common.utils;

import java.util.UUID;
/**
 * 公共类
 *  
 */
public class CommonUtils{
	/**
	 * 获取32位随机数字
	 * 
	 */
	public static String getRandomId(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "").toUpperCase();
	}
}
