package com.security.modules.touch.dao;

import java.util.List;

import com.security.modules.sys.dao.BaseDao;
import com.security.modules.touch.entity.BFProductInf;
/**
 * 
 * @说明 产品 Dao
 * @author lbx
 * 
 */
public interface BFProductDao extends BaseDao<BFProductInf> {
	List<BFProductInf> queryImageList(BFProductInf product);
	int updateStar(BFProductInf product);
	int updateDetail(BFProductInf product);
	BFProductInf queryEditProduct(BFProductInf product);
	int queryDetail(BFProductInf product);
	int updateImage(BFProductInf product);
	void saveImage(BFProductInf product);
	int queryMaxRank(BFProductInf product);
}
