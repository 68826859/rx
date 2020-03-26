package com.rx.base.model;

import com.rx.base.Showable;

public enum ModelApplyEnum implements Showable<String> {

	INSERT("新增"),DELETE("删除"),UPDATE("修改"),SELECT("查询");

	//@RxModelField(isID = true)
	private String code;

	ModelApplyEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.name();
	}

	@Override
	public String display() {
		return this.code;
	}

	@Override
	public String value() {
		return this.name();
	}

}
