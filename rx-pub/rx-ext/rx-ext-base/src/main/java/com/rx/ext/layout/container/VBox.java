package com.rx.ext.layout.container;

import com.rx.ext.annotation.ExtClass;
	@ExtClass(alias="layout.vbox")
public class VBox extends Box {
	public static VBox stretch = new VBox(){{setAlign(Box.AlignEnum.stretch);}};
	public static VBox stretchmax = new VBox(){{setAlign(Box.AlignEnum.stretchmax);}};
}
