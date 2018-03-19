package com.security.modules.touch.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.security.modules.touch.entity.ProdCatEntity;

public interface ProdCatService {

	List<ProdCatEntity> queryPageList(Map<String, Object> map, RowBounds rowBounds);

	int count(Map<String, Object> map);

	void update(ProdCatEntity category);

	ProdCatEntity queryEntity(String pid);

}
