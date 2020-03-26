package com.rx.ext.layout.container;

import java.util.HashMap;
import java.util.Map;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;

@ExtClass(alias="layout.table")
public class Table extends Container {

	@ExtConfig
	private Integer columns;
	
	
	@ExtConfig
	private Map<String,Object> tableAttrs;
	
	@ExtConfig
	private Map<String,Object> tdAttrs;
	
	
	public Table() {
	}
	
	public Table(int columns) {
		this.columns = columns;
	}

	public Map<String,Object> getTableAttrs() {
		
		if(tableAttrs == null) {
			tableAttrs = new HashMap<String,Object>();
		}
		
		return tableAttrs;
	}

	
	public void setTableAttr(String key,Object attribute) {
		this.getTableAttrs().put(key, attribute);
	}
	
	
	public void setTableAttrs(Map<String,Object> tableAttrs) {
		this.tableAttrs = tableAttrs;
	}

	public Map<String, Object> getTdAttrs() {
		if(tdAttrs == null) {
			tdAttrs = new HashMap<String,Object>();
		}
		return tdAttrs;
	}

	public void setTdAttrs(Map<String, Object> tdAttrs) {
		this.tdAttrs = tdAttrs;
	}
	public void setTdAttr(String key,Object attribute) {
		this.getTdAttrs().put(key, attribute);
	}
	
}
