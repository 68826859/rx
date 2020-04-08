package com.rx.pub.msgq.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledFuture;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rx.base.utils.StringUtil;
import com.rx.pub.msgq.base.Msgq;
import com.rx.pub.msgq.base.MsgqHandler;
import com.rx.pub.msgq.mapper.MsgqMapper;
import com.rx.pub.msgq.po.MsgqPo;
import com.rx.spring.utils.SpringContextHelper;

@Component()
public class MsgqConsumer {
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    
    @Autowired
    private MsgqMapper msgqMapper;
    
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler; 
 
    private ScheduledFuture<?> future; 
 
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
       return new ThreadPoolTaskScheduler();
    }
    
    
    private Map<String,MsgqHandler> handlers;
    
    
    
    
    
    @PostConstruct
    public void doInit() {
    	
    	Map<String, MsgqHandler> handlerMap  = SpringContextHelper.getBeans(MsgqHandler.class);
    	
    	if(!handlerMap.isEmpty()) {
    		handlers = new HashMap<String,MsgqHandler>();
    		for(Entry<String, MsgqHandler> entry : handlerMap.entrySet()) {
    			Class<? extends Msgq>[] clss = entry.getValue().getHandleTypes();
    			if(clss != null) {
    				for(Class<? extends Msgq> cls : clss) {
    					handlers.put(cls.getName(), entry.getValue());
    				}
    			}
    		}
    	
	    	future = threadPoolTaskScheduler.schedule(new Runnable() {
				@Override
				public void run() {
					consumeMsg();
				}
	    	}, new CronTrigger("0/5 * * * * ?"));
    	}
    }

    @PreDestroy
    public void doDestory() {
    	if (future != null) {
            future.cancel(true);
    	}
    }
    
    
    /*
    private int taskIndex = 0;
    private void doTask() {
    	
    	if(!senders.isEmpty()) {
	    	Set<Class<? extends Msgq>> mts = senders.keySet();
	    	Set<Class<? extends Msgq>> sets = null;
	    	if(taskIndex++ % 10 == 0) {
	    		sets = mts;
	    		if(taskIndex == Integer.MAX_VALUE) {
	    			taskIndex = 0;
	    		}
	    	}else {
		    	sets = new HashSet<Class<? extends Msgq>>();
		    	for(Class<? extends Msgq> mt : mts) {
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
    */
    
    /**
     *	 获得一个消息
     */
    public static Msgq getMessage(String msgId) {
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
    
    /*
    private static Map<Class<? extends Msgq>,MsgqHandler> senders =  new HashMap<Class<? extends Msgq>,MsgqHandler>();
    
    public static void regiestSender(Class<? extends Msgq> msgType,MsgqHandler sender) {
    	senders.put(msgType, sender);
    }
    
    public static boolean removeSender(Class<? extends Msgq> msgType,MsgqHandler sender) {
    	MsgqHandler sed = senders.get(msgType);
    	if(sed != null && sed == sender) {
    		return senders.remove(msgType, sender);
    	}
    	return false;
    }
    
    
    public static MsgqHandler getSender(Class<? extends Msgq> msgType) {
    	return senders.get(msgType);
    }
    
    */
    
    
    private static int existThread = 0;
    final private static String TM_SYNC_KEY = "MSGQ_CONSUMER_SYNC_KEY";
    
    public void delMsg(String msgId){
    	msgqMapper.deleteByPrimaryKey(msgId);
    }
    
    public Msgq getMsg(String msgId){
    	MsgqPo msg = msgqMapper.selectByPrimaryKey(msgId);
    	if(msg != null) {
    		try {
    			Class<? extends Msgq> cls = (Class<? extends Msgq>) Class.forName(msg.getMsgType());
    			Msgq obj = JSON.parseObject(msg.getMsgContent(), cls);
    			return obj;
    		}catch(Exception e) {
    			e.printStackTrace();
    		}finally{
    			
    		}
    	}
    	return null;
    }
    
    private void consumeMsg(){
        if (existThread < 3){
            existThread++;
	        taskExecutor.execute(new Runnable() {
	            @Override
	            public void run() {
	            	
	            	for(Entry<String, MsgqHandler> entry :handlers.entrySet()) {
	            		
	            		String msgType = entry.getKey();
	                	MsgqPo msg = null;
	                	do{
		                	synchronized (TM_SYNC_KEY){
		                		String holder = StringUtil.getUUID();
		                		if(msgqMapper.updateHoldOneMsg(holder,msgType) > 0) {
		                			msg = msgqMapper.selectOneHoldMsg(holder, msgType);
		                		}else {
		                			msg = null;
		                			//CacheHelper.getCacher().put(msgType,"");
		                		}
		                    }
		                	if(msg != null) {
		                		try {
		                			Class<? extends Msgq> cls = (Class<? extends Msgq>) Class.forName(msg.getMsgType());
		                			Msgq obj = JSON.parseObject(msg.getMsgContent(), cls);
		                			entry.getValue().handleMsg(obj,msg.getMsgId());
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
    
}
