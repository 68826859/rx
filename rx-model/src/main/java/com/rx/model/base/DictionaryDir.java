package com.rx.model.base;

import com.rx.base.Showable;

public interface DictionaryDir {

	/**
	* 字典码
	* @return
	*/
	public String getCode();
	
	/**
	* 字典名称
	* @return
	*/
	public String getName();
	
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
	
	
	public Class<? extends Showable<?>> getDefaults();
}
