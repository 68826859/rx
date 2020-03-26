package com.rx.pub.file.enm;

import com.rx.base.model.annotation.RxModelField;
import com.rx.base.user.RxPermissionable;

public enum PermissionEnum implements RxPermissionable {

	// 查询字典项("dic_list","字典管理","查询字典列表"),
	文件上传("file_add", "文件管理", "上传文件"),
	文件下载("file_update", "文件管理", "文件下载"), 
	文件删除("file_del", "文件管理", "文件删除"),
	文件列表("file_list", "文件管理", "文件列表");

	@RxModelField(isID = true)
	private String code;

	@RxModelField()
	private String group;

	@RxModelField()
	private String desc;

	PermissionEnum(String code, String group, String desc) {
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
