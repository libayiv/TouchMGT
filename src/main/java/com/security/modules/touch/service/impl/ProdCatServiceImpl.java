package com.security.modules.touch.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.qiniu.util.StringUtils;
import com.security.modules.touch.dao.ProdCatDao;
import com.security.modules.touch.entity.ProdCatEntity;
import com.security.modules.touch.service.ProdCatService;

@Service
public class ProdCatServiceImpl implements ProdCatService {

	@Autowired
	ProdCatDao categoryDao;
	
	@Value("${FILE_URL_PATH}")
	private String fileUrlPath;
	
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
		ProdCatEntity cat=categoryDao.queryBFCat(String.valueOf(category.getCate_id()));
		if(cat!=null){
			categoryDao.update(category);
		}else{
			categoryDao.save(category);
		}
	}

	@Override
	public ProdCatEntity queryEntity(String pid) {
		// TODO Auto-generated method stub
		ProdCatEntity BFcat=categoryDao.queryBFCat(pid);
		ProdCatEntity AFcat=categoryDao.queryObject(pid);
		if(BFcat==null){
			AFcat.setCate_coversrc("http://www.crc360.cn/upload/thumb/default.jpg");
		}else{
			AFcat.setCate_coversrc(fileUrlPath+BFcat.getCate_coversrc());
		}
		return AFcat;
	}
	
}
