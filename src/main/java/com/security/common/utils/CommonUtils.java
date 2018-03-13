package com.security.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
/**
 * 公共类
 *  
 */
public class CommonUtils{
	
	 /** 
     * 获取指定位数的随机数 
     * @param num 
     * @return 
     */  
    public static String getRandom(int num){  
        Random random = new Random();  
        StringBuilder sb = new StringBuilder();  
        for(int i = 0;i < num; i++){  
            sb.append(String.valueOf(random.nextInt(10)));  
        }  
        return sb.toString();  
    }  
    /** 
     * 获取名称后缀 
     * @param name 
     * @return 
     */  
    public static String getExt(String name){  
        if(name == null || "".equals(name) || !name.contains("."))  
            return "";  
        return name.substring(name.lastIndexOf(".")+1);  
    }  
	/**
	 * 获取32位随机数字
	 * 
	 */
	public static String getRandomId(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "").toUpperCase();
	}
	
	/**
	 * 
	 */
	public static Map<String,Object> getAcceptorParams(String acceptors){
		Map<String,Object> paramMap = new HashMap<String, Object>();
		String[] conditions = acceptors.split(" ");
		for(String condition : conditions){
			if(condition.contains("PV")){
				String[] pvRange = condition.substring(condition.indexOf("["), condition.length()-1).split(",");
				if("0".equals(pvRange[1]) && "0".equals(pvRange[0])){
					continue;
				}else{
					paramMap.put("minPv", Integer.parseInt(pvRange[0]));
					paramMap.put("maxPv", Integer.parseInt(pvRange[1]));
				}
			}
			if(condition.contains("国家")){
				String[] countrys  = condition.substring(condition.indexOf("["), condition.length()-1).split(",");
				List<String> countryList = new ArrayList<String>();
				for(String country:countrys){
					countryList.add(country);
				}
				paramMap.put("compList", countryList);
			}
			if(condition.contains("会员等级")){
				String[] ranksRange  = condition.substring(condition.indexOf("["), condition.length()-1).split(",");
				if("0".equals(ranksRange[1]) && "0".equals(ranksRange[0])){
					continue;
				}else{
					paramMap.put("minRank", Integer.parseInt(ranksRange[0]));
					paramMap.put("maxRank", Integer.parseInt(ranksRange[1]));
				}
			}
			if(condition.contains("GPV")){
				String[] gpvRange  = condition.substring(condition.indexOf("["), condition.length()-1).split(",");
				if("0".equals(gpvRange[1]) && "0".equals(gpvRange[0])){
					continue;
				}else{
					paramMap.put("minGpv", Integer.parseInt(gpvRange[0]));
					paramMap.put("maxGpv", Integer.parseInt(gpvRange[1]));
				}
			}
		}
		return paramMap;
	}
}
