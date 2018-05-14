package com.security.modules.africa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.modules.africa.dao.BFAFricafunctionDao;
import com.security.modules.africa.entity.BFAfricaSCB_MS;
import com.security.modules.africa.service.BFAFricafunctionService;

@Service
public class BFAFricafunctionServiceImpl implements BFAFricafunctionService {

	@Autowired
	private BFAFricafunctionDao bfAFricafunctionDao;
	@Override
	public List<BFAfricaSCB_MS> queryList() throws Exception {
		// TODO Auto-generated method stub
		return bfAFricafunctionDao.querySCBMS2DayList();
	}
	@Override
	public int updateSCBMS(BFAfricaSCB_MS scbMs) throws Exception {
		// TODO Auto-generated method stub
		return bfAFricafunctionDao.updateSCBMS(scbMs);
	}

}
