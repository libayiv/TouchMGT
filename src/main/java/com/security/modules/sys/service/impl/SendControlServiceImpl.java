package com.security.modules.sys.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.security.common.utils.CommonUtils;
import com.security.modules.sys.dao.SendControlDao;
import com.security.modules.sys.entity.SendEntity;
import com.security.modules.sys.service.SendControlService;




@Service
public class SendControlServiceImpl implements SendControlService {
	@Autowired
	private SendControlDao sendDao;
	
	@Override
	public SendEntity queryObject(Long id){
		return sendDao.queryObject(id);
	}
	
	@Override
	public List<SendEntity> queryList(Map<String, Object> map){
		return sendDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sendDao.queryTotal(map);
	}
	
	@Override
	public void save(SendEntity send){
		send.setId(CommonUtils.getRandomId());
		sendDao.save(send);
	}
	
	@Override
	public void update(SendEntity send){
		sendDao.update(send);
	}
	
	@Override
	public void delete(Long id){
		sendDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		sendDao.deleteBatch(ids);
	}

	@Override
	public List<SendEntity> queryPageList(Map<String, Object> map, RowBounds rowBounds) throws Exception {
		
		return sendDao.queryList(map, rowBounds);

	}

	@Override
	public void updateStatus(String pids, String status) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		List<String> list = new ArrayList<String>();
		String[] split = pids.split(",");
		for(String pid : split){
			if(pid != null && !"".equals(pid.trim())){
				list.add(pid);
			}
		}
		if(list.size() > 0){
			params.put("list", list);
			sendDao.updateStatus(params);
		}
		
	}

	@Override
	public SendEntity queryEntity(String pid) throws Exception {
		return sendDao.queryObject(pid);
	}

	@Override
	public JSONObject validateSendCtrl(String auth_code) {
		JSONObject sendJson=new JSONObject();
		try {
			SendEntity send=sendDao.querySendStatus(auth_code);
			if(send.getStatus()==1){
				sendJson.put("code", "100");
			}else{
				sendJson.put("code", "102");
				sendJson.put("result", send.getRemark()+"没有权限，请到Touch后台中的推送控制进行恢复！");
			}
				
		} catch (Exception e) {
			sendJson.put("code", "103");
			sendJson.put("result", "推送控制出现异常，请及时处理");
			e.printStackTrace();
		}
		return sendJson;
	}
	
}
