package com.security.modules.sys.dao;

import java.util.List;

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
	
}
