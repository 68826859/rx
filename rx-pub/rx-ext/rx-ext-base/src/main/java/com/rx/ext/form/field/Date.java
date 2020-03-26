package com.rx.ext.form.field;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

import com.rx.base.enm.RxDatePattern;
import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.CompApplyer;
import com.rx.ext.Component;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.annotation.ExtFormField;

@ExtClass(alias="widget.datefield")
public class Date extends Picker{
	
	@ExtConfig
	private String format;
	
	@ExtConfig
	private String maxValue;
	
	@ExtConfig
	private String minValue;
	
	@Override
	public void applyAnnotation(Annotation annotation,Field field,Object value) throws Exception{
		if(annotation instanceof ExtFormField){
				ExtFormField formField = (ExtFormField)annotation;
				RxModelField modelField = null;
				if(field != null){
					modelField = field.getAnnotation(RxModelField.class);
				}
				if(RxDatePattern.NULL != formField.datePattern()){
					this.setFormat(formField.datePattern().getJsFormat());
				}else if(modelField != null && RxDatePattern.NULL != modelField.datePattern()){
					this.setFormat(modelField.datePattern().getJsFormat());
				}
		}
		super.applyAnnotation(annotation,field,value);
	}


	public String getFormat() {
		return format;
	}


	public void setFormat(String format) {
		this.format = format;
	}


	public String getMaxValue() {
		return maxValue;
	}


	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}


	public String getMinValue() {
		return minValue;
	}


	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	
	public static class DateMaxValueNowApplyer implements CompApplyer{
		
		@Override
		public void applyAnnotation(Component comp, Annotation annotation, Field field, Object value) throws Exception {
			if(comp instanceof Date) {
				Date me = (Date)comp;
				String ft = me.getFormat();
				String maxV = new SimpleDateFormat(RxDatePattern.getByJsFormat(ft).getFormat()).format(new java.util.Date());
				me.setMaxValue(maxV);
			}
		}
	}
	public static class DateMinValueNowApplyer implements CompApplyer{
		
		@Override
		public void applyAnnotation(Component comp, Annotation annotation, Field field, Object value) throws Exception {
			if(comp instanceof Date) {
				Date me = (Date)comp;
				String ft = me.getFormat();
				String mV = new SimpleDateFormat(RxDatePattern.getByJsFormat(ft).getFormat()).format(new java.util.Date());
				me.setMinValue(mV);
			}
		}
	}
}
