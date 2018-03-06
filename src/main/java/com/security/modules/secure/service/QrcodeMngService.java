package com.security.modules.secure.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.security.modules.secure.entity.QrcodeMng;

/**
 * 好医保友情链接service
 * 
 * @author lbx
 */
public interface QrcodeMngService {
	/**
	 * 查询单个链接信息
	 */
	QrcodeMng queryEntity(String id) throws Exception;
	
	/**
	 * 获取链接列表
	 */
	List<QrcodeMng> queryList(Map<String, Object> map,RowBounds rowBounds) throws Exception;
	
	/**
	 * 统计数量
	 */
	int count(Map<String, Object> map) throws Exception;
	
	/**
	 * 保存
	 */
	void save(QrcodeMng links) throws Exception;
	/**
	 * 更新
	 */
	void update(QrcodeMng links) throws Exception;
	
	/**
	 * 删除
	 */
	void deleteByStatus(String[] pIds) throws Exception;
	
	/**
	 * 前端调用：
	 * 获取可显示列表
	 */
	List<QrcodeMng> queryVisibleList(QrcodeMng links) throws Exception;
}
