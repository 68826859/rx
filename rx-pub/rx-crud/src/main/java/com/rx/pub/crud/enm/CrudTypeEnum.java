package com.rx.pub.crud.enm;

import com.rx.base.Showable;
import com.rx.base.model.annotation.RxModelField;

public enum CrudTypeEnum implements Showable<Integer> {

	新增(1),删除(2),修改(3),查询(4);

	@RxModelField(isID = true)
	private Integer code;

	CrudTypeEnum(Integer code) {
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

}
