package com.security.modules.touch.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.common.utils.Query;
import com.security.modules.touch.entity.BFBlackList;
import com.security.modules.touch.dao.BFBlackListDao;
import com.security.modules.touch.service.BFBlackListService;

@Service
public class BFBlackListServiceImpl implements BFBlackListService{
	@Autowired
	private BFBlackListDao bfBlackListDao;
	
	@Override
	public List<BFBlackList> queryPageList(Map<String, Object> map, RowBounds rowBounds) {
		return bfBlackListDao.queryList(map, rowBounds);
	}

	@Override
	public int count(Map<String, Object> map) {
		return bfBlackListDao.queryTotal(map);
	}

	@Override
	public void save(BFBlackList bfBlackList) throws Exception {
		bfBlackListDao.save(bfBlackList);
	}

	@Override
	public void updateBatch(Map<String, Object> params) {
		List<String> list = new ArrayList<String>();
		String[] split = params.get("ids").toString().split(",");
		for(String pid : split){
			if(pid != null && !"".equals(pid.trim())){
				list.add(pid);
			}
		}
		params.put("ids", list);
		bfBlackListDao.updateBatch(params);
	}

	@Override
	public int queryByMmebId(String membId) {
		
		return bfBlackListDao.queryByMmebId(membId);
	}
	
}
