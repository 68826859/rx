package com.rx.base.msgq;

import java.lang.reflect.Type;
import java.util.Date;

import com.rx.base.RxProviderable;

public interface Msgq extends RxProviderable{
	
	default String send() {
    	return this.send(null,null);
    }
	default String send(Date beginTime,Date endTime) {
    	return this.send(beginTime,endTime,false,null,null);
    }
	default String send(Date beginTime,Date endTime,String groupKey) {
    	return this.send(beginTime,endTime,false,null,groupKey);
    }
	default String send(Date beginTime,Date endTime,boolean cover,String singleKey) {
    	return this.send(beginTime,endTime,cover,singleKey,null);
    }
	default String send(Date beginTime,Date endTime,boolean cover,String singleKey,String groupKey) {
    	return getRxProvider(MsgqProducerProvider.class,new Type[] {Msgq.class}).sendMsgq(this,beginTime,endTime,cover,singleKey,groupKey);
    }
}
