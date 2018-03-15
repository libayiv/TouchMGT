package com.security.modules.touch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.modules.touch.dao.BFBusCenterDao;
import com.security.modules.touch.entity.BFBusCenter;
import com.security.modules.touch.service.BFBusCenterService;
@Service
public class BFBusCenterServiceImpl implements BFBusCenterService {
	@Autowired
	BFBusCenterDao busCenterDao;
	
	@Override
	public BFBusCenter getInfo(String type) {
		// TODO Auto-generated method stub
		return busCenterDao.getInfo(type);
	}

}
