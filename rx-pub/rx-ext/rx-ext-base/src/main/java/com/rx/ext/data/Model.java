package com.rx.ext.data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.Base;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;

import javax.persistence.Id;

@ExtClass()
public class Model extends Base {
	@ExtConfig
	private String displayProperty;

	@ExtConfig
	private String idProperty;

	@ExtConfig()
	private ArrayList<com.rx.ext.data.field.Field> fields = null;

	public ArrayList<com.rx.ext.data.field.Field> getFields() {
		if (this.fields == null) {
			this.fields = new ArrayList<com.rx.ext.data.field.Field>();
		}
		return this.fields;
	}

	public void setFields(ArrayList<com.rx.ext.data.field.Field> fields) {
		this.fields = fields;
	}

	public void addField(com.rx.ext.data.field.Field field) {
		this.getFields().add(field);
	}

	public static Class<? extends com.rx.ext.data.field.Field> getDefaultFieldType(Class<?> fieldType) {
		if (fieldType == int.class || fieldType == long.class || fieldType == Integer.class
				|| fieldType == Long.class) {
			return com.rx.ext.data.field.Integer.class;
		} else if (fieldType == float.class || fieldType == double.class || fieldType == Float.class
				|| fieldType == Double.class) {
			return com.rx.ext.data.field.Number.class;
		} else if (String.class == fieldType) {
			return com.rx.ext.data.field.String.class;
		} else if (Date.class == fieldType) {
			return com.rx.ext.data.field.Date.class;
		} else if (Boolean.class == fieldType || boolean.class == fieldType) {
			return com.rx.ext.data.field.Boolean.class;
		}
		return com.rx.ext.data.field.Field.class;
	}

	@Override
	public void applyAnnotation(Annotation annotation, Field field, Object value) throws Exception {
		if (annotation instanceof RxModelField) {
			RxModelField modelField = (RxModelField) annotation;
			Class<? extends com.rx.ext.data.field.Field> cls = getDefaultFieldType(field.getType());
			;
			com.rx.ext.data.field.Field f = cls.newInstance();
			f.applyAnnotation(modelField, field, value);
			this.addField(f);

			if (modelField.isID()) {
				this.setIdProperty(f.getName());
			}
			if (modelField.isDisplay()) {
				this.setDisplayProperty(f.getName());
			}
		} else if (annotation instanceof Id && this.getIdProperty() == null) {
			Class<? extends com.rx.ext.data.field.Field> cls = getDefaultFieldType(field.getType());
			com.rx.ext.data.field.Field f = cls.newInstance();
			f.setName(field.getName());
			this.addField(f);
			this.setIdProperty(field.getName());
		} else {
			super.applyAnnotation(annotation, field, value);
		}
	}

	public String getIdProperty() {
		return idProperty;
	}

	public void setIdProperty(String idProperty) {
		this.idProperty = idProperty;
	}

	public String getDisplayProperty() {
		return displayProperty;
	}

	public void setDisplayProperty(String displayProperty) {
		this.displayProperty = displayProperty;
	}
}
