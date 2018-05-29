package com.security.modules.job.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.security.common.utils.CommonUtils;
import com.security.common.utils.SendMsgUtil;
import com.security.modules.africa.entity.BFAfricaSCB_MS;
import com.security.modules.africa.service.BFAFricafunctionService;
import com.security.modules.sys.entity.MessageInfo;
import com.security.modules.sys.service.MessageService;
import com.security.modules.sys.service.SendControlService;

@Component("ScbSendTask")
public class ScbSendTask {
private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BFAFricafunctionService bfAFricafunctionService;
	@Autowired
	private SendControlService sendControlService;
	@Autowired
	private MessageService msg;
	
	public void sendScb(){	
		
		try {
			JSONObject obj=sendControlService.validateSendCtrl("job:send:order");
			if(!"100".equals(obj.getString("code"))){
				logger.error(obj.getString("result"));
				return ;
			}
			List<BFAfricaSCB_MS> scbList=bfAFricafunctionService.queryList();
			
			for (BFAfricaSCB_MS bfAfricaSCB_MS : scbList) {
				MessageInfo scmMes=new MessageInfo();
				JSONObject sendJson = new JSONObject();
				JSONObject dataJson = new JSONObject();
				String id=CommonUtils.getRandomId();
				scmMes.setAcc_type(4);
				scmMes.setTitle("You have an order at "+bfAfricaSCB_MS.getScb_crt_date());
				scmMes.setIntro("click to order history!");
				scmMes.setId(id);
				scmMes.setStatus(2);
				scmMes.setType(2);
				scmMes.setAcceptor(bfAfricaSCB_MS.getScb_input_memb_id());
				msg.save(scmMes);
				sendJson.put("title", scmMes.getTitle());
				sendJson.put("body", scmMes.getIntro());
				dataJson.put("type","order");
				dataJson.put("orderid",bfAfricaSCB_MS.getScb_no());
				//sendJson.put("data", dataJson);
				System.out.println(sendJson.toJSONString());
				SendMsgUtil mUtil = new SendMsgUtil();
				mUtil.addQueue(bfAfricaSCB_MS.getScb_input_memb_id());
				Map<String,Object> membParam = new HashMap<String, Object>();
				membParam.put("membId", bfAfricaSCB_MS.getScb_input_memb_id());
				membParam.put("id", scmMes.getId());
				msg.addDetail(membParam);
				
				mUtil.execute(sendJson,dataJson,false);
				
				bfAFricafunctionService.updateSCBMS(bfAfricaSCB_MS);
			}
			/*sendJson.put("type","order");
			sendJson.put("oderid",mesType);*/

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		//SysUserEntity user = sysUserService.queryObject(1L);
		//System.out.println(ToStringBuilder.reflectionToString(user));
		
	}
}
