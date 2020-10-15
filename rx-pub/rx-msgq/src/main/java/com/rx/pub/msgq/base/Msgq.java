package com.rx.pub.msgq.base;

import java.util.Date;

import com.rx.pub.msgq.utils.MsgqProducer;

public interface Msgq {
	
	default String send() {
    	return MsgqProducer.sendMessage(this,null,null);
    }
    
	default String send(Date beginTime,Date endTime) {
    	return MsgqProducer.sendMessage(this,beginTime,endTime,false,null,null);
    }
    
	default String send(Date beginTime,Date endTime,String groupKey) {
    	return MsgqProducer.sendMessage(this,beginTime,endTime,false,null,groupKey);
    }
	default String send(Date beginTime,Date endTime,boolean cover,String singleKey) {
    	return MsgqProducer.sendMessage(this,beginTime,endTime,cover,singleKey,null);
    }
    
	default String send(Date beginTime,Date endTime,boolean cover,String singleKey,String groupKey) {
    	return MsgqProducer.sendMessage(this,beginTime,endTime,cover,singleKey,groupKey);
    }
}
