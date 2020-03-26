package com.rx.base.enm;

import com.rx.base.Showable;
import com.rx.base.model.annotation.RxModelField;

public enum StatusEnum implements Showable<Integer> {

	启用(1),
	禁用(0);

	@RxModelField(isID = true)
	private Integer code;

	StatusEnum(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return this.code;
	}

	@Override
	public String display() {
		return this.name();
	}

	@Override
	public Integer value() {
		return this.code;
	}

	public static StatusEnum findByValue(Integer value, StatusEnum defaultE) {
		for (StatusEnum pe : StatusEnum.values()) {
			if (pe.code.equals(value)) {
				return pe;
			}
		}
		return defaultE;
	}
	
	public static StatusEnum findByValue(int value, StatusEnum defaultE) {
		for (StatusEnum pe : StatusEnum.values()) {
			if (pe.code.equals(value)) {
				return pe;
			}
		}
		return defaultE;
	}

}
