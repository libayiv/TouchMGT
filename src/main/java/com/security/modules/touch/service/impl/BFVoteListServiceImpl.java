package com.security.modules.touch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.common.utils.CommonUtils;
import com.security.modules.touch.dao.BFVoteListDao;
import com.security.modules.touch.entity.BFVoteDetail;
import com.security.modules.touch.entity.BFVoteInf;
import com.security.modules.touch.service.BFVoteListService;

@Service
public class BFVoteListServiceImpl implements BFVoteListService {

	@Autowired
	private BFVoteListDao bfVoteListDao;

	@Override
	public BFVoteInf queryEntity(String pid) throws Exception {
		return bfVoteListDao.queryObject(pid);
	}

	@Override
	public List<BFVoteInf> queryList(Map<String, Object> map) throws Exception {
		return bfVoteListDao.queryList(map);
	}
	
	@Override
	public List<BFVoteInf> queryPageList(Map<String, Object> map, RowBounds rowBounds) throws Exception {
		return bfVoteListDao.queryList(map, rowBounds);
	}


	@Override
	public int count(Map<String, Object> map) throws Exception {
		return bfVoteListDao.queryTotal(map);
	}

	@Override
	public void save(BFVoteInf voteInf) throws Exception {
		voteInf.setId(CommonUtils.getRandomId());
		bfVoteListDao.save(voteInf);;
	}

	@Override
	public void update(BFVoteInf voteInf) throws Exception {
		bfVoteListDao.update(voteInf);
	}

	@Override
	public void deleteBatch(String[] pids) throws Exception {
		bfVoteListDao.deleteBatch(pids);
	}

	@Override
	public void updateStatus(String pids,String status) throws Exception {
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
			bfVoteListDao.updateStatus(params);
		}
	}

	@Override
	public List<BFVoteDetail> queryDetail(Map<String, Object> map, RowBounds rowBounds) throws Exception {
		return bfVoteListDao.queryDetail(map,rowBounds);
	}

	@Override
	public int detailCount(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return bfVoteListDao.queryDetailTotal(map);
	}

	@Override
	public List<BFVoteDetail> queryUserDetail(Map<String, Object> map, RowBounds rowBounds) throws Exception {
		return bfVoteListDao.queryUserDetail(map, rowBounds);
	}

	@Override
	public int userCount(Map<String, Object> map) throws Exception {
		return bfVoteListDao.queryUserTotal(map);
	}

	@Override
	public void saveDetail(BFVoteDetail bfVoteDetail) {
		bfVoteDetail.setId(UUID.randomUUID().toString().toUpperCase());
		bfVoteListDao.saveDetail(bfVoteDetail);		
	}

	@Override
	public void updateDetail(BFVoteDetail bfVoteDetail) throws Exception {
		bfVoteListDao.updateDetail(bfVoteDetail);
		
	}

	@Override
	public List<BFVoteDetail> queryDetail(Map<String, Object> map) throws Exception {
		return bfVoteListDao.queryDetail(map);
	}

	@Override
	public void updateDetailStatus(String pids, String status) throws Exception {
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
			bfVoteListDao.updateDetailStatus(params);
		}
	}

	

}
