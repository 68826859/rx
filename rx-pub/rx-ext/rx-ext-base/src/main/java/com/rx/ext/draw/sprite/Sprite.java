package com.rx.ext.draw.sprite;

import java.lang.annotation.Annotation;

import com.rx.ext.Base;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
	@ExtClass(alias="sprite.sprite")
public class Sprite extends Base {

	
	@ExtConfig
	private String type;
	
	
	@ExtConfig
	public Boolean hidden;
	
	
	
	@Override
	public void applyAnnotation(Annotation annotation,Class<?> clazz,Object value) throws Exception{
	super.applyAnnotation(annotation, clazz, value);
	if(annotation instanceof ExtClass){
			ExtClass extClass = (ExtClass)annotation;
			if(!ExtClass.NULL.equals(extClass.alias())){
				if(!this.isDefine){
					String as = extClass.alias();
					String px = "sprite.";
					if(as.startsWith(px)){
						this.type = as.substring(px.length());
					}
				}
			}
	}
	}


	public String getType() {
	return type;
	}
}
