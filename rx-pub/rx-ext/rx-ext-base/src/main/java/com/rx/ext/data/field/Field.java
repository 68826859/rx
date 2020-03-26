package com.rx.ext.data.field;

import java.lang.annotation.Annotation;

import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.Base;
import com.rx.ext.Script;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;

@ExtClass(alias = "data.field.auto")
public class Field extends Base {
	@ExtConfig()
	public java.lang.String name;

	@ExtConfig()
	private java.lang.String type;

	@ExtConfig()
	public java.lang.String mapping;

	@ExtConfig()
	public Script convert;
	
	
	public Field() {

	}

	public Field(java.lang.String name, java.lang.String mapping) {
		this.name = name;
		this.mapping = mapping;
	}

	@Override
	public void applyAnnotation(Annotation annotation, Class<?> clazz, Object value) throws Exception {
		super.applyAnnotation(annotation, clazz, value);
		if (annotation instanceof ExtClass) {
			ExtClass extClass = (ExtClass) annotation;
			if (!ExtClass.NULL.equals(extClass.alias())) {
				if (!this.isDefine) {
					java.lang.String as = extClass.alias();
					java.lang.String px = "data.field.";
					if (as.startsWith(px)) {
						this.setType(as.substring(px.length()));
					}
				}
			}
		}
	}

	@Override
	public void applyAnnotation(Annotation annotation, java.lang.reflect.Field field, Object value) throws Exception {
		super.applyAnnotation(annotation, field, value);
		if (annotation instanceof RxModelField) {
			RxModelField modelField = (RxModelField) annotation;
			if (RxModelField.NULL.equals(modelField.name())) {
				if (field != null) {
					this.setName(field.getName());
				}
			} else {
				this.setName(modelField.name());
				this.setMapping(field.getName());
			}
			
			if (!RxModelField.NULL.equals(modelField.convert())) {
				this.setConvert(new Script(modelField.convert()));
			}
			
		}
	}

	public java.lang.String getMapping() {
		return this.mapping;

	}

	public java.lang.String getName() {
		return this.name;

	}

	public void setMapping(java.lang.String mapping2) {
		this.mapping = mapping2;

	}

	public void setName(java.lang.String name2) {
		this.name = name2;

	}

	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	public Script getConvert() {
		return convert;
	}

	public void setConvert(Script convert) {
		this.convert = convert;
	}
}
