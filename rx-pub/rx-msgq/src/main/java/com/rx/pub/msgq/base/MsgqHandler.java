package com.rx.pub.msgq.base;

public interface MsgqHandler {
	
	
	public Class<? extends Msgq>[] getHandleTypes();
	
	
	public void handleMsg(Msgq msg,String msgId) throws Exception;
}
