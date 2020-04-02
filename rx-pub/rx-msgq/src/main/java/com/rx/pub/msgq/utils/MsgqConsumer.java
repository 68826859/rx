package com.rx.pub.msgq.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import com.rx.base.cache.CacheHelper;
import com.rx.base.serialize.Snapshot;
import com.rx.base.utils.StringUtil;
import com.rx.pub.msgq.mapper.MsgqMapper;
import com.rx.pub.msgq.po.MsgqPo;
import com.rx.spring.utils.SpringContextHelper;

@Component()
public class MsgqConsumer {
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    
    @Autowired
    private MsgqMapper msgqMapper;
    
    private int taskIndex = 0;
    
    //@Scheduled(cron="0/10 * * * * ?")
    public void doTask() {
    	
    	if(!senders.isEmpty()) {
	    	Set<Class<? extends Snapshot<String>>> mts = senders.keySet();
	    	Set<Class<? extends Snapshot<String>>> sets = null;
	    	if(taskIndex++ % 10 == 0) {
	    		sets = mts;
	    		if(taskIndex == Integer.MAX_VALUE) {
	    			taskIndex = 0;
	    		}
	    	}else {
		    	sets = new HashSet<Class<? extends Snapshot<String>>>();
		    	for(Class<? extends Snapshot<String>> mt : mts) {
		    		if(MsgqProducer.MSGQ_KEY.equals(CacheHelper.getCacher().getString(mt.getName()))){
		    			sets.add(mt);
		    		}
		    	}
	    	}
	    	if(sets != null && !sets.isEmpty()) {
	    		consumeMsg(sets);
	    	}
    	}
    }
    public static void doTaskInteval() {
        try {
        	MsgqConsumer client = SpringContextHelper.getBean(MsgqConsumer.class);
        	client.doTask();
        }catch (Exception ex){
        	ex.printStackTrace();
        }
    }
    
    public static void consumeMessage() {
        try {
        	MsgqConsumer client = SpringContextHelper.getBean(MsgqConsumer.class);
        	client.consumeMsg(senders.keySet());
        }catch (Exception ex){
        	ex.printStackTrace();
        }
    }
    
    /**
     *	 获得一个消息
     */
    public static Snapshot<String> getMessage(String msgId) {
        try {
        	MsgqConsumer client = SpringContextHelper.getBean(MsgqConsumer.class);
        	return client.getMsg(msgId);
        }catch (Exception ex){
        	ex.printStackTrace();
        }
        return null;
    }
    /**
     *	删除一个消息
     */
    public static void delMessage(String msgId) {
        try {
        	MsgqConsumer client = SpringContextHelper.getBean(MsgqConsumer.class);
        	client.delMsg(msgId);
        }catch (Exception ex){
        	ex.printStackTrace();
        }
    }
    
    
    private static Map<Class<? extends Snapshot<String>>,MsgqSender> senders =  new HashMap<Class<? extends Snapshot<String>>,MsgqSender>();
    
    public static void regiestSender(Class<? extends Snapshot<String>> msgType,MsgqSender sender) {
    	senders.put(msgType, sender);
    }
    
    public static boolean removeSender(Class<? extends Snapshot<String>> msgType,MsgqSender sender) {
    	MsgqSender sed = senders.get(msgType);
    	if(sed != null && sed == sender) {
    		return senders.remove(msgType, sender);
    	}
    	return false;
    }
    
    
    public static MsgqSender getSender(Class<? extends Snapshot<String>> msgType) {
    	return senders.get(msgType);
    }
    
    
    
    private static int existThread = 0;
    final private static String TM_SYNC_KEY = "MSGQ_CONSUMER_SYNC_KEY";
    
    public void delMsg(String msgId){
    	msgqMapper.deleteByPrimaryKey(msgId);
    }
    
    public Snapshot<String> getMsg(String msgId){
    	MsgqPo msg = msgqMapper.selectByPrimaryKey(msgId);
    	if(msg != null) {
    		try {
    			Class<? extends Snapshot<String>> cls = (Class<? extends Snapshot<String>>) Class.forName(msg.getMsgType());
    			Snapshot<String> obj = cls.newInstance();
    			obj.applyShot(msg.getMsgContent());
    			return obj;
    		}catch(Exception e) {
    			e.printStackTrace();
    		}finally{
    			
    		}
    	}
    	return null;
    }
    
    public void consumeMsg(Set<Class<? extends Snapshot<String>>> sets){
        if (existThread < 3){
            existThread++;
	        taskExecutor.execute(new Runnable() {
	            @Override
	            public void run() {
	            	//Set<Entry<Class<? extends Snapshot<String>>, MsgqSender>> sets = senders.entrySet();
	            	for(Class<? extends Snapshot<String>> ent:sets) {
	            		
	            		String msgType = ent.getName();
	                	MsgqPo msg = null;
	                	do{
		                	synchronized (TM_SYNC_KEY){
		                		String holder = StringUtil.getUUID();
		                		if(msgqMapper.updateHoldOneMsg(holder,msgType) > 0) {
		                			msg = msgqMapper.selectOneHoldMsg(holder, msgType);
		                		}else {
		                			msg = null;
		                			CacheHelper.getCacher().put(msgType,"");
		                		}
		                    }
		                	if(msg != null) {
		                		try {
		                			Class<? extends Snapshot<String>> cls = (Class<? extends Snapshot<String>>) Class.forName(msg.getMsgType());
		                			Snapshot<String> obj = cls.newInstance();
		                			obj.applyShot(msg.getMsgContent());
		                			senders.get(ent).sendMsg(obj,msg.getMsgId());
		                			msgqMapper.deleteByPrimaryKey(msg.getMsgId());
		                		}catch(Exception e) {
		                			String str = JSONObject.toJSONString(e);
		                			if(str.length() > 1024) {
		                				str = str.substring(0, 1024);
		                			}
		                			msgqMapper.updateByPrimaryKeySelective(new MsgqPo(msg.getMsgId()).setErrorMsg(str));
		                		}finally{
		                			
		                		}
		                	}
	                	}while(msg != null);
	                	if(existThread > 0) {
	                		existThread--;
	                	}
	            	}
	            }
	        });
        }
    }

    
    
    public interface MsgqSender{
    	public void sendMsg(Snapshot<String> msg,String msgId) throws Exception;
    }
    
}
