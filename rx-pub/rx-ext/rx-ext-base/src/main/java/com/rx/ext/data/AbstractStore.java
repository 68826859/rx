package com.rx.ext.data;

import java.lang.annotation.Annotation;

import com.rx.ext.Observable;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;

public class AbstractStore extends Observable{
	@ExtConfig()
	private String type;
	
	
	@ExtConfig()
	private String groupField;
	
	
	@Override
	public void applyAnnotation(Annotation annotation,Class<?> cls,Object value) throws Exception{
	if(annotation instanceof ExtClass){
			ExtClass extClass = (ExtClass)annotation;
			if(!ExtClass.NULL.equals(extClass.alias())){
				if(!this.isDefine){
					String as = extClass.alias();
					java.lang.String px = "store.";
					if(as.startsWith(px)){
						this.setType(as.substring(px.length()));
					}
				}
			}
	}
	super.applyAnnotation(annotation,cls,value);
	}
	
	
	public String getType() {
	return type;
	}
	public void setType(String type) {
	this.type = type;
	}

	public String getGroupField() {
	return groupField;
	}

	public void setGroupField(String groupField) {
	this.groupField = groupField;
	}
}
