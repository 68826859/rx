package com.rx.ext.layout.container;

import com.rx.ext.annotation.ExtClass;
	@ExtClass(alias="layout.hbox")
public class HBox extends Box {
	public static HBox stretch = new HBox(){{setAlign(Box.AlignEnum.stretch);}};
	public static HBox stretchmax = new HBox(){{setAlign(Box.AlignEnum.stretchmax);}};
	
	}
