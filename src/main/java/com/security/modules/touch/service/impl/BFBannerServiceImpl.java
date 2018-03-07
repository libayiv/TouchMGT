package com.security.modules.touch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.common.utils.CommonUtils;
import com.security.modules.touch.dao.BFBannerDao;
import com.security.modules.touch.entity.BFBannerConfig;
import com.security.modules.touch.entity.BFBannerInf;
import com.security.modules.touch.service.BFBannerService;



@Service
public class BFBannerServiceImpl implements BFBannerService {
	
	@Autowired
	private BFBannerDao BFBannerDao;

	@Override
	public BFBannerInf queryEntity(String pid) throws Exception {
		return BFBannerDao.queryObject(pid);
	}

	@Override
	public List<BFBannerInf> queryList(Map<String, Object> map) throws Exception {
		return BFBannerDao.queryList(map);
	}
	
	@Override
	public List<BFBannerInf> queryPageList(Map<String, Object> map, RowBounds rowBounds) throws Exception {
		return BFBannerDao.queryList(map, rowBounds);
	}


	@Override
	public int count(Map<String, Object> map) throws Exception {
		return BFBannerDao.queryTotal();
	}

	@Override
	public void save(BFBannerInf carouselInf) throws Exception {
		carouselInf.setId(CommonUtils.getRandomId());
		BFBannerDao.save(carouselInf);;
	}

	@Override
	public void update(BFBannerInf carouselInf) throws Exception {
		BFBannerDao.update(carouselInf);
	}

	@Override
	public void deleteBatch(String[] pids) throws Exception {
		BFBannerDao.deleteBatch(pids);
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
			BFBannerDao.updateStatus(params);
		}
	}

	@Override
	public List<BFBannerConfig> queryConfig(Map<String, String> parmas) throws Exception {
		
		return BFBannerDao.queryConfig(parmas);
	}

	@Override
	public BFBannerInf querySingle() throws Exception {
		return BFBannerDao.querySingle();
	}

}
