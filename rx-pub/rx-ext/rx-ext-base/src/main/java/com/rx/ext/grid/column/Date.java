package com.rx.ext.grid.column;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.rx.base.enm.RxDatePattern;
import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.annotation.ExtGridColumn;

@ExtClass(alias="widget.datecolumn")
public class Date extends Column{
	
	@ExtConfig()
	private String format;
	
	
	@Override
	public void applyAnnotation(Annotation annotation,Field field,Object value) throws Exception{
		if(annotation instanceof ExtGridColumn){
			ExtGridColumn columnField = (ExtGridColumn)annotation;
			RxModelField modelField = null;
			if(field != null){
				modelField = field.getAnnotation(RxModelField.class);
			}
			if(RxDatePattern.NULL != columnField.datePattern()){
				this.setFormat(columnField.datePattern().getJsFormat());
			}else if(modelField != null && RxDatePattern.NULL != modelField.datePattern()){
				this.setFormat(modelField.datePattern().getJsFormat());
			}
		}
		super.applyAnnotation(annotation, field, value);
	}
	
	public String getFormat() {
		return format;
	}
	
	
	public void setFormat(String format) {
		this.format = format;
	}
	
}
