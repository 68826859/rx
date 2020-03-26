package com.rx.extrx.widget;

import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.direct.RemotingMethod;

public class ServerMethod extends RemotingMethod {
	@ExtConfig
	private String url;
	
	@ExtConfig
	private String method;
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
}
