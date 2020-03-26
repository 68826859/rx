package com.rx.base.enm;

import com.rx.base.Showable;

public enum DySqlFieldTypeEumn implements Showable<String> {
	字符串("String"),
	数字("Integer"),
	数值("Double"),
	日期("Date"),
	字典("Dict"),
	外键("Refer"),
	枚举("Enum"),
	;
	private String code;
	DySqlFieldTypeEumn(String code){
	this.code = code;
	}
	
	public String getCode(){
	return this.code;
	}
	
	@Override
	public String display() {
	return this.name();
	}
	@Override
	public String value() {
	return this.code;
	}
}
