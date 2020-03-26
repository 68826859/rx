package com.rx.extrx.button;

import com.rx.ext.Base;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.button.Icon;
import com.rx.extrx.widget.ServerMethod;
import com.rx.extrx.widget.ServerProvider;

@ExtClass(alter = "Ext.Base")
public class SpringButton extends com.rx.ext.button.Button {

	public SpringButton() {
	}

	public SpringButton(String text) {
		super(text);
	}

	public SpringButton(String text, String handler) {
		super(text, handler);
	}

	public SpringButton(String text, String handler, String iconCls) {
		super(text, handler, iconCls);
	}

	public SpringButton(String text, String handler, Icon icon) {
		super(text, handler);
		this.icon = icon.getPath();
	}

	public SpringButton(Class<?> providerClass, String methodName, String text, Icon icon) {
		this(providerClass, methodName, text, (String) null);
		this.icon = icon.getPath();

	}

	public SpringButton(Class<?> providerClass, String methodName, String text, String iconCls) {
		super(text, null, iconCls);
		ServerProvider sp = null;
		try {
			sp = (ServerProvider) Base.forClass(providerClass);
			sp.applyTarget();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServerMethod sm = sp.getMethod(methodName);
		this.href = sm.getUrl();
	}
}
