package com.rx.ext.tree;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.data.TreeStore;

@ExtClass(alias="widget.treepanel")
public class Panel extends com.rx.ext.panel.Table {
	@ExtConfig
	private Boolean rootVisible;
	
	@ExtConfig
	private String displayField;
	
	@ExtConfig
	private TreeStore store;
	
	public Panel(){
		
	}
	
	public Panel(TreeStore tstore){
	this.setStore(tstore);
	}
	
	public TreeStore getStore() {
	return store;
	}
	public void setStore(TreeStore store) {
	this.store = store;
	}
	public Boolean getRootVisible() {
	return rootVisible;
	}
	public void setRootVisible(Boolean rootVisible) {
	this.rootVisible = rootVisible;
	}
	public String getDisplayField() {
	return displayField;
	}
	public void setDisplayField(String displayField) {
	this.displayField = displayField;
	}
	
	}
