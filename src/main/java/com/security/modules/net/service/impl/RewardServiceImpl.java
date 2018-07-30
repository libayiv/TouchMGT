package com.security.modules.net.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.common.utils.CommonUtils;
import com.security.modules.net.dao.MembInfoDao;
import com.security.modules.net.dao.RewardDao;
import com.security.modules.net.entity.MembInfo;
import com.security.modules.net.entity.RewardInfo;
import com.security.modules.net.service.RewardService;



@Service
public class RewardServiceImpl implements RewardService {
	
	@Autowired
	private RewardDao rewardDao;
	@Autowired
	private MembInfoDao membDao;

	@Override
	public RewardInfo queryEntity(String pid) throws Exception {
		return rewardDao.queryObject(pid);
	}

	@Override
	public List<RewardInfo> queryList(Map<String, Object> map) throws Exception {
		return rewardDao.queryList(map);
	}
	
	@Override
	public List<RewardInfo> queryPageList(Map<String, Object> map, RowBounds rowBounds) throws Exception {
		return rewardDao.queryList(map, rowBounds);
	}


	@Override
	public int count(Map<String, Object> map) throws Exception {
		return rewardDao.queryTotal(map);
	}

	@Override
	public void save(RewardInfo carouselInf) throws Exception {
		/*carouselInf.setOrder_id(CommonUtils.getRandomId());
		rewardDao.save(carouselInf);;*/
	}

	@Override
	public void update(RewardInfo carouselInf) throws Exception {
		rewardDao.update(carouselInf);
	}



	@Override
	public void updateStatus(String pids, String status) throws Exception {
		/*Map<String, Object> params = new HashMap<String, Object>();
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
			rewardDao.updateStatus(params);
		}*/
	}

	@Override
	public MembInfo queryMemb(String userId) throws Exception {
		return membDao.queryObject(userId);
	}

	

}
