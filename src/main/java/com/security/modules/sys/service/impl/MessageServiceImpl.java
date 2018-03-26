package com.security.modules.sys.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.security.common.utils.CommonUtils;
import com.security.common.utils.SendMsgUtil;
import com.security.modules.sys.dao.MessageDao;
import com.security.modules.sys.entity.MessageInfo;
import com.security.modules.sys.service.MessageService;
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
	/**
	 * 手动发送信息
	 */
	@Override
	public void sendMsg(String[] pids) {
		//  获取未发送信息的MessageInfo
		List<MessageInfo> list = messageDao.queryHandSendList(pids);
		for(int index = 0 ; index<=list.size() ; index++){
			MessageInfo msg = list.get(index);
			JSONObject sendJson = new JSONObject();
			Map<String,Object> paramMap = new HashMap<String, Object>();
			List<String> membList = new ArrayList<String>();
			if( msg.getAcc_type() == 1 ){//全部
				
			}else if(msg.getAcc_type() == 2){ //人员
				String[] membs  = msg.getAcceptor().split(",");
				for(String membId:membs){
					membList.add(membId);
				}
				paramMap.put("membList", membList);
			}else{ // 取值范围
				String acceptors = msg.getAcceptor();
				paramMap = CommonUtils.getAcceptorParams(acceptors);
			}
			paramMap.put("type", msg.getAcc_type());
			membList = messageDao.getAcceptMembs(paramMap);
			sendJson.put("title", msg.getTitle());
			sendJson.put("body", msg.getIntro());
			SendMsgUtil mUtil = new SendMsgUtil();
			for(String membs:membList){
				mUtil.addQueue(membs);
			}
			mUtil.execute(sendJson);	
		}
		messageDao.updateSendStatus(pids);
	}
	/**
	 * 自动发送消息
	 */
	@Override
	public void autoSendMsg() {
		//  获取未发送信息自动推送的MessageInfo
		List<MessageInfo> list = messageDao.queryAutoSendList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] ids = new String[list.size()] ;
		int sendCount = 0;
		for(int index = 0 ; index <= list.size() ; index++){
			MessageInfo msg = list.get(index);
			JSONObject sendJson = new JSONObject();
			Map<String,Object> paramMap = new HashMap<String, Object>();
			List<String> membList = new ArrayList<String>();
			// 判断时间
			String currentDateStr = sdf.format(new Date());
			String autoSendDate = msg.getAuto_date().substring(0, 10);
			if(currentDateStr.equals(autoSendDate)){
				if( msg.getAcc_type() == 1 ){//全部
					
				}else if(msg.getAcc_type() == 2){ //人员
					String[] membs  = msg.getAcceptor().split(",");
					for(String membId:membs){
						membList.add(membId);
					}
					paramMap.put("membList", membList);
				}else{ // 取值范围
					String acceptors = msg.getAcceptor();
					paramMap = CommonUtils.getAcceptorParams(acceptors);
				}
				paramMap.put("type", msg.getAcc_type());
				membList = messageDao.getAcceptMembs(paramMap);
				sendJson.put("title", msg.getTitle());
				sendJson.put("body", msg.getIntro());
				SendMsgUtil mUtil = new SendMsgUtil();
				for(String membs:membList){
					mUtil.addQueue(membs);
				}
				mUtil.execute(sendJson);	
				ids[sendCount] = msg.getId();
				sendCount++;
			}
		}
		messageDao.updateSendStatus(ids);
	}

}
