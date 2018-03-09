package com.security.modules.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.common.utils.CommonUtils;
import com.security.modules.sys.dao.MessageDao;
import com.security.modules.sys.entity.MessageInfo;
import com.security.modules.sys.service.MessageService;
import com.security.modules.touch.dao.BFBannerDao;
/**
 * 
 * @说明  消息配置service实现类
 * @author hejun
 * @时间 2018年3月6日 下午5:11:31
 */
@Service("MessageService")
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private MessageDao messageDao;
	@Override
	public List<MessageInfo> queryPageList(Map<String, Object> map, RowBounds rowBounds) {
		return messageDao.queryList(map, rowBounds);
	}

	@Override
	public int count(Map<String, Object> map) {
		return messageDao.queryTotal();
	}

	@Override
	public void save(MessageInfo message) {
		message.setId(CommonUtils.getRandomId());
		messageDao.save(message);
	}

	@Override
	public void update(MessageInfo message) {
		messageDao.update(message);	
	}

	@Override
	public void deleteBatch(String[] pids) {
		messageDao.deleteBatch(pids);
	}

	@Override
	public MessageInfo queryEntity(String pid) {
		return messageDao.queryObject(pid);
	}

	@Override
	public void sendMsg(String[] pids) {
		// TODO Auto-generated method stub
		//  获取未发送信息的MessageInfo
		List<MessageInfo> list = messageDao.queryHandSendList(pids);
		for(int index = 0 ; index<=list.size() ; index++){
			MessageInfo msg = list.get(index);
			if( msg.getAcc_type() == 1 ){//全部
				
			}else if(msg.getAcc_type() == 2){ //人员
				
			}else{ // 取值范围
				
			}	
		}
	}

}
