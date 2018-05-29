package com.security.modules.sys.dao;

import java.util.Map;

import com.security.modules.sys.entity.FileListEntity;
import com.security.modules.sys.entity.SendEntity;

/**
 * 推送控制
 * 
 * @author lbx
 */
public interface SendControlDao extends BaseDao<SendEntity> {
	int updateStatus(Map<String, Object> params) throws Exception;

	SendEntity querySendStatus(String auth_code)throws Exception;
}
