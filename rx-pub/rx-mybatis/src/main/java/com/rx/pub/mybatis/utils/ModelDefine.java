package com.rx.pub.mybatis.utils;

import java.lang.reflect.Field;

public class ModelDefine{
	
	private Class<?> modelClass;
	
	private Field field;
	
	private Class<?> fieldModelClass;
	
	private String mapperBeanName;

	public ModelDefine(Class<?> modelClass, Field field, Class<?> fieldModelClass, String mapperBeanName) {
		super();
		this.modelClass = modelClass;
		field.setAccessible(true);
		this.field = field;
		this.fieldModelClass = fieldModelClass;
		this.mapperBeanName = mapperBeanName;
	}

	public Class<?> getModelClass() {
		return modelClass;
	}

	public void setModelClass(Class<?> modelClass) {
		this.modelClass = modelClass;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Class<?> getFieldModelClass() {
		return fieldModelClass;
	}

	public void setFieldModelClass(Class<?> fieldModelClass) {
		this.fieldModelClass = fieldModelClass;
	}

	public String getMapperBeanName() {
		return mapperBeanName;
	}

	public void setMapperBeanName(String mapperBeanName) {
		this.mapperBeanName = mapperBeanName;
	}
}
