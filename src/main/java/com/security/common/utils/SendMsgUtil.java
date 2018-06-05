package com.security.common.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class SendMsgUtil {

    //队列大小  
    private final int QUEUE_LENGTH = 10000*10;  
    //基于内存的阻塞队列  
    private BlockingQueue<String> queue = new LinkedBlockingQueue<String>(QUEUE_LENGTH);  
    //创建计划任务执行器  
    private ScheduledExecutorService es = Executors.newScheduledThreadPool(1);  
  
    /** 
     * 构造函数，执行execute方法 
     */  
    public SendMsgUtil() {  
      
    }  
      
    /** 
     * 添加信息至队列中 
     * @param content 
     */  
    public void addQueue(String content) {  
        queue.add(content);  
    }  
      
    /** 
     * 初始化执行 
     */  
    public void execute(final JSONObject msgJson,final JSONObject dataJson,boolean isAll) {  
    	
        //每30s执行一次  
        es.scheduleWithFixedDelay(new Runnable(){  
            public void run() {  
                try {  
                	int count = 1;
                	JSONArray ids = new JSONArray();
                	if(!isAll){
                		for(count = 1;count <= 1;count++){
                    		String content = queue.take();  
                    		//处理队列中的信息。。。。。   发送google推送信息
                    		String to = "/topics/"+content+"_dev";
                    		FireBaseUtil.pushFCMNotification(to,null,msgJson,dataJson);
                    		System.out.println(content); 
                    		if(count > queue.size()){
                    			es.shutdown();
                    		}
                    	}
                	}else{
                		for(count = 1;count <= 1000;count++){
                    		String content = queue.take();  
                    		//处理队列中的信息。。。。。   发送google推送信息
                    		ids.add(content);
                    		//System.out.println(content); 	
                    		if(count > queue.size()){
                    			es.shutdown();
                    		}
                    		if(queue.size()==0){
                    			break;
                    		}
                    	}
                		FireBaseUtil.pushFCMNotification(null,ids,msgJson,dataJson);
                	}
                	
                	
                	
            		

                } catch (InterruptedException e) {  
                    e.printStackTrace();  
                }  
            }   
        }, 0, 5, TimeUnit.SECONDS);  
    } 
    

}
