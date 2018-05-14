package com.security.modules.africa.service;

import java.util.List;

import com.security.modules.africa.entity.BFAfricaSCB_MS;

public interface BFAFricafunctionService {
	List<BFAfricaSCB_MS> queryList() throws Exception;
	int updateSCBMS(BFAfricaSCB_MS scbMs)throws Exception;

}
