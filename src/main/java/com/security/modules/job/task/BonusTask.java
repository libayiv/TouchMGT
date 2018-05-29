package com.security.modules.job.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.mchange.lang.ArrayUtils;
import com.security.common.utils.CommonUtils;
import com.security.common.utils.SendMsgUtil;
import com.security.modules.africa.entity.BFAfricaBonus;
import com.security.modules.africa.service.BFAFricafunctionService;
import com.security.modules.sys.entity.MessageInfo;
import com.security.modules.sys.service.MessageService;
import com.security.modules.sys.service.SendControlService;

@Component("BonusTask")
public class BonusTask {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BFAFricafunctionService bfAFricafunctionService;
	@Autowired
	private SendControlService sendControlService;
	@Autowired
	private MessageService msg;

	public void sendBonus(){	

		try {
			JSONObject obj=sendControlService.validateSendCtrl("job:send:bonus");
			if(!"100".equals(obj.getString("code"))){
				logger.error(obj.getString("result"));
				return ;
			}
			List<MessageInfo> list=msg.queryMesBonus("BonusSend");
			if(!list.isEmpty()){
				return ;
			}
			BFAfricaBonus bonus=bfAFricafunctionService.queryBounsSend();
			if(bonus!=null){
				if("Bonus Success!".equals(bonus.getEnd_info()) || bonus.getEnd_info() == null || "null".equals(bonus.getEnd_info())){

					Map<String,String> paramMap = new HashMap<String, String>();
					MessageInfo mes=new MessageInfo();
					List<Map<String,Object>> resultMap = msg.getBonusMembs(paramMap);
					JSONObject sendJson = new JSONObject();
					String id=CommonUtils.getRandomId();
					mes.setAcc_type(4);
					mes.setTitle(bonus.getCalculate_date()+" The monthly bonus ("+bonus.getBns_code()+") has been calculated successfully!");
					mes.setIntro("You can check on your Top-up now!");
					mes.setTag("BonusSend");
					mes.setId(id);
					mes.setStatus(2);
					mes.setType(1);
					mes.setAcc_type(1);
					mes.setAcceptor("all");
					msg.save(mes);
					sendJson.put("title", mes.getTitle());
					sendJson.put("body", mes.getIntro());
					
					SendMsgUtil mUtil = new SendMsgUtil();
//					先群发7星以下且有奖金的
					for (Map<String,Object> map : resultMap) {
						String membs=map.get("MEMB_ID").toString();
						String regis_id=map.get("MEMB_SEC_QUEST_CUSTOM").toString();
						if(StringUtils.isNotEmpty(regis_id)){
							mUtil.addQueue(regis_id);
							Map<String,Object> membParam = new HashMap<String, Object>();
							membParam.put("membId", membs);
							membParam.put("id", mes.getId());
							msg.addDetail(membParam);
						}
					}
					mUtil.execute(sendJson,null,true);	

//					再分别发送7星以上且有奖金的		
					paramMap.put("rank", "7");
					resultMap = msg.getBonusMembs(paramMap);
					SendMsgUtil mUtil_1 = new SendMsgUtil();
					JSONObject sendJson_1 = new JSONObject();
					JSONObject dataJson = new JSONObject();
					for (Map<String,Object> map : resultMap) {
						String membs=map.get("MEMB_ID").toString();
						sendJson_1.put("title", mes.getTitle());
						sendJson_1.put("body", "You received "+map.get("DT_BNS_AMT")+" bonus");
						dataJson.put("url", "http://bfintf.bfsuma.com/AFInterface/bfsuma/analysis.html?distributorid="+membs);
						mUtil_1.addQueue(membs);
						Map<String,Object> membParam = new HashMap<String, Object>();
						membParam.put("membId", membs);
						membParam.put("id", mes.getId());
						msg.addDetail(membParam);
						mUtil_1.execute(sendJson_1,dataJson,false);
					}

					
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}


	}
}
