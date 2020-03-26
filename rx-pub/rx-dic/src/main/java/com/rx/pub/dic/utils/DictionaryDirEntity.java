package com.rx.pub.dic.utils;

import com.rx.base.Showable;
import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.data.Model;
import com.rx.model.base.DictionaryDir;

@ExtClass(extend = Model.class)
public class DictionaryDirEntity implements DictionaryDir {
	
	@RxModelField(isID = true)
	private String code;
	
	@RxModelField(text = "名称",isDisplay = true)
	private String name;
	
	@RxModelField()
	private String group;
	
	@RxModelField(text = "描述")
	private String desc;

	public DictionaryDirEntity() {
	}

	public DictionaryDirEntity(DictionaryDir dir) {
		this.code = dir.getCode();
		this.name = dir.getName();
		this.group = dir.getGroup();
		this.desc = dir.getDesc();
	}

	public DictionaryDirEntity(String code, String name, String group, String desc) {
		this.code = code;
		this.name = name;
		this.group = group;
		this.desc = desc;
	}

	public String toJS() {
		return "{name:'" + name + "',code:'" + code + "',group:'" + group + "',desc:'" + desc + "'}";
	}
	
	
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getGroup() {
		// TODO Auto-generated method stub
		return group;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return desc;
	}

	@Override
	public Class<? extends Showable<?>> getDefaults() {
		// TODO Auto-generated method stub
		return null;
	}

}
