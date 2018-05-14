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

@Component("ScbSendTask")
public class ScbSendTask {
private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BFAFricafunctionService bfAFricafunctionService;
	@Autowired
	private MessageService msg;
	
	public void sendScb(){	
		
		try {
			List<BFAfricaSCB_MS> scbList=bfAFricafunctionService.queryList();
			Map<String,Object> paramMap = new HashMap<String, Object>();
			
			for (BFAfricaSCB_MS bfAfricaSCB_MS : scbList) {
				MessageInfo scmMes=new MessageInfo();
				JSONObject sendJson = new JSONObject();
				String id=CommonUtils.getRandomId();
				scmMes.setAcc_type(4);
				scmMes.setTitle("You have an order at "+bfAfricaSCB_MS.getScb_crt_date());
				scmMes.setIntro("click to order history!");
				scmMes.setId(id);
				scmMes.setStatus(2);
				scmMes.setType(2);
				scmMes.setAcceptor(bfAfricaSCB_MS.getScb_input_memb_id());
				msg.save(scmMes);
				paramMap.put("membList", bfAfricaSCB_MS.getScb_input_memb_id());
				sendJson.put("title", scmMes.getTitle());
				sendJson.put("body", scmMes.getIntro());
				sendJson.put("type","order");
				sendJson.put("oderid",bfAfricaSCB_MS.getScb_no());
				
				SendMsgUtil mUtil = new SendMsgUtil();
				mUtil.addQueue(bfAfricaSCB_MS.getScb_input_memb_id());
				Map<String,Object> membParam = new HashMap<String, Object>();
				membParam.put("membId", bfAfricaSCB_MS.getScb_input_memb_id());
				membParam.put("id", scmMes.getId());
				msg.addDetail(membParam);
				
				mUtil.execute(sendJson);
				
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
