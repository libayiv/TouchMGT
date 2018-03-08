package com.security.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import com.security.modules.sys.entity.MessageInfo;


public interface MessageService {

	List<MessageInfo> queryPageList(Map<String, Object> map, RowBounds rowBounds);

	int count(Map<String, Object> map);

	void save(MessageInfo message);

	void update(MessageInfo message);

	void deleteBatch(String[] pids);

	MessageInfo queryEntity(String pid);
	
	void sendMsg(String[] pids);

}
