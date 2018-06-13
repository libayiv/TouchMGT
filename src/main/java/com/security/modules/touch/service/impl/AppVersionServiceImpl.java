package com.security.modules.touch.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.security.common.utils.CommonUtils;
import com.security.modules.touch.dao.AppVersionDao;
import com.security.modules.touch.entity.AppVersionEntity;
import com.security.modules.touch.service.AppVersionService;




@Service
public class AppVersionServiceImpl implements AppVersionService {
	@Autowired
	private AppVersionDao appDao;
	
	@Value("${APP_UPLOAD_URL}")
	private String upload;
	
	@Override
	public AppVersionEntity queryObject(Long id){
		return appDao.queryObject(id);
	}
	

	
	@Override
	public void save(AppVersionEntity app){
		app.setId(CommonUtils.getRandomId());
		app.setUrl(upload+app.getUrl());
		appDao.save(app);
	}
	
	@Override
	public void update(AppVersionEntity send){
		appDao.update(send);
	}
	
	@Override
	public void delete(Long id){
		appDao.delete(id);
	}
	
	@Override
	public AppVersionEntity queryEntity() throws Exception {
		return appDao.queryMax();
	}

	
	
}
