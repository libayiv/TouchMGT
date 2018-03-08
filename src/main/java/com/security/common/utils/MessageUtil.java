package com.security.common.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MessageUtil {

    //队列大小  
    private final int QUEUE_LENGTH = 10000*10;  
    //基于内存的阻塞队列  
    private BlockingQueue<String> queue = new LinkedBlockingQueue<String>(QUEUE_LENGTH);  
    //创建计划任务执行器  
    private ScheduledExecutorService es = Executors.newScheduledThreadPool(1);  
  
    /** 
     * 构造函数，执行execute方法 
     */  
    public MessageUtil() {  
      
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
    public void execute() {  
        //每30s执行一次  
        es.scheduleWithFixedDelay(new Runnable(){  
            public void run() {  
                try {  
                	int count = 1;
                	for(count = 1;count <= 1;count++){
                		String content = queue.take();  
                		//处理队列中的信息。。。。。   发送google推送信息
                		System.out.println(content); 
                		if(count > queue.size()){
                			es.shutdown();
                		}
                	}
                     
                } catch (InterruptedException e) {  
                    e.printStackTrace();  
                }  
            }   
        }, 0, 2, TimeUnit.SECONDS);  
    } 
    
    public static void main(String[] args) {
    	MessageUtil s = new MessageUtil();
    	s.addQueue("计划1");
    	s.addQueue("计划2");
    	s.execute();
    	
	}
}
