package com.rx.base.msgq;

import java.util.List;

public interface MsgqHandler {
	
	
	public List<Class<? extends Msgq>> supportMsgTypes();
	
	public void handleMsg(Msgq msg,String msgId) throws Exception;
}
