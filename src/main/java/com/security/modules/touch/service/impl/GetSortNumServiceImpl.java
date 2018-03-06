package com.security.modules.touch.service.impl;



import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.modules.touch.dao.GetSortNumDao;
import com.security.modules.touch.service.GetSortNumService;
import com.security.modules.touch.service.exception.ArgumentNullException;
import com.security.modules.touch.utils.ParamValidate;

/**
 * @author lbx
 * 
 */
@Service
public class GetSortNumServiceImpl implements GetSortNumService{
	
	private Logger log = LoggerFactory.getLogger(GetSortNumServiceImpl.class);
	
	@Autowired
	private GetSortNumDao getSortNumDao;
	
	@Override
	public Map<String, Object> repeat(Map<String, Object> map) {		
		Map<String, Object> repeatMap = new HashMap<String, Object>();	
		try {
			ParamValidate.doing(map, "rank","tableName");
			int count = getSortNumDao.queryTotal(map);
			if(count > 0){
				repeatMap.put("code", 500);
				repeatMap.put("msg", "已存在相同序号");
			}else{
				repeatMap.put("code", 0);			
			}			
		} catch (ArgumentNullException e) {
			log.error(e.getMessage(), e);
			repeatMap.put("code", 500);
			repeatMap.put("msg", e.getMessage());
		} catch (Exception e){
			log.error(e.getMessage(), e);
			repeatMap.put("code", 500);
			repeatMap.put("msg", e.getMessage());
		}		
		return repeatMap;	
	}
}
