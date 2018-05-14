package com.security.modules.africa.dao;

import java.util.List;

import com.security.common.dataSource.AFRICASqlMapper;
import com.security.modules.africa.entity.BFAfricaSCB_MS;

public interface BFAFricafunctionDao extends AFRICASqlMapper{
	List<BFAfricaSCB_MS> querySCBMS2DayList();
	int updateSCBMS(BFAfricaSCB_MS scbMs);
}
