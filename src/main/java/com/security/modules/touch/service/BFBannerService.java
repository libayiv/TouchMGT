package com.security.modules.touch.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.security.modules.touch.entity.BFBannerConfig;
import com.security.modules.touch.entity.BFBannerInf;


/**
 * APP首页service
 * 
 * @author lbx
 */
public interface BFBannerService {

	/**
	 * 查询单个轮播图信息
	 */
	BFBannerInf queryEntity(String pid) throws Exception;
	
	/**
	 * 获取轮播图列表
	 */
	List<BFBannerInf> queryList(Map<String, Object> map) throws Exception;
	
	/**
	 * 分页获取轮播图列表
	 */
	List<BFBannerInf> queryPageList(Map<String, Object> map, RowBounds rowBounds) throws Exception;
	
	/**
	 * 统计数量
	 */
	int count(Map<String, Object> map) throws Exception;
	
	/**
	 * 添加
	 */
	void save(BFBannerInf bannerInf) throws Exception;
	
	/**
	 * 更新
	 */
	void update(BFBannerInf bannerInf) throws Exception;
	
	/**
	 * 批量删除
	 */
	void deleteBatch(String[] pids) throws Exception;
	
	/**
	 * 修改状态
	 * 
	 * @param pids  多个pid
	 * @param status  状态
	 */
	void updateStatus(String pids, String status) throws Exception;
	
	List<BFBannerConfig> queryConfig(Map<String,String> parmas) throws Exception;

	BFBannerInf querySingle() throws Exception;

}
