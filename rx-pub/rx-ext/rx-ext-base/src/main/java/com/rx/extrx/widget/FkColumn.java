package com.rx.extrx.widget;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import com.rx.base.result.type.ValidateException;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.base.model.annotation.RxModelField;

@ExtClass(alias="widget.fkcolumn",alter="Rx.widget.FkColumn")
public class FkColumn extends RemoteColumn{
	
	private static String method = "listFk";
	
	@ExtConfig()
	public String className;
	
	public FkColumn(){
		//Store<ListVo> sto = new SpringProviderStore<ListVo>(TreeVo.class,ExtController.class,method);
		//this.setStore(sto);
	}
	
	public FkColumn(Class<?> cn){
		this();
		this.className = cn.getName();
	}
	
	@Override
	public void applyAnnotation(Annotation annotation,Field field,Object value) throws Exception{
		if (field != null) {
			RxModelField modelField = field.getAnnotation(RxModelField.class);
			if(modelField != null) {
				Class<?> cls = modelField.fk();
				if(cls == Object.class){
					throw new ValidateException("外键下拉框缺少外键配置:" + field.getName());
				}
		        this.className = cls.getName();
			}
		}
		super.applyAnnotation(annotation, field, value);
	}
}
