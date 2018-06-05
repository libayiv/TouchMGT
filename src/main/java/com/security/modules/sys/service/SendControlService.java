package com.security.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.alibaba.fastjson.JSONObject;
import com.security.modules.sys.entity.SendEntity;


public interface SendControlService {

SendEntity queryObject(Long id);
	
	SendEntity queryEntity(String pid) throws Exception;
	
	List<SendEntity> queryList(Map<String, Object> map);
	
	List<SendEntity> queryPageList(Map<String, Object> map, RowBounds rowBounds) throws Exception;
	/**
	 * 修改状态
	 * 
	 * @param pids  多个pid
	 * @param status  状态
	 */
	void updateStatus(String pids, String status) throws Exception;

	int queryTotal(Map<String, Object> map);
	
	void save(SendEntity file);
	
	void update(SendEntity file);
	
	void delete(Long id);
	
	void deleteBatch(String[] ids);
	
	JSONObject validateSendCtrl(String auth_code);

}
