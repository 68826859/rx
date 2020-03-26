package com.rx.ext.selection;

import java.lang.annotation.Annotation;

import com.rx.ext.Base;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;

@ExtClass(alias="selection.abstract")
public class Model extends Base {

	
	@ExtConfig
	private String type;
	
	
	@Override
	public void applyAnnotation(Annotation annotation,Class<?> cls,Object value) throws Exception{
	if(annotation instanceof ExtClass){
			ExtClass extClass = (ExtClass)annotation;
			if(!ExtClass.NULL.equals(extClass.alias())){
				if(!this.isDefine){
					String as = extClass.alias();
					String px = "selection.";
					if(as.startsWith(px)){
						this.type = as.substring(px.length());
					}
				}
			}
	}
	super.applyAnnotation(annotation,cls,value);
	}

	public String getType() {
	return type;
	}
}
