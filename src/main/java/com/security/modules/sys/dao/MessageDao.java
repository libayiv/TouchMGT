package com.security.modules.sys.dao;

import java.util.List;
import java.util.Map;

import com.security.modules.sys.entity.MessageInfo;
import com.security.modules.sys.entity.SysConfigEntity;
/**
 * 
 * @说明 google消息的配置
 * @author hejun
 * @时间 2018年3月6日 下午3:36:54
 */
public interface MessageDao extends BaseDao<MessageInfo> {
	/**
	 * @说明 获取手动未发送的消息
	 * @param pids
	 * @返回 List<MessageInfo>
	 * @创建者 hejun
	 * @时间 2018年3月8日 下午2:54:48
	 */
	List<MessageInfo> queryHandSendList(String[] pids);
	/**
	 * @说明 获取接受信息的会员id数组
	 * @param paramMap
	 * @返回 List<String>
	 * @创建者 hejun
	 * @时间 2018年3月22日 上午11:46:55
	 */
	List<String> getAcceptMembs(Map<String, Object> paramMap);
	/**
	 * @说明 修改状态为已发送
	 * @param pids
	 * @返回 void
	 * @创建者 hejun
	 * @时间 2018年3月22日 上午11:47:25
	 */
	void updateSendStatus(String[] pids);
	/**
	 * @说明 获取未发送的自动发送类型的消息列表
	 * @返回 List<MessageInfo>
	 * @创建者 hejun
	 * @时间 2018年3月22日 上午11:47:43
	 */
	List<MessageInfo> queryAutoSendList();
	
}
