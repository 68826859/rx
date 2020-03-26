package com.rx.extrx.model;

import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtTreeField;
import com.rx.ext.data.TreeModel;
import com.rx.ext.enums.TreeField;
//import com.rx.base.model.annotation.RxModelField;

@ExtClass
public class TreeNode<T> extends TreeModel{
	
	@RxModelField(isID=true)
	private T id;
	
	@ExtTreeField(treeField=TreeField.parentId)
	private T parentId;
	
	//@RxModelField()
	//private Integer index;
	
	//@RxModelField()
	//private Integer depth;
	
	//@RxModelField()
	//private Boolean expanded;
	
	//@RxModelField()
	//private Boolean expandable;
	
	@ExtTreeField(treeField=TreeField.leaf)
	private Boolean leaf;
	
	//@RxModelField()
	//private String cls;
	
	//@RxModelField()
	//private String iconCls;
	
	//@RxModelField()
	//private String icon;
	
	//@RxModelField(treeField=TreeField.loaded)
	//private Boolean loaded;
	
	//@RxModelField()
	//private Boolean loading;
	
	@ExtTreeField(treeField=TreeField.text)
	private String text;
	
	@ExtTreeField(treeField=TreeField.checked)
	private Boolean checked;
	
	
	public TreeNode(){}
	public TreeNode(T id,T parentId,String text,Boolean leaf){
	this.id = id;
	this.parentId = parentId;
	this.text = text;
	this.leaf = leaf;
	}
	
	public T getId() {
	return id;
	}
	public void setId(T id) {
	this.id = id;
	}
	public T getParentId() {
	return parentId;
	}
	public void setParentId(T parentId) {
	this.parentId = parentId;
	}
	public Boolean getLeaf() {
	return leaf;
	}
	public void setLeaf(Boolean leaf) {
	this.leaf = leaf;
	}
	public String getText() {
	return text;
	}
	public void setText(String text) {
	this.text = text;
	}
	public Boolean getChecked() {
	return checked;
	}
	public void setChecked(Boolean checked) {
	this.checked = checked;
	}
	/*
	public Integer getIndex() {
	return index;
	}
	public void setIndex(Integer index) {
	this.index = index;
	}
	public Integer getDepth() {
	return depth;
	}
	public void setDepth(Integer depth) {
	this.depth = depth;
	}
	public Boolean getExpanded() {
	return expanded;
	}
	public void setExpanded(Boolean expanded) {
	this.expanded = expanded;
	}
	public Boolean getExpandable() {
	return expandable;
	}
	public void setExpandable(Boolean expandable) {
	this.expandable = expandable;
	}
	public String getCls() {
	return cls;
	}
	public void setCls(String cls) {
	this.cls = cls;
	}
	public String getIconCls() {
	return iconCls;
	}
	public void setIconCls(String iconCls) {
	this.iconCls = iconCls;
	}
	public String getIcon() {
	return icon;
	}
	public void setIcon(String icon) {
	this.icon = icon;
	}
	public Boolean getLoaded() {
	return loaded;
	}
	public void setLoaded(Boolean loaded) {
	this.loaded = loaded;
	}
	public Boolean getLoading() {
	return loading;
	}
	public void setLoading(Boolean loading) {
	this.loading = loading;
	}
	*/
}
