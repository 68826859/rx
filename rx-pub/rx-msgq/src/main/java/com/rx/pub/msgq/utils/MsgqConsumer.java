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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rx.base.msgq.Msgq;
import com.rx.base.msgq.MsgqConsumerProvider;
import com.rx.base.msgq.MsgqHandler;
import com.rx.base.utils.StringUtil;
import com.rx.pub.msgq.mapper.MsgqMapper;
import com.rx.pub.msgq.po.MsgqPo;

@Component()
public class MsgqConsumer implements MsgqConsumerProvider<Msgq> {
	
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
    
    private  Map<Class<?>,MsgqHandler> handlers = new HashMap<Class<?>,MsgqHandler>();
    
    
    @Override
    public void regHandler(MsgqHandler handler) {
    	
    	List<Class<? extends Msgq>> typeCls = handler.supportMsgTypes();
    	for(Class<? extends Msgq> type : typeCls) {
    		handlers.put(type, handler);
    	}
    }
    
    @PostConstruct
    public void doInit() {
    	taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
            	
            	try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
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
    
    
    @PreDestroy
    public void doDestory() {
    	if (future != null) {
            future.cancel(true);
    	}
    }
    
    
    private static int existThread = 0;
    final private static String TM_SYNC_KEY = "MSGQ_CONSUMER_SYNC_KEY";
    
    
    @Override
    public int delMsgq(String msgId){
    	return msgqMapper.deleteByPrimaryKey(msgId);
    }
    
    @Override
    public int delGroupMsgq(String groupKey){
    	
    	MsgqPo dels = new MsgqPo();
    	dels.setGroupKey(groupKey);
    	
    	return msgqMapper.delete(dels);
    }
    
    @Override
    public Msgq getMsgq(String msgId){
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
