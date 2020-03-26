package com.rx.extrx.component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.rx.base.enm.RxNullEnum;
import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.annotation.ExtFormField;
import com.rx.ext.enums.NullEnum;
import com.rx.ext.form.field.ComboBox;

@ExtClass(alias="widget.enumcombo")
public class EnumComboBox extends ComboBox{
	
	@ExtConfig()
	private Class<?> em;
	
	@Override
	public void applyAnnotation(Annotation annotation,Field field,Object value) throws Exception{
		if(annotation instanceof ExtFormField){
			ExtFormField formField = (ExtFormField)annotation;
			RxModelField modelField = null;
			if(field != null){
				modelField = field.getAnnotation(RxModelField.class);
			}
			
			Class<? extends Enum<?>> ecls = formField.em();
			if(ecls != NullEnum.class){
				this.em = ecls;
			}else if(modelField != null && !RxNullEnum.class.equals(modelField.em())){
				this.em = modelField.em();
			}
			
		}
		super.applyAnnotation(annotation, field, value);
	}
	public Class<?> getEm() {
		return em;
	}
	public void setEm(Class<?> em) {
		this.em = em;
	}

}
