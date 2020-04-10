package com.rx.pub.msgq.base;

public interface MsgqHandler<T extends Msgq> {
	
	
	public void handleMsg(T msg,String msgId) throws Exception;
}
