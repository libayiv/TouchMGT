package com.security.modules.touch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.security.common.utils.CommonUtils;
import com.security.modules.touch.dao.BFActivityDao;
import com.security.modules.touch.entity.BFActivityApplication;
import com.security.modules.touch.entity.BFActivityFBack;
import com.security.modules.touch.entity.BFActivityList;
import com.security.modules.touch.service.BFActivityService;



@Service
public class BFActivityServiceImpl implements BFActivityService {
	
	@Autowired
	private BFActivityDao bfActivityDao;

	@Override
	public BFActivityList queryEntity(String pid) throws Exception {
		return bfActivityDao.queryObject(pid);
	}

	@Override
	public List<BFActivityList> queryList(Map<String, Object> map) throws Exception {
		return bfActivityDao.queryList(map);
	}
	
	@Override
	public List<BFActivityList> queryPageList(Map<String, Object> map, RowBounds rowBounds) throws Exception {
		return bfActivityDao.queryList(map, rowBounds);
	}


	@Override
	public int count(Map<String, Object> map) throws Exception {
		return bfActivityDao.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(BFActivityList activity) throws Exception {
		String activityId=CommonUtils.getRandomId();
		activity.setId(activityId);
		String detailId=CommonUtils.getRandomId();
		activity.setDetail_id(detailId);
		bfActivityDao.save(activity);
		bfActivityDao.saveDetail(activity);
	}

	@Override
	public void update(BFActivityList activity) throws Exception {
		bfActivityDao.update(activity);
		bfActivityDao.updateDetail(activity);
	}

	@Override
	public void deleteBatch(String[] pids) throws Exception {
		bfActivityDao.deleteBatch(pids);
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
			bfActivityDao.updateStatus(params);
		}
	}

	@Override
	public void updateSortNum(BFActivityList activity) throws Exception {
		bfActivityDao.update(activity);
	}

	@Override
	public List<BFActivityFBack> queryFeedBackList(Map<String, Object> map, RowBounds rowBounds) throws Exception {
		return bfActivityDao.queryFeedBackList(map, rowBounds);
	}

	@Override
	public List<BFActivityApplication> queryApplicationList(Map<String, Object> map, RowBounds rowBounds)
			throws Exception {
		return bfActivityDao.queryApplicationList(map, rowBounds);
	}

	@Override
	public int feedCount(Map<String, Object> map) throws Exception {
		return bfActivityDao.queryFTotal(map);
	}

	@Override
	public int appCount(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return bfActivityDao.queryAppTotal(map);
	}

	@Override
	public void updateFeedStatus(String pids, String status) throws Exception {
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
			bfActivityDao.updateFeedStatus(params);
		}
		
	}

	

}
