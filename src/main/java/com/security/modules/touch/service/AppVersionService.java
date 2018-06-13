package com.security.modules.touch.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.security.modules.touch.entity.AppVersionEntity;


public interface AppVersionService {

		AppVersionEntity queryObject(Long id);
	
	AppVersionEntity queryEntity() throws Exception;
	
	

	
	void save(AppVersionEntity app);
	
	void update(AppVersionEntity app);
	
	void delete(Long id);
	
	
	

}
