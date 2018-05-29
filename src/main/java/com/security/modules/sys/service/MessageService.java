package com.security.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import com.security.modules.sys.entity.MessageInfo;


public interface MessageService {
	/**
	 * @说明 查询消息记录  分页
	 * @param map
	 * @param rowBounds
	 * @返回 List<MessageInfo>
	 * @创建者 hejun
	 * @时间 2018年3月22日 上午11:39:35
	 */
	List<MessageInfo> queryPageList(Map<String, Object> map, RowBounds rowBounds);
	/**
	 * @说明 获取总数
	 * @param map
	 * @返回 int
	 * @创建者 hejun
	 * @时间 2018年3月22日 上午11:39:18
	 */
	int count(Map<String, Object> map);

	void save(MessageInfo message);

	void update(MessageInfo message);
	/**
	 * @说明 批量删除
	 * @param pids
	 * @返回 void
	 * @创建者 hejun
	 * @时间 2018年3月22日 上午11:38:43
	 */
	void deleteBatch(String[] pids);
	/**
	 * @说明	获取消息详细
	 * @param pid
	 * @返回 MessageInfo
	 * @创建者 hejun
	 * @时间 2018年3月22日 上午11:38:59
	 */
	MessageInfo queryEntity(String pid);
	/**
	 * @说明 手动发送信息
	 * @param pids
	 * @返回 void
	 * @创建者 hejun
	 * @时间 2018年3月22日 上午11:36:24
	 */
	void sendMsg(String[] pids);

	/**
	 * @说明 自动发送消息
	 * @返回 void
	 * @创建者 hejun
	 * @时间 2018年3月22日 上午11:38:22
	 */
	void autoSendMsg();
	
	void addDetail(Map<String,Object> membParam);
	
	void updateSendStatus(String[] pids);
	
	/**
	 * @说明 查询所有人的regis_id
	 * @返回 Map<String, String>
	 * @创建者 lbx
	 */
	List<Map<String, Object>> getBonusMembs(Map<String, String> param);
	
	List<MessageInfo> queryMesBonus(String tag);
}
