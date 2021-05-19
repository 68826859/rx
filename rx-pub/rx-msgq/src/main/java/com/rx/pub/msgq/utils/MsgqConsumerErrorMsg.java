package com.rx.pub.msgq.utils;

import com.rx.base.msgq.Msgq;

public class MsgqConsumerErrorMsg implements Msgq {

	private String msgId;
	
	private String errorMsg;
	
	
	public MsgqConsumerErrorMsg(String msgId,String eMsg) {
		this.msgId = msgId;
		this.errorMsg = eMsg;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
