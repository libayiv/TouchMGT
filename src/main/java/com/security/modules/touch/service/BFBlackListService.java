package com.security.modules.touch.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.security.modules.touch.entity.BFBlackList;

public interface BFBlackListService {
	
	//获取blacklist列表
	List<BFBlackList> queryPageList(Map<String, Object> map, RowBounds rowBounds) ;

	// 统计数量
	int count(Map<String, Object> map);
	
	//保存
	void save(BFBlackList bfBlackList) throws Exception;

	//删除黑名单
	void updateBatch(Map<String, Object> params);

	//查询是否存在该membId
	int queryByMmebId(String membId);

}
