package com.rx.ext.data.proxy;

import java.util.HashMap;
import java.util.Map;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;

@ExtClass(alias="proxy.server")
public class Server extends Proxy{
	
	@ExtConfig()
	private String url;
	
	private String method;
	
	@ExtConfig()
	private Map<String,Object> extraParams;
	
	public String getUrl() {
	return url;
	}
	public Server setUrl(String url) {
	this.url = url;
	return this;
	}
	public String getMethod() {
	return method;
	}
	public void setMethod(String method) {
	this.method = method;
	}

	
	public void addExtraParam(String key,Object value){
	this.getExtraParams().put(key, value);
	}
	public Map<String,Object> getExtraParams() {
	if(extraParams == null){
			extraParams = new HashMap<String ,Object>();
	}
	return extraParams;
	}
	public void setExtraParams(Map<String,Object> extraParams){
	this.extraParams = extraParams;
	}
}
