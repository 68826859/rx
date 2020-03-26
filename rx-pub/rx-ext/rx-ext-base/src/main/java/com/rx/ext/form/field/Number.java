package com.rx.ext.form.field;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.annotation.ExtFormField;

@ExtClass(alias="widget.numberfield")
public class Number extends Text{

	//是否允许有小数位
	@ExtConfig()
	public Boolean allowDecimals;
	
	
	@ExtConfig()
	public Float maxValue;
	
	@ExtConfig()
	public Float minValue;
	
    @ExtConfig()
    private Float step;
    
    @ExtConfig()
    private Integer decimalPrecision;
    
    
	public void applyAnnotation(Annotation annotation, Field field, Object value) throws Exception {
		if (annotation instanceof ExtFormField) {
			ExtFormField formField = (ExtFormField) annotation;
			
			if (!formField.allowDecimals()) {
				allowDecimals = Boolean.FALSE;
			}
			
			if (formField.maxValue() != Float.MAX_VALUE) {
				maxValue = Float.valueOf(formField.maxValue());
			}

			if (formField.minValue() != Float.MIN_VALUE) {
				minValue = Float.valueOf(formField.minValue());
			}

			if (formField.step() != 1) {
				step = Float.valueOf(formField.step());
			}
			
			if (formField.decimalPrecision() != 2) {
				decimalPrecision = Integer.valueOf(formField.decimalPrecision());
			}
		}
		super.applyAnnotation(annotation, field, value);
	}
    
}
