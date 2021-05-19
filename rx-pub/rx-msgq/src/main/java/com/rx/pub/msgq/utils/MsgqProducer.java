package com.rx.pub.msgq.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.rx.base.cache.CacheHelper;
import com.rx.base.msgq.Msgq;
import com.rx.base.msgq.MsgqProducerProvider;
import com.rx.pub.msgq.po.MsgqPo;
import com.rx.pub.msgq.service.MsgqService;

@Component
public class MsgqProducer implements MsgqProducerProvider<Msgq>{
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    
    @Autowired
    private MsgqService msgqService;
    /**
     * 
     * @param msg 消息体
     * @param beginTime 消息被消费的开始时间
     * @param endTime 消息的过期时间
     * @param singleKey 独生键
     * @param cover 如果设置独生键，是否覆盖之前的消息。true，覆盖之前的消息，新加的消息有效。false，如果有旧的消息，旧的消息有效，新加的消息无效。
     */
    @Override
    public String sendMsgq(Msgq msg,Date beginTime,Date endTime,boolean cover,String singleKey,String groupKey){
    	
    	if(singleKey == null) {
	    	synchronized (MSGQ_KEY){
	    		MsgqPo msgp = new MsgqPo(null);
	    		msgs.add(msgp.setMsgContent(JSON.toJSONString(msg)).setMsgType(msg.getClass().getName()).setBeginTime(beginTime).setEndTime(endTime).setGroupKey(groupKey));
	    		if (existThread < 2){
	                existThread++; 
	    	        taskExecutor.execute(new ProducerRunnable());
	            }
	    		return msgp.getMsgId();
	    	}
    	}else {
	    	synchronized (MSGQ_KEY_SINGLE){
	    		MsgqPo msgp = new MsgqPo(null);
	    		singleMsgs.add(msgp.setMsgContent(JSON.toJSONString(msg)).setMsgType(msg.getClass().getName()).setBeginTime(beginTime).setEndTime(endTime).setSingleKey(singleKey).setCover(cover).setGroupKey(groupKey));
	    		if (existThreadSingle < 2){
	    			existThreadSingle++; 
	    	        taskExecutor.execute(new ProducerSingleRunnable());
	            }
	    		return msgp.getMsgId();
	    	}
    	}
    }

    final public static String MSGQ_KEY = "MSGQ_KEY";
    private static List<MsgqPo> msgs = new ArrayList<MsgqPo>();
    private static int existThread = 0;
    
    private class ProducerRunnable implements Runnable{
    	@Override
        public void run() {
    		List<MsgqPo> olds = null;
    		do {
	        	synchronized (MSGQ_KEY){
	        		olds = msgs;
	        		if(msgs.size() > 0) {
	        			msgs = new ArrayList<MsgqPo>();
	        		}
	            }
	    		try {
	            	if(olds.size() > 0) {
	            		msgqService.insertMsgs(olds);
	            		HashSet<String> set = new HashSet<String>();
	            		for(MsgqPo msg:olds) {
	            			set.add(msg.getMsgType());
	            		}
	            		for(String nm : set) {
	            			CacheHelper.getCacher().put(nm, MSGQ_KEY);
	            		}
	            	}
	            } catch (Exception e) {
	            	e.printStackTrace();
	            }
    		}while(olds.size() > 0);
    		if(existThread > 0) {
        		existThread--;
        	}
        }
    	
    }
    
    /**
     *	 独生消息
     */
    final private static String MSGQ_KEY_SINGLE = "MSGQ_KEY_SINGLE";
    private static List<MsgqPo> singleMsgs = new ArrayList<MsgqPo>();
    private static int existThreadSingle = 0;
    
    private class ProducerSingleRunnable implements Runnable{
    	@Override
        public void run() {
    		List<MsgqPo> olds = null;
    		do {
	        	synchronized (MSGQ_KEY_SINGLE){
	        		olds = singleMsgs;
	        		if(singleMsgs.size() > 0) {
	        			singleMsgs = new ArrayList<MsgqPo>();
	        		}
	            }
	    		try {
	            	if(olds.size() > 0) {
	            		HashSet<String> set = new HashSet<String>();
	            		HashMap<String,MsgqPo> singles = new HashMap<String,MsgqPo>();
	            		MsgqPo base;
	            		String skey;
	            		for(MsgqPo msg:olds) {
	            			skey = msg.getSingleKey();
	            			base = singles.get(skey);
	            			if(base == null) {
	            				base = msgqService.selectOneAvailableSingleMsg(skey);
	            				if(base == null) {
	            					singles.put(skey, msg);
	            				}else {
	            					if(msg.isCover()) {
	    	            				base.setHolder(msg.getMsgId());
	    	            				msgqService.updateByPrimaryKey(base);
	    	            				singles.put(skey, msg);
	    	            			}else {
	    	            				msg.setHolder(base.getMsgId());
	    	            				singles.put(skey, base);
	    	            			}
	            				}
	            			}else {
		            			if(msg.isCover()) {
		            				base.setHolder(msg.getMsgId());
		            				singles.put(skey, msg);
		            			}else {
		            				msg.setHolder(base.getMsgId());
		            			}
	            			}
	            			set.add(msg.getMsgType());
	            		}
	            		msgqService.insertMsgs(olds);
	            		for(String nm : set) {
	            			CacheHelper.getCacher().put(nm, MSGQ_KEY);
	            		}
	            	}
	            } catch (Exception e) {
	            	e.printStackTrace();
	            }
    		}while(olds.size() > 0);
    		
    		if(existThreadSingle > 0) {
    			existThreadSingle--;
        	}
        }
    }
}
