package com.rx.ext.grid.column;

import java.util.ArrayList;
import java.util.List;
	import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;

@ExtClass(alias="widget.actioncolumn")
public class Action extends Column {
	@ExtConfig()
	protected List<ActionItem> items;
	
	
	public List<ActionItem> getItems() {
	if(this.items == null){
			this.items = new ArrayList<ActionItem>();
	}
	return this.items;
	}
	public void setItems(List<ActionItem> items) {
	this.items = items;
	}
	
	public void addItem(ActionItem item) {
	this.getItems().add(item);
	}
}
