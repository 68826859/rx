package com.rx.base.msgq;

import java.util.Date;

public interface MsgqProducerProvider<T> {
	
    public String sendMsgq(Msgq msg,Date beginTime,Date endTime,boolean cover,String singleKey,String groupKey);
    
}