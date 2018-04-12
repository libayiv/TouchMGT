package com.security.modules.touch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.common.utils.CommonUtils;
import com.security.modules.touch.dao.BFOrderListDao;
import com.security.modules.touch.entity.BFOrderDetail;
import com.security.modules.touch.entity.BFOrderInf;
import com.security.modules.touch.service.BFOrderListService;



@Service
public class BFOrderListServiceImpl implements BFOrderListService {
	
	@Autowired
	private BFOrderListDao bfOrderListDao;

	@Override
	public BFOrderInf queryEntity(String pid) throws Exception {
		return bfOrderListDao.queryObject(pid);
	}

	@Override
	public List<BFOrderInf> queryList(Map<String, Object> map) throws Exception {
		return bfOrderListDao.queryList(map);
	}
	
	@Override
	public List<BFOrderInf> queryPageList(Map<String, Object> map, RowBounds rowBounds) throws Exception {
		return bfOrderListDao.queryList(map, rowBounds);
	}


	@Override
	public int count(Map<String, Object> map) throws Exception {
		return bfOrderListDao.queryTotal(map);
	}

	@Override
	public void save(BFOrderInf order) throws Exception {
		order.setOrder_id(CommonUtils.getRandomId());
		bfOrderListDao.save(order);;
	}

	@Override
	public void update(BFOrderInf order) throws Exception {
		bfOrderListDao.update(order);
	}

	@Override
	public void deleteBatch(String[] pids) throws Exception {
		bfOrderListDao.deleteBatch(pids);
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
			bfOrderListDao.updateStatus(params);
		}
	}

	@Override
	public List<BFOrderDetail> queryDetailList(Map<String, Object> map, RowBounds rowBounds) throws Exception {
		return bfOrderListDao.queryDetailList(map, rowBounds);

	}



}
