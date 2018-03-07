package com.security.modules.touch.service;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.security.modules.touch.entity.FileListEntity;

/**
 * 文件上传
 * 
 * @author lbx
 * 
 * 
 */
public interface FileListService {
	
	FileListEntity queryObject(Long id);
	
	FileListEntity queryEntity(String pid) throws Exception;
	
	List<FileListEntity> queryList(Map<String, Object> map);
	
	List<FileListEntity> queryPageList(Map<String, Object> map, RowBounds rowBounds) throws Exception;
	/**
	 * 修改状态
	 * 
	 * @param pids  多个pid
	 * @param status  状态
	 */
	void updateStatus(String pids, String status) throws Exception;

	int queryTotal(Map<String, Object> map);
	
	void save(FileListEntity file);
	
	void update(FileListEntity file);
	
	void delete(Long id);
	
	void deleteBatch(String[] ids);
}
