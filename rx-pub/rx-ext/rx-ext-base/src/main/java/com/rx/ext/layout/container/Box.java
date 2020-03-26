package com.rx.ext.layout.container;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;

	@ExtClass(alias="layout.box")
public class Box extends Container {

	
	@ExtConfig
	private String align;
	
	public enum AlignEnum{
	begin,middle,end,stretch,stretchmax;
	}
	public String getAlign() {
	return align;
	}
	public void setAlign(String align) {
	this.align = align;
	}
	
	public void setAlign(AlignEnum ae) {
	this.align = ae.name();
	}
}
