package com.rx.ext.form.field;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.annotation.ExtFormField;
import com.rx.ext.enums.BooleanValue;
import com.rx.ext.enums.FormFieldVType;

@ExtClass(alias = "widget.textfield")
public class Text extends Base {

	@ExtConfig()
	public Boolean allowBlank;

	@ExtConfig()
	public Boolean editable;

	@ExtConfig()
	public String emptyText;
	
	@ExtConfig
	public String vtype;

	@Override
	public void applyAnnotation(Annotation annotation, Field field, Object value) throws Exception {
		if (annotation instanceof ExtFormField) {
			ExtFormField formField = (ExtFormField) annotation;
			
			if (formField.vType() != FormFieldVType.无) {
				vtype = formField.vType().getVtype();
			}
			
			if (!formField.allowBlank()) {
				allowBlank = Boolean.FALSE;
			}

			if (formField.editable() != BooleanValue.NULL) {
				editable = formField.editable().getValue();
			}

			if (!ExtFormField.NULL.equals(formField.emptyText())) {
				emptyText = formField.emptyText();
			}
		}
		super.applyAnnotation(annotation, field, value);
	}
}
