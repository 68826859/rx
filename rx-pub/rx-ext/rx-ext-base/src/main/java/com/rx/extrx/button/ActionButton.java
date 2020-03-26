package com.rx.extrx.button;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.button.Icon;
import com.rx.ext.grid.column.ActionItem;

@ExtClass(alter = "Ext.Base")
public class ActionButton extends ActionItem {

	public static ActionButton delete = new ActionButton(Icon.delete, "doDeleteRecord", "删除", "确定删除该记录吗？");
	public static ActionButton update = new ActionButton(Icon.update, "doUpdateRecord", "修改");

	@ExtConfig
	private String handler;

	@ExtConfig
	private String confirm;

	@ExtConfig
	private String realHandler;

	public ActionButton() {
		this.iconCls = "actioncolumn-margin";
	}

	public ActionButton(Icon icon, String handler, String tooltip) {
		this.iconCls = "actioncolumn-margin";
		this.tooltip = tooltip;
		this.handler = handler;
		this.icon = icon.getPath();
	}

	public ActionButton(Icon icon, String handler, String tooltip, String confirm) {
		this(icon, handler, tooltip);
		this.setConfirm(confirm);
	}

	public String getRealHandler() {
		return realHandler;
	}

	public void setRealHandler(String realHandler) {
		this.realHandler = realHandler;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.realHandler = this.handler;
		this.handler = "$recordHandler";
		this.confirm = confirm;
	}

	public String getHandler() {
		return handler;
	}

	public ActionButton setHandler(String handler) {
		if (this.confirm != null) {
			this.realHandler = handler;
		} else {
			this.handler = handler;
		}
		return this;
	}
}
