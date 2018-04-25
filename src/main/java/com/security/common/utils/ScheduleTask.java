package com.security.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.security.modules.sys.service.MessageService;

@Component
public class ScheduleTask {
	@Autowired
	MessageService msg;
	//@Scheduled(cron="0 0 7 * * ?")
	@Scheduled(cron="0/50 * *  * * ?")//每天凌晨两点执行
     void doSomethingWith(){
         System.out.println("定时任务开始......");
         long begin = System.currentTimeMillis();
     
         //执行数据库操作了哦...
         msg.autoSendMsg();
         long end = System.currentTimeMillis();
         System.out.println("定时任务结束，共耗时：[" + (end-begin) / 1000 + "]秒");
 }
}
