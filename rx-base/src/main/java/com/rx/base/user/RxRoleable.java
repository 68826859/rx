package com.rx.base.user;

import java.util.List;

import com.rx.base.model.RxModelable;
import com.rx.base.user.RxPermissionable;

public interface RxRoleable extends RxModelable<String>{
	
	
	public Class<? extends RxUserable> getUserClass();
	/**
	* 角色包含的权限
	* @return
	*/
	public List<RxPermissionable> getPermissions();
	
	/**
	* 角色所要禁用的权限
	* @return
	*/
	public List<RxPermissionable> getPermissionReverses();
	
}
