package com.rx.ext.button;

import com.rx.ext.Component;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
	@ExtClass(alias="widget.button")
public class Button extends Component {
	public Button(){
	}
	
	public Button(String text){
	this.text = text;
	}
	public Button(String text,String handler){
	this.text = text;
	this.handler = handler;
	}
	public Button(String text,String handler,String iconCls){
	this.text = text;
	this.handler = handler;
	this.iconCls = iconCls;
	}
	
	@ExtConfig
	public String text;
	
	@ExtConfig
	public String handler;
	
	@ExtConfig
	public String tooltip;
	
	@ExtConfig
	public String iconCls;
	
	@ExtConfig
	public String icon;
	@ExtConfig
	public String hrefTarget;
	
	@ExtConfig
	public String href;

}
