package com.rx.pub.msgq.utils;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.util.StringUtils;

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
    
    public static int ERRORMSG_LEN = 1024;
    
    private static Map<Class<?>,MsgqHandler> handlers = new HashMap<Class<?>,MsgqHandler>();
    
    public static void regHandler(MsgqHandler handler) {
    	
    	List<Class<? extends Msgq>> typeCls = handler.supportMsgTypes();
    	for(Class<? extends Msgq> type : typeCls) {
    		handlers.put(type, handler);
    	}
    }
    /*
    public static void regHandler(MsgqHandler handler) {
    	
    	Class<?> typeCls = SpringContextHelper.getBeanActualType(handler,MsgqHandler.class,0);
    	
    	handlers.put(typeCls, handler);
    	
    	MsgqConsumer client = SpringContextHelper.getBean(MsgqConsumer.class);
    	if(client != null) {
    		client.doInitTask();
    	}
    }
    
    
    private static Map<Class<?>,Class<? extends MsgqHandler<?>>> handlerTypes = new HashMap<Class<?>,Class<? extends MsgqHandler<?>>>();
    
    
    
    
    public static void regHandlerCls(Class<? extends MsgqHandler<?>> cls) {
    	
    	Class<?> typeCls = SpringContextHelper.getInterfacesActualType(cls,MsgqHandler.class,0);
    	
    	handlerTypes.put(typeCls,cls);
    	
    	
    	MsgqConsumer client = SpringContextHelper.getBean(MsgqConsumer.class);
    	if(client != null) {
    		client.doInitTask();
    	}
    	
    	
    }
    
    private static Map<String, MsgqHandler<?>> getHandlers(){
    	
    	
    	Map<String, MsgqHandler> handlerMap  = SpringContextHelper.getBeans(MsgqHandler.class);
    	
    	MsgqHandler<?> handler;
    	
    	for(Entry<Class<?>, Class<? extends MsgqHandler<?>>> entry :handlerTypes.entrySet()) {
    		
    		handler = null;
    		entry.getKey();
    		
    		for(Entry<String, MsgqHandler> handlerItem:handlerMap.entrySet()) {
    			
    			if(entry.getValue().isInstance(handlerItem.getValue())) {
    				handler = handlerItem.getValue();
    				break;
    			}
        	}
    		
    		if(handler == null) {
    			try {
					handler = entry.getValue().newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				} 
    		}
    		
    		
    		
    		
    		
    		
    	}
    	
    	
    	return null;
    }
    
    */
    
    @PostConstruct
    public void doInit() {
    	taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
            	
            	try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
            	
            	doInitTask();
            }
    	});
    }
    
    private void doInitTask() {
        	
    	if(!handlers.isEmpty()) {
        	
    		if (future == null) {
		    	future = threadPoolTaskScheduler.schedule(new Runnable() {
					@Override
					public void run() {
						consumeMsg();
					}
		    	}, new CronTrigger("0/5 * * * * ?"));
    		}
    	}
    	
    }

    /*
    public static void main(String[] args) {
    	
    	System.out.println(SpringContextHelper.getBeanActualType(MsgqServiceImpl.class,BaseService.class,0).getName());
    	
    	
    }
    */
    
    
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
    public static int delMessage(String msgId) {
        try {
        	MsgqConsumer client = SpringContextHelper.getBean(MsgqConsumer.class);
        	return client.delMsg(msgId);
        }catch (Exception ex){
        	ex.printStackTrace();
        }
        return 0;
    }
    
    
    /**
     *	删除分组消息
     */
    public static int delGroupMessage(String groupKey) {
        try {
        	if(StringUtils.hasText(groupKey)) {
        		MsgqConsumer client = SpringContextHelper.getBean(MsgqConsumer.class);
        		return client.delGroupMsg(groupKey);
        	}
        }catch (Exception ex){
        	ex.printStackTrace();
        }
        return 0;
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
    
    public int delMsg(String msgId){
    	return msgqMapper.deleteByPrimaryKey(msgId);
    }
    
    public int delGroupMsg(String groupKey){
    	
    	MsgqPo dels = new MsgqPo();
    	dels.setGroupKey(groupKey);
    	
    	return msgqMapper.delete(dels);
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
	            	
	            	MsgqHandler handler;
	            	
	            	for(Entry<Class<?>, MsgqHandler> entry :handlers.entrySet()) {
	            		/*
	            		handler = SpringContextHelper.getBean(entry.getValue());
	            		if(handler == null) {
	            			try {
	        					handler = entry.getValue().newInstance();
	        				} catch (Exception e) {
	        					e.printStackTrace();
	        				} 
	            		}
	            		*/
	            		handler = entry.getValue();
	            		String msgType = entry.getKey().getName();
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
		                			Class<?> cls = Class.forName(msg.getMsgType());
		                			Msgq obj = (Msgq)JSON.parseObject(msg.getMsgContent(), cls);
		                			handler.handleMsg(obj , msg.getMsgId());
		                			msgqMapper.deleteByPrimaryKey(msg.getMsgId());
		                		}catch(Exception e) {
		                			String str = JSONObject.toJSONString(e);
		                			if(str.length() > ERRORMSG_LEN) {
		                				str = str.substring(0, ERRORMSG_LEN);
		                			}
		                			msgqMapper.updateByPrimaryKeySelective(new MsgqPo(msg.getMsgId()).setErrorMsg(str));
		                			new MsgqConsumerErrorMsg(msg.getMsgId(),e.getMessage()).send();
		                		}finally{
		                			
		                		}
		                	}
	                	}while(msg != null);
	            	}
	            	if(existThread > 0) {
                		existThread--;
                	}
	            }
	        });
        }
    }
    
}
