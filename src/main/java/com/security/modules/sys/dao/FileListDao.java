package com.security.modules.sys.dao;

import java.util.Map;

import com.security.modules.sys.entity.FileListEntity;

/**
 * 文件上传
 * 
 * @author lbx
 */
public interface FileListDao extends BaseDao<FileListEntity> {
	int updateStatus(Map<String, Object> params) throws Exception;

}
