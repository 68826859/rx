package com.rx.pub.mp.base;


public abstract class AbstractWxMpTemplateMsg implements WxMpTemplateMsg {

	private static final long serialVersionUID = -8703631768634188351L;

	private String openId;
	

	@Override
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

}
