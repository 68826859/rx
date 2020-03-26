package com.rx.ext.direct;

import java.lang.annotation.Annotation;

import com.rx.ext.Base;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
@ExtClass(alias="direct.provider")
public class Provider extends Base {
	@ExtConfig()
	private String type;
	
	
	@Override
	public void applyAnnotation(Annotation annotation,Class<?> clazz,Object value) throws Exception{
	super.applyAnnotation(annotation, clazz, value);
	if(annotation instanceof ExtClass){
			ExtClass extClass = (ExtClass)annotation;
			if(!ExtClass.NULL.equals(extClass.alias())){
				if(!this.isDefine){
					String as = extClass.alias();
					String px = "direct.";
					if(as.startsWith(px)){
						this.setType(as.substring(px.length()));
					}
				}
			}
	}
	}
	public String getType() {
	return type;
	}
	public void setType(String type) {
	this.type = type;
	}
}
