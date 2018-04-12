package com.security.modules.touch.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.security.modules.touch.entity.BFProductInf;

public interface BFProductInfService {

	List<BFProductInf> queryPageList(Map<String, Object> map, RowBounds rowBounds);

	int count(Map<String, Object> map);

	void update(BFProductInf product);

	void insert(BFProductInf product);
	
	List<BFProductInf> queryImageList(BFProductInf product);
	
	int updateStar(BFProductInf product);
	
	void updateDetail(BFProductInf product);

	void updateImage(BFProductInf product);
	
	void saveImage(BFProductInf product);
	
	BFProductInf queryEditProduct(BFProductInf product);
	
	int queryDetail(BFProductInf product);
	
	
}
