package com.rx.pub.crud.enm;

import com.rx.base.model.annotation.RxModelField;
import com.rx.base.user.RxPermissionable;

public enum PermissionEumn implements RxPermissionable {

	还原回收站对象("recycle_restore", "回收站", "还原对象"),
	删除回收站对象("recycle_remove", "回收站", "删除对象"),
	查看回收站对象("recycle_list", "回收站", "查看对象列表");
	
	@RxModelField(isID = true)
	private String code;

	@RxModelField()
	private String group;

	@RxModelField()
	private String desc;

	PermissionEumn(String code, String group, String desc) {
		this.code = code;
		this.group = group;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	@Override
	public String getId() {
		return code;
	}

	public String getGroup() {
		return group;
	}

	@Override
	public String getName() {
		return this.name();
	}

}
