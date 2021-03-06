package com.security.modules.sys.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
@Component
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
		String[] ids = new String[list.size()];
		int sendCount = 0;
		for(int index = 0 ; index < list.size(); index++){
			MessageInfo msg = list.get(index);
			JSONObject sendJson = new JSONObject();
			Map<String,Object> paramMap = new HashMap<String, Object>();
			List<Map<String,String>> resultMap = new ArrayList<Map<String,String>>();

			List<String> membList = new ArrayList<String>();
			paramMap.put("type", msg.getAcc_type());
			boolean isAll=false;
			if( msg.getAcc_type() == 1 ){//全部
				resultMap = messageDao.getAllMembs();
				isAll=true;
			}else if(msg.getAcc_type() == 2){ //人员
				String[] membs  = msg.getAcceptor().split(",");
				paramMap.put("membList", membs);
				membList = messageDao.getAcceptMembs(paramMap);
				/*for(String membId:membs){
					membList.add(membId);
				}*/
				paramMap.put("membList", membList);
			}else{ // 取值范围
				String acceptors = msg.getAcceptor();
				paramMap = CommonUtils.getAcceptorParams(acceptors);
				paramMap.put("type", msg.getAcc_type());
				membList = messageDao.getAcceptMembs(paramMap);
			}
			
			sendJson.put("title", msg.getTitle());
			sendJson.put("body", msg.getIntro());
			sendJson.put("type","mes");
			
			SendMsgUtil mUtil = new SendMsgUtil();
			if(isAll){
				for (Map<String,String> map : resultMap) {
					String membs=map.get("MEMB_ID");
					String regis_id=map.get("MEMB_SEC_QUEST_CUSTOM");
					if(StringUtils.isNotEmpty(regis_id)){
						mUtil.addQueue(regis_id);
						Map<String,Object> membParam = new HashMap<String, Object>();
						membParam.put("membId", membs);
						membParam.put("id", msg.getId());
						messageDao.addDetail(membParam);
					}
				}
			}else{
				for(String membs:membList){
					mUtil.addQueue(membs);
					Map<String,Object> membParam = new HashMap<String, Object>();
					membParam.put("membId", membs);
					membParam.put("id", msg.getId());
					messageDao.addDetail(membParam);
				}
			}
			
			mUtil.execute(sendJson,null,isAll);
			ids[sendCount] = msg.getId();
			sendCount++;

			
		}
		messageDao.updateSendStatus(pids);
		/*addMsgDetail(ids);*/
		
	}
	
	public void addMsgDetail(String[] ids){
		List<MessageInfo> list = messageDao.queryListByArray(ids);
		for(int index = 0 ; index < list.size(); index++){
			MessageInfo msg = list.get(index);
			Map<String,Object> paramMap = new HashMap<String, Object>();
			List<String> membList = new ArrayList<String>();
			paramMap.put("type", msg.getAcc_type());
			if( msg.getAcc_type() == 1 ){//全部
				membList = messageDao.getAcceptMembs(paramMap);
			}else if(msg.getAcc_type() == 2){ //人员
				String[] membs  = msg.getAcceptor().split(",");
				for(String membId:membs){
					membList.add(membId);
				}
				
				paramMap.put("membList", membList);
			}else{ // 取值范围
				String acceptors = msg.getAcceptor();
				paramMap = CommonUtils.getAcceptorParams(acceptors);
				membList = messageDao.getAcceptMembs(paramMap);
			}
			Map<String,Object> membParam = new HashMap<String, Object>();
			membParam.put("membList", membList);
			membParam.put("sid", msg.getId());
			messageDao.addMessageDetailList(membParam);
		}
	}
	/**
	 * 自动发送消息
	 */
	@Override
	public void autoSendMsg() {
		//  获取未发送信息自动推送的MessageInfo
		System.out.println("------------------ google message autosend ---------------");
		List<MessageInfo> list = messageDao.queryAutoSendList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> idList = new ArrayList<String>();
		List<Map<String,String>> resultMap = new ArrayList<Map<String,String>>();

		int sendCount = 0;
		for(int index = 0 ; index <list.size() ; index++){
			MessageInfo msg = list.get(index);
			JSONObject sendJson = new JSONObject();
			Map<String,Object> paramMap = new HashMap<String, Object>();
			List<String> membList = new ArrayList<String>();
			// 判断时间
			String currentDateStr = sdf.format(new Date());
			String autoSendDate = msg.getAuto_date().substring(0, 10);
			paramMap.put("type", msg.getAcc_type());
			boolean isAll=false;
			if(currentDateStr.equals(autoSendDate)){
				if( msg.getAcc_type() == 1 ){//全部
					resultMap = messageDao.getAllMembs();
					isAll=true;
				}else if(msg.getAcc_type() == 2){ //人员
					String[] membs  = msg.getAcceptor().split(",");
					paramMap.put("membList", membs);
					membList = messageDao.getAcceptMembs(paramMap);
					/*for(String membId:membs){
						membList.add(membId);
					}*/
					paramMap.put("membList", membList);
				}else{ // 取值范围
					String acceptors = msg.getAcceptor();
					paramMap = CommonUtils.getAcceptorParams(acceptors);
					membList = messageDao.getAcceptMembs(paramMap);
				}
				
				sendJson.put("title", msg.getTitle());
				sendJson.put("body", msg.getIntro());
				sendJson.put("type","mes");
				SendMsgUtil mUtil = new SendMsgUtil();	
				if(isAll){
					for (Map<String,String> map : resultMap) {
						String membs=map.get("MEMB_ID");
						String regis_id=map.get("MEMB_SEC_QUEST_CUSTOM");
						if(StringUtils.isNotEmpty(regis_id)){
							mUtil.addQueue(regis_id);
							Map<String,Object> membParam = new HashMap<String, Object>();
							membParam.put("membId", membs);
							membParam.put("id", msg.getId());
							messageDao.addDetail(membParam);
						}
					}
				}else{
					for(String membs:membList){
						mUtil.addQueue(membs);
						Map<String,Object> membParam = new HashMap<String, Object>();
						membParam.put("membId", membs);
						membParam.put("id", msg.getId());
						messageDao.addDetail(membParam);
					}
				}
				mUtil.execute(sendJson,null,isAll);
				idList.add(msg.getId()) ;
				
				
				/*Map<String,Object> membParam = new HashMap<String, Object>();
				membParam.put("membList", membList);
				membParam.put("sid", msg.getId());
				messageDao.addMessageDetailList(membParam);*/
			}
		}
		String[] ids = new String[idList.size()];
		for(int count=0 ; count< idList.size() ;count++){
			ids[count]=idList.get(count);
		}
		if(ids.length >0){
			messageDao.updateSendStatus(ids);
		}
		
		/*addMsgDetail(ids);*/
		
	}

	@Override
	public void addDetail(Map<String,Object> membParam) {
		messageDao.addDetail(membParam);
		
	}

	@Override
	public void updateSendStatus(String[] pids) {
		messageDao.updateSendStatus(pids);
		
	}

	@Override
	public List<Map<String, Object>> getBonusMembs(Map<String, String> param) {
		return messageDao.getBonusMembs(param);
	}

	@Override
	public List<MessageInfo> queryMesBonus(String tag) {
		return messageDao.queryMesBonus(tag);
	}

}
