package com.security.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.util.StringUtils;

public class FireBaseUtil {
	public final static String AUTH_KEY_FCM= "AAAA3uYRTaY:APA91bH7Qfoz3cHcu_JxUSr4WDFymPReKmRbIJvimWE0MDp-HuHNeqnBoWliFKEGzsps705wEcKhWVln4aoesQvEU7P94bEby_KrkkBjyrHu8UXFGpbWzPMHjQpG5AYk3XnO7M5bNy96";//app服务密钥  
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";//谷歌推送地址  
    private static Logger log = LoggerFactory.getLogger(FireBaseUtil.class); 
      
/*    public static void main(String[] args) {  
        pushFCMNotification();  
    } */ 
      
    public static void pushFCMNotification(String to,JSONArray ids,JSONObject info,JSONObject data) {  
    	JSONObject json = new JSONObject();
    	try {  
    		   //创建SSLContext对象，并使用我们指定的信任管理器初始化  
            TrustManager[] tm = { new MyX509TrustManager() };  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
            sslContext.init(null, tm, new java.security.SecureRandom());  
            //从上述SSLContext对象中得到SSLSocketFactory对象  
            SSLSocketFactory ssf = sslContext.getSocketFactory();  
  
            URL url = new URL(API_URL_FCM);  
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();  
            conn.setSSLSocketFactory(ssf);  

            conn.setUseCaches(false);  
            conn.setDoInput(true);  
            conn.setDoOutput(true);  
            conn.setRequestMethod("POST");    
            conn.setRequestProperty("Authorization","key="+AUTH_KEY_FCM);  
            conn.setRequestProperty("Content-Type","application/json");//不设置默认发送文本格式。设置就是json      
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36");
            
            //json.put("to","此处填写您的客户端app token");//推送到哪台客户端机器，方法一推一个token,             
            //方法二，批量推送 ，最多1000个token ，此处的tokens是一个token JSONArray数组json.put("registration_ids", tokens);  
            /*JSONObject info = new JSONObject();  
            info.put("title","Notification Tilte");  
            info.put("body", "Hello Test notification");  
            info.put("icon", "myicon");*/  
            
            if(StringUtils.isNullOrEmpty(to)){
                json.put("registration_ids", ids);
            }else{
            	json.put("to",to);
            }
            json.put("notification", info);
            json.put("data", data);//json 还可以put其他你想要的参数  
            log.info(json.toString());
            
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());  
            wr.write(json.toString());  
            wr.flush();  
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            String line = null;  
            while ((line = reader.readLine())!= null) {  
                System.out.println(line); 
                log.info("发送消息成功("+to+")！返回"+line);
            }  
            wr.close();  
            reader.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
            log.error("发送消息失败("+to+")！返回:"+e.getMessage() +";详细:"+json.toString()); 
        }  
    }  
}
