package com.security.modules.secure.dao;

import java.util.List;

import com.security.modules.secure.entity.QrcodeMng;
import com.security.modules.sys.dao.BaseDao;

/**
 * 好医保二维码Dao
 * 
 * @author lbx
 */
public interface QrcodeMngDao extends BaseDao<QrcodeMng> {
	
	/**
	 * 删除数据改为0
	 */	
	int deleteByStatus(Object[] id);
	
	/**
	 * 可显示列表
	 */	
	List<QrcodeMng> queryVisibleList(QrcodeMng qrcode);
}
