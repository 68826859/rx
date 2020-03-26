package com.rx.ext.direct;

import com.rx.ext.Base;
import com.rx.ext.annotation.ExtConfig;

public class RemotingMethod extends Base {

	
	@ExtConfig
	private String name;
	
	@ExtConfig
	private Boolean formHandler;
	
	public String getName() {
	return name;
	}
	public void setName(String name) {
	this.name = name;
	}
	public Boolean getFormHandler() {
	return formHandler;
	}
	public void setFormHandler(Boolean formHandler) {
	this.formHandler = formHandler;
	}
}
