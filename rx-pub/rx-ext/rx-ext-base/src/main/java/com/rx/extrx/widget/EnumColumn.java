package com.rx.extrx.widget;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.ext.enums.NullEnum;
import com.rx.ext.grid.column.Column;
import com.rx.base.enm.RxNullEnum;
import com.rx.base.model.annotation.RxModelField;


@ExtClass(alias="widget.enumcolumn")
public class EnumColumn extends Column{
	@ExtConfig()
	private Class<? extends Enum<?>> em;
	
	public EnumColumn(){}
	
	public EnumColumn(Class<? extends Enum<?>> em){
		this.setEm(em);
	}
	
	@Override
	public void applyAnnotation(Annotation annotation,Field field,Object value) throws Exception{
		if(annotation instanceof ExtGridColumn){
			RxModelField modelField = null;
			if(field != null){
				modelField = field.getAnnotation(RxModelField.class);
			}
			
			ExtGridColumn columnField = (ExtGridColumn)annotation;
			Class<? extends Enum<?>> ecls = columnField.em();
			if(ecls != NullEnum.class){
				this.em = ecls;
			}else if(modelField != null && !RxNullEnum.class.equals(modelField.em())){
				this.em = modelField.em();
			}
		}
		super.applyAnnotation(annotation, field, value);
	}

	public Class<? extends Enum<?>> getEm() {
		return em;
	}

	public void setEm(Class<? extends Enum<?>> em) {
		this.em = em;
	}
}
