package com.rx.base.enm;

import com.rx.base.Showable;
import com.rx.base.model.annotation.RxModelField;

public enum DelEnum implements Showable<Integer> {

	已删除(1),
	未删除(0);

	@RxModelField(isID = true)
	private Integer code;

	DelEnum(Integer code) {
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

	public static DelEnum findByValue(Integer value, DelEnum defaultE) {
		for (DelEnum pe : DelEnum.values()) {
			if (pe.code.equals(value)) {
				return pe;
			}
		}
		return defaultE;
	}
	
	public static DelEnum findByValue(int value, DelEnum defaultE) {
		for (DelEnum pe : DelEnum.values()) {
			if (pe.code.equals(value)) {
				return pe;
			}
		}
		return defaultE;
	}

}
