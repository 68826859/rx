package com.rx.pub.dic.enm;

import com.rx.base.model.annotation.RxModelField;
import com.rx.base.user.RxPermissionable;

public enum DicPermissionEnum implements RxPermissionable{
	
	查询字典项("dic_list","字典管理","查询字典列表"),
	新增字典项("dic_add","字典管理","新增字典项"),
	修改字典项("dic_update","字典管理","修改字典项"),
	删除字典项("dic_del","字典管理","删除字典项"),
	移动字典顺序("dic_move","字典管理","移动字典顺序");
	
	
	@RxModelField(isID=true)
	private String code;
	
	@RxModelField()
	private String group;
	
	@RxModelField()
	private String desc;
	
	DicPermissionEnum(String code,String group,String desc){
		this.code = code;
		this.group = group;
		this.desc = desc;
	}
	
	public String getDesc(){
		return desc;
	}
	
	public String getCode() {
		return code;
	}
	public String getGroup() {
		return group;
	}

	@Override
	public String getName() {
		return name();
	}

	@Override
	public String getId() {
		return code;
	}

	

}
