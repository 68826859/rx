package com.rx.pub.mp.base;

import java.util.Map;

public abstract class AbstractWxMpTemplateMsg implements WxMpTemplateMsg {

	private static final long serialVersionUID = -8703631768634188351L;

	private String openId;
	
	private String templateUrl;
	
	private String templateId;
	
	private String miniAppId;
	
	private String miniPagePath;
	
	private boolean miniUsePath = false;
	
	@Override
	public String getOpenId() {
		return openId;
	}

	@Override
	public String getTemplateUrl() {
		return templateUrl;
	}

	@Override
	public Map<String, String> getTemplateDataColor() {
		return null;
	}

	@Override
	public String getMiniAppId() {
		return miniAppId;
	}

	@Override
	public String getMiniPagePath() {
		return miniPagePath;
	}

	@Override
	public boolean getMiniUsePath() {
		return miniUsePath;
	}

	@Override
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public void setMiniPagePath(String miniPagePath) {
		this.miniPagePath = miniPagePath;
	}

	public void setMiniAppId(String miniAppId) {
		this.miniAppId = miniAppId;
	}
	
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	public void setTemplateUrl(String templateUrl) {
		this.templateUrl = templateUrl;
	}

	public void setMiniUsePath(boolean miniUsePath) {
		this.miniUsePath = miniUsePath;
	}

}
