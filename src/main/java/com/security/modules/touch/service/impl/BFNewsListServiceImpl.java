package com.security.modules.touch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.common.utils.CommonUtils;
import com.security.modules.touch.dao.BFNewsListDao;
import com.security.modules.touch.entity.BFNewsEntity;
import com.security.modules.touch.service.BFNewsListService;

@Service
public class BFNewsListServiceImpl implements BFNewsListService {

	@Autowired
	private BFNewsListDao bfNewsListDao;

	@Override
	public BFNewsEntity queryEntity(String pid) throws Exception {
		return bfNewsListDao.queryObject(pid);
	}

	@Override
	public List<BFNewsEntity> queryList(Map<String, Object> map) throws Exception {
		return bfNewsListDao.queryList(map);
	}
	
	@Override
	public List<BFNewsEntity> queryPageList(Map<String, Object> map, RowBounds rowBounds) throws Exception {
		return bfNewsListDao.queryList(map, rowBounds);
	}


	@Override
	public int count(Map<String, Object> map) throws Exception {
		return bfNewsListDao.queryTotal();
	}

	@Override
	public void save(BFNewsEntity carouselInf) throws Exception {
		carouselInf.setId(CommonUtils.getRandomId());
		bfNewsListDao.save(carouselInf);;
	}

	@Override
	public void update(BFNewsEntity carouselInf) throws Exception {
		bfNewsListDao.update(carouselInf);
	}

	@Override
	public void deleteBatch(String[] pids) throws Exception {
		bfNewsListDao.deleteBatch(pids);
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
			bfNewsListDao.updateStatus(params);
		}
	}

}
