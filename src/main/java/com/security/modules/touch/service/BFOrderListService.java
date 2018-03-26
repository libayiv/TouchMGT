package com.security.modules.touch.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.security.modules.touch.entity.BFBannerConfig;
import com.security.modules.touch.entity.BFBannerInf;
import com.security.modules.touch.entity.BFOrderDetail;
import com.security.modules.touch.entity.BFOrderInf;


/**
 * Order首页service
 * 
 * @author lbx
 */
public interface BFOrderListService {

	
	BFOrderInf queryEntity(String pid) throws Exception;
	
	List<BFOrderInf> queryList(Map<String, Object> map) throws Exception;
	

	List<BFOrderInf> queryPageList(Map<String, Object> map, RowBounds rowBounds) throws Exception;
	
	List<BFOrderDetail> queryDetailList(Map<String, Object> map, RowBounds rowBounds) throws Exception;


	int count(Map<String, Object> map) throws Exception;

	void save(BFOrderInf order) throws Exception;
	
	
	void update(BFOrderInf order) throws Exception;
	
	
	void deleteBatch(String[] pids) throws Exception;
	
	void updateStatus(String pids, String status) throws Exception;
	


}
