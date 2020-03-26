package com.rx.web.user;

import com.rx.base.model.annotation.RxModel;
import com.rx.base.user.RxPermissionable;

@RxModel(text = "资源模型")
public class PermissionEntity implements RxPermissionable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String code;
	private String name;
	private String group;
	private String desc;
	
	
	public PermissionEntity() {}
	
	public PermissionEntity(RxPermissionable item) {
		this.setCode(item.getId());
		this.setName(item.getName());
		this.setGroup(item.getGroup());
		this.setDesc(item.getDesc());
	}
	
    public String toJS(){
    	return "{name:'"+name+"',code:'"+code+"',group:'"+group+"',desc:'"+desc+"'}";
    }
	
	/**
	* 权限码
	* @return
	*/
	public String getId() {
		return this.code;
	}
	
	/**
	* 权限名称
	* @return
	*/
	public String getName() {
		return this.name;
	}
	
	/**
	* 分组名称
	* @return
	*/
	public String getGroup() {
		return this.group;
	}
	/**
	* 描述
	* @return
	*/
	public String getDesc() {
		return this.desc;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
