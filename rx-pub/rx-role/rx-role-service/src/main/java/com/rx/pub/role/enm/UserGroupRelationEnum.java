package com.rx.pub.role.enm;

import com.rx.base.Showable;
import com.rx.base.model.annotation.RxModelField;

/**
 * 组关系：0:所属，1：管理，2:隔离
 */
public enum UserGroupRelationEnum implements Showable<Integer> {

    所属(0),
    管理(1),
    隔离(2),
	;
	@RxModelField(isID = true)
	private Integer code;

	UserGroupRelationEnum(Integer code) {
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
