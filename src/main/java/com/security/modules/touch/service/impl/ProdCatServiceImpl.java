package com.security.modules.touch.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.modules.touch.dao.ProdCatDao;
import com.security.modules.touch.entity.ProdCatEntity;
import com.security.modules.touch.service.ProdCatService;

@Service
public class ProdCatServiceImpl implements ProdCatService {

	@Autowired
	ProdCatDao categoryDao;
	
	@Override
	public List<ProdCatEntity> queryPageList(Map<String, Object> map, RowBounds rowBounds) {
		return categoryDao.queryList(map, rowBounds);
	}

	@Override
	public int count(Map<String, Object> map) {
		return categoryDao.queryTotal(map);
	}

	@Override
	public void update(ProdCatEntity category) {
		categoryDao.update(category);
	}

	@Override
	public ProdCatEntity queryEntity(String pid) {
		// TODO Auto-generated method stub
		return categoryDao.queryObject(pid);
	}
	
}
