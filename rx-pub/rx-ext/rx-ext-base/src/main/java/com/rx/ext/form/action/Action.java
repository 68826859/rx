package com.rx.ext.form.action;

import com.rx.ext.Base;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;

@ExtClass()
public class Action extends Base{
	
	@ExtConfig()
	private String url;
	@ExtConfig()
	private String method;
	
	@ExtConfig()
	private String type;
	
	public String getUrl() {
	return url;
	}
	public void setUrl(String url) {
	this.url = url;
	}
	public String getMethod() {
	return method;
	}
	public void setMethod(String method) {
	this.method = method;
	}
	public String getType() {
	return type;
	}
	public void setType(String type) {
	this.type = type;
	}
	}
