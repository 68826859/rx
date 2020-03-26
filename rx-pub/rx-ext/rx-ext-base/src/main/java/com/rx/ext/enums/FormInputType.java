package com.rx.ext.enums;

public enum FormInputType {
	文本("text"), 密码("password"), 文件("file");

	private String type;

	FormInputType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
}