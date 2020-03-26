package com.rx.pub.role.enm;

import com.rx.base.Showable;
import com.rx.base.model.annotation.RxModelField;

/**
 * 角色资源关系:1去除,0:添加
 */
public enum RoleResourceReverseEumn implements Showable<Integer> {
	去除(1),
	添加(0),
	;
	@RxModelField(isID = true)
	private Integer code;

	RoleResourceReverseEumn(Integer code) {
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
