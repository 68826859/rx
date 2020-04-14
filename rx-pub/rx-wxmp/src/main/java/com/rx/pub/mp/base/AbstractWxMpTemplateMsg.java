package com.rx.pub.mp.base;


public abstract class AbstractWxMpTemplateMsg implements WxMpTemplateMsg {

	private static final long serialVersionUID = -8703631768634188351L;

	private String openId;
	
	private String templateUrl;
	
	@Override
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getTemplateUrl() {
		return templateUrl;
	}

	public void setTemplateUrl(String templateUrl) {
		this.templateUrl = templateUrl;
	}

}
