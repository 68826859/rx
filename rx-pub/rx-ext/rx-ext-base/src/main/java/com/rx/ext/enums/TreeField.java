package com.rx.ext.enums;

	public enum TreeField {
	NULL(null),
	parentId(com.rx.ext.data.field.Field.class),
	index(com.rx.ext.data.field.Integer.class),
	depth(com.rx.ext.data.field.Integer.class),
	expanded(com.rx.ext.data.field.Boolean.class),
	expandable(com.rx.ext.data.field.Boolean.class),
	checked(com.rx.ext.data.field.Boolean.class),
	leaf(com.rx.ext.data.field.Boolean.class),
	cls(com.rx.ext.data.field.String.class),
	iconCls(com.rx.ext.data.field.String.class),
	icon(com.rx.ext.data.field.String.class),
	glyph(com.rx.ext.data.field.String.class),
	root(com.rx.ext.data.field.Boolean.class),
	isLast(com.rx.ext.data.field.Boolean.class),
	isFirst(com.rx.ext.data.field.Boolean.class),
	allowDrop(com.rx.ext.data.field.Boolean.class),
	allowDrag(com.rx.ext.data.field.Boolean.class),
	loaded(com.rx.ext.data.field.Boolean.class),
	loading(com.rx.ext.data.field.Boolean.class),
	href(com.rx.ext.data.field.String.class),
	hrefTarget(com.rx.ext.data.field.String.class),
	qtip(com.rx.ext.data.field.String.class),
	qtitle(com.rx.ext.data.field.String.class),
	qshowDelay(com.rx.ext.data.field.Integer.class),
	children(com.rx.ext.data.field.Field.class),
	visible(com.rx.ext.data.field.Boolean.class),
	text(com.rx.ext.data.field.String.class);
	
	private Class<? extends com.rx.ext.data.field.Field> clazz;
	
	TreeField(Class<? extends com.rx.ext.data.field.Field> clazz){
	this.clazz = clazz;
	}
	
	public Class<? extends com.rx.ext.data.field.Field> getClazz(){
	return this.clazz;
	}
}