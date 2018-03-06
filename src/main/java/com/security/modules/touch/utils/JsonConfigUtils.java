package com.security.modules.touch.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读取config.json 配置文件信息
 * 
 * @author lixiaoxin
 */
public class JsonConfigUtils {
	
	private final static Map<String, String> map = new HashMap<String, String>();
	private final static Map<String, List<Map<String, String>>> dictMap = new HashMap<String, List<Map<String, String>>>();
	private static Logger log = LoggerFactory.getLogger(JsonConfigUtils.class);
	private static JSONObject jsonObject;
	
	
	static{
		File file = new File(JsonConfigUtils.class.getClassLoader().getResource("config.json").getFile());
		try {
			String content = FileUtils.readFileToString(file,"UTF-8");
	        jsonObject = new JSONObject(content);
	        @SuppressWarnings("unchecked")
			Iterator<String> keys = jsonObject.keys();
	        while(keys.hasNext()){
	        	String key = keys.next();
	        	JSONArray array = (JSONArray) jsonObject.get(key);
	        	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	        	dictMap.put(key, list);
	        	for(int i = 0; i < array.length(); i++){
	        		JSONObject object = (JSONObject) array.get(i);
	        		Map<String, String> dict = new HashMap<>();
	        		dict.put("KEY", object.getString("KEY"));
	        		dict.put("VALUE", object.getString("VALUE"));
	        		list.add(dict);
	        		map.put(key + "_" + object.getString("KEY"), object.getString("VALUE"));
	        	}
	        }
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 获取中文名称
	 * 
	 * @param dictCode
	 * @param childDictVal
	 */
	public static String get(String dictCode, String childDictVal) {
		return map.get(dictCode + "_" + childDictVal);
	}
	
	/**
	 * 获取数据字典列表
	 * 
	 * @param dictCode
	 */
	public static List<Map<String, String>> getDictValue(String dictCode){
		return dictMap.get(dictCode);
	}
	
	public static void main(String[] args) {
		System.out.println(get("STATUS", "0"));
	}
	
}
