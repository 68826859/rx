package com.rx.ext.enums;

	public enum ExtFieldXType {
	field("Ext.form.field.Base"),//默认类型
	displayfield("Ext.form.field.Display"),//只显示，不提交
	textfield("Ext.form.field.Text"),//文本框
	numberfield("Ext.form.field.Number"),//数值类型
	datefield("Ext.form.field.Date"),//日期类型
	combo("Ext.form.field.ComboBox"),//下拉框
	tagfield("Ext.form.field.Tag");//多选下拉框
	//基本类型
	
	private String className;
	
	ExtFieldXType(String className){
	this.className = className;
	}
	
	public String getClassName(){
	return this.className;
	}
}