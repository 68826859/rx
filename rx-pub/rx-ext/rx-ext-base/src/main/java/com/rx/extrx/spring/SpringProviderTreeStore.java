package com.rx.extrx.spring;

import com.rx.ext.Base;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.data.TreeStore;
import com.rx.ext.data.proxy.Ajax;
import com.rx.ext.data.reader.Json;
import com.rx.extrx.widget.ServerMethod;
import com.rx.extrx.widget.ServerProvider;

@ExtClass(alias="store.springprovidertreestore",alter="Rx.spring.SpringProviderTreeStore")
public class SpringProviderTreeStore extends TreeStore{

	
	//@ExtConfig
	private Class<?> providerClass;
	
	//@ExtConfig
	private String methodName;
	
	
	public SpringProviderTreeStore(){
		
	}
	public SpringProviderTreeStore(Class<?> model,String providerClass,String methodName) throws ClassNotFoundException{
	this(model,Class.forName(providerClass),methodName);
	}
	public SpringProviderTreeStore(Class<?> model,Class<?> providerClass,String methodName){
	this.setModel(model);
	ServerProvider sp = null;
	try {
			sp = (ServerProvider)Base.forClass(providerClass);
	} catch (Exception e) {
			e.printStackTrace();
	}
	ServerMethod sm = sp.getMethod(methodName);
	if(sm == null){
			
	}
	Ajax ajax = new Ajax();
	ajax.setUrl(sm.getUrl());
	Json json = new Json();
	json.setRootProperty("data");
	ajax.setReader(json);
	ajax.setMethod(sm.getMethod());
	this.setProxy(ajax);
	}
	
	public Class<?> getProviderClass() {
	return providerClass;
	}
	public void setProviderClass(Class<?> providerClass) {
	this.providerClass = providerClass;
	}
	public String getMethodName() {
	return methodName;
	}
	public void setMethodName(String methodName) {
	this.methodName = methodName;
	}
}
