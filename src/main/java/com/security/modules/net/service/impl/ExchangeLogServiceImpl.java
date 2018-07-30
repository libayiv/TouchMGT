package com.security.modules.net.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.security.common.utils.CommonUtils;
import com.security.common.utils.SendMsgUtil;
import com.security.modules.net.dao.ExchangeLogDao;
import com.security.modules.net.entity.OrderInfo;
import com.security.modules.net.service.ExchangeLogService;
import com.security.modules.sys.dao.MessageDao;
import com.security.modules.sys.entity.MessageInfo;



@Service
public class ExchangeLogServiceImpl implements ExchangeLogService {
	
	@Autowired
	private ExchangeLogDao exchangeDao;
	@Autowired
	private MessageDao messageDao;

	@Override
	public OrderInfo queryEntity(String order_id) throws Exception {
		return exchangeDao.queryObject(order_id);
	}

	@Override
	public List<OrderInfo> queryList(Map<String, Object> map) throws Exception {
		return exchangeDao.queryList(map);
	}
	
	@Override
	public List<OrderInfo> queryPageList(Map<String, Object> map, RowBounds rowBounds) throws Exception {
		return exchangeDao.queryList(map, rowBounds);
	}


	@Override
	public int count(Map<String, Object> map) throws Exception {
		return exchangeDao.queryTotal(map);
	}

	@Override
	public void save(OrderInfo carouselInf) throws Exception {
		carouselInf.setOrder_id(CommonUtils.getRandomId());
		exchangeDao.save(carouselInf);;
	}

	@Override
	public void update(OrderInfo carouselInf) throws Exception {
		exchangeDao.update(carouselInf);
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
			exchangeDao.updateStatus(params);
		}
	}

	@Override
	public void sendOrder(OrderInfo order) throws Exception {
		MessageInfo scmMes=new MessageInfo();
		JSONObject sendJson = new JSONObject();
		String id=CommonUtils.getRandomId();
		scmMes.setAcc_type(2);
		scmMes.setTitle("You have received a message!");
		scmMes.setContent("code:"+order.getRedeem_code()+";store:"+order.getName()+";address:"+order.getAddr());
		scmMes.setId(id);
		scmMes.setStatus(2);
		scmMes.setType(1);
		scmMes.setAcceptor(order.getUser_id());
		scmMes.setCoversrc(order.getPro_picture());
		messageDao.save(scmMes);
		
		sendJson.put("title", scmMes.getTitle());
		sendJson.put("body", scmMes.getIntro());
		sendJson.put("type","mes");

		//sendJson.put("data", dataJson);
		System.out.println(sendJson.toJSONString());
		SendMsgUtil mUtil = new SendMsgUtil();
		mUtil.addQueue(order.getUser_id());
		Map<String,Object> membParam = new HashMap<String, Object>();
		membParam.put("membId", order.getUser_id());
		membParam.put("id", scmMes.getId());
		messageDao.addDetail(membParam);
		
		mUtil.execute(sendJson,null,false);
		
		
	}

	

}
