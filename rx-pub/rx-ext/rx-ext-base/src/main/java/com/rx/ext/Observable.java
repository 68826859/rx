package com.rx.ext;

import java.util.HashMap;
import java.util.Map;

import com.rx.ext.annotation.ExtConfig;

public class Observable extends Base {
	@ExtConfig
	private Map<String, String> listeners;
	
	
	public Map<String, String> getListeners() {
	if(listeners == null){
			listeners = new HashMap<String,String>();
	}
	return listeners;
	}
	public void addListener(String eventName,String fn) {
	this.getListeners().put(eventName, fn);
	}
}
