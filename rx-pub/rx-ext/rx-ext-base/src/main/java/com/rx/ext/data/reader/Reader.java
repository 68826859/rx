package com.rx.ext.data.reader;

import java.lang.annotation.Annotation;

import com.rx.ext.Base;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;

@ExtClass(alias="reader.base")
public class Reader extends Base{
	
	@ExtConfig()
	private String type;
	
	
	@ExtConfig
	private String rootProperty;

	@ExtConfig
	private String totalProperty;
	
	
	
	@Override
	public void applyAnnotation(Annotation annotation,Class<?> clazz,Object value) throws Exception{
		
	if(annotation instanceof ExtClass){
			ExtClass extClass = (ExtClass)annotation;
			if(!ExtClass.NULL.equals(extClass.alias())){
				if(!this.isDefine){
					String as = extClass.alias();
					java.lang.String px = "reader.";
					if(as.startsWith(px)){
						this.setType(as.substring(px.length()));
					}
				}
			}
	}
	super.applyAnnotation(annotation, clazz, value);
	}
	public String getType() {
	return type;
	}
	public Reader setType(String type) {
	this.type = type;
	return this;
	}
	
	
	public String getRootProperty() {
	return rootProperty;
	}
	public Reader setRootProperty(String rootProperty) {
	this.rootProperty = rootProperty;
	return this;
	}
	public String getTotalProperty() {
	return totalProperty;
	}
	public Reader setTotalProperty(String totalProperty) {
	this.totalProperty = totalProperty;
	return this;
	}
}
