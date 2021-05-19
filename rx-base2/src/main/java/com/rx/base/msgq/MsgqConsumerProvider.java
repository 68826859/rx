package com.rx.base.msgq;

public interface MsgqConsumerProvider<T> {

	
	public void regHandler(MsgqHandler handler);

	public int delMsgq(String msgId);
    
    public int delGroupMsgq(String groupKey);
    
    public Msgq getMsgq(String msgId);
}