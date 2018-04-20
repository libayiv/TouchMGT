package com.security.modules.touch.dao;

import com.security.modules.sys.dao.BaseDao;
import com.security.modules.touch.entity.ProdCatEntity;
/**
 * 
 * @说明 产品分类 Dao
 * @author hejun
 * @时间 2018年3月16日 下午2:28:49
 */
public interface ProdCatDao extends BaseDao<ProdCatEntity> {
	ProdCatEntity queryBFCat(Object id);
}
