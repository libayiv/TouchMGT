package com.security.modules.touch.dao;

import java.util.Map;

import com.security.modules.sys.dao.BaseDao;
import com.security.modules.touch.entity.AppVersionEntity;

/**
 * app版本更改
 * 
 * @author lbx
 */
public interface AppVersionDao extends BaseDao<AppVersionEntity> {
	AppVersionEntity queryMax();
}
