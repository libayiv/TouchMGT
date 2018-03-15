package com.security.modules.touch.dao;

import com.security.modules.sys.dao.BaseDao;
import com.security.modules.touch.entity.BFBusCenter;

public interface BFBusCenterDao extends BaseDao<BFBusCenter> {

	BFBusCenter getInfo(String type);

}
