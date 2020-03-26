package com.rx.extrx.spring;

import com.rx.ext.Base;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.form.action.Action;
import com.rx.extrx.widget.ServerMethod;
import com.rx.extrx.widget.ServerProvider;

@ExtClass()
public class SpringAction extends Action{
	public SpringAction(){
		
	}
	public SpringAction(Class<?> providerClass,String methodName){
	try {
			ServerProvider sp = (ServerProvider)Base.forClass(providerClass);
			ServerMethod method= sp.getMethod(methodName);
			this.setUrl(method.getUrl());
			this.setMethod(method.getMethod());
	} catch (Exception e) {
			e.printStackTrace();
	}
	}
}
