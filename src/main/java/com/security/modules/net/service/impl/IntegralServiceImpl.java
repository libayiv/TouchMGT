package com.security.modules.net.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.modules.net.dao.IntegralDao;
import com.security.modules.net.dao.MembInfoDao;
import com.security.modules.net.entity.IntegralLog;
import com.security.modules.net.entity.MembInfo;
import com.security.modules.net.service.IntegralService;



@Service
public class IntegralServiceImpl implements IntegralService {
	
	@Autowired
	private IntegralDao integralDao;
	@Autowired
	private MembInfoDao membDao;

	@Override
	public IntegralLog queryEntity(String pid) throws Exception {
		return integralDao.queryObject(pid);
	}

	@Override
	public List<IntegralLog> queryList(Map<String, Object> map) throws Exception {
		return integralDao.queryList(map);
	}
	
	@Override
	public List<IntegralLog> queryPageList(Map<String, Object> map, RowBounds rowBounds) throws Exception {
		return integralDao.queryList(map, rowBounds);
	}


	@Override
	public int count(Map<String, Object> map) throws Exception {
		return integralDao.queryTotal(map);
	}

	@Override
	public void save(IntegralLog carouselInf) throws Exception {
//		carouselInf.setOrder_id(CommonUtils.getRandomId());
//		integralDao.save(carouselInf);;
	}

	@Override
	public void update(IntegralLog carouselInf) throws Exception {
		integralDao.update(carouselInf);
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
			integralDao.updateStatus(params);
		}
	}

	@Override
	public MembInfo queryMemb(String userId) throws Exception {
		
		return membDao.queryObject(userId);
	}

	

}
