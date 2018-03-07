package com.security.modules.touch.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.common.utils.CommonUtils;
import com.security.modules.touch.dao.FileListDao;
import com.security.modules.touch.entity.FileListEntity;
import com.security.modules.touch.service.FileListService;




@Service
public class FileListServiceImpl implements FileListService {
	@Autowired
	private FileListDao fileListDao;
	
	@Override
	public FileListEntity queryObject(Long id){
		return fileListDao.queryObject(id);
	}
	
	@Override
	public List<FileListEntity> queryList(Map<String, Object> map){
		return fileListDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return fileListDao.queryTotal(map);
	}
	
	@Override
	public void save(FileListEntity file){
		file.setFile_id(CommonUtils.getRandomId());
		fileListDao.save(file);
	}
	
	@Override
	public void update(FileListEntity file){
		fileListDao.update(file);
	}
	
	@Override
	public void delete(Long id){
		fileListDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		fileListDao.deleteBatch(ids);
	}

	@Override
	public List<FileListEntity> queryPageList(Map<String, Object> map, RowBounds rowBounds) throws Exception {
		
		return fileListDao.queryList(map, rowBounds);

	}

	@Override
	public void updateStatus(String pids, String status) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		List<String> list = new ArrayList<String>();
		String[] split = pids.split(",");
		for(String pid : split){
			if(pid != null && !"".equals(pid.trim())){
				list.add(pid);
			}
		}
		if(list.size() > 0){
			params.put("list", list);
			fileListDao.updateStatus(params);
		}
		
	}

	@Override
	public FileListEntity queryEntity(String pid) throws Exception {
		return fileListDao.queryObject(pid);
	}
	
}
