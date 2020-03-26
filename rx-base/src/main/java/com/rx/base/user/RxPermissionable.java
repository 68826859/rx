package com.rx.base.user;

import com.rx.base.model.RxModelable;

public interface RxPermissionable extends RxModelable<String>{
	
	/**
	* 分组名称
	* @return
	*/
	public String getGroup();
	/**
	* 描述
	* @return
	*/
	public String getDesc();
}
