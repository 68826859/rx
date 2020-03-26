package com.rx.ext.enums;

public enum BooleanValue {
	TRUE(Boolean.TRUE,"True"),
	FALSE(Boolean.FALSE,"False"),
	NULL(null,null);
	private Boolean value;
	private String strValue;
	
	BooleanValue(Boolean value,String strVal){
		this.value = value;
		this.strValue = strVal;
	}
	
	public Boolean getValue(){
		return this.value;
	}

	public String getStrValue() {
		return strValue;
	}
}