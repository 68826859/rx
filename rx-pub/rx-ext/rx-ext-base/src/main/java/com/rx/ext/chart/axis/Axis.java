package com.rx.ext.chart.axis;

import java.lang.annotation.Annotation;

import com.rx.ext.Base;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.chart.sprite.Label;

@ExtClass(alias="axis.axis")
public class Axis extends Base {

	
	@ExtConfig
	private String type;
	
	@ExtConfig
	public String title;
	
	@ExtConfig
	public Position postion;
	
	@ExtConfig
	public Label label;
	
	@ExtConfig(key="grid")
	public Boolean gridBool;
	
	@ExtConfig
	public Integer minimum;
	@ExtConfig
	public Integer maximum;
	@ExtConfig
	public Integer maxZoom;
	@ExtConfig
	public Integer minZoom;
	@ExtConfig
	public Integer margin;
	
	public enum Position{
	left,bottom,right,top,radial,angular;
	}
	
	
	
	@Override
	public void applyAnnotation(Annotation annotation,Class<?> clazz,Object value) throws Exception{
	super.applyAnnotation(annotation, clazz, value);
	if(annotation instanceof ExtClass){
			ExtClass extClass = (ExtClass)annotation;
			if(!ExtClass.NULL.equals(extClass.alias())){
				if(!this.isDefine){
					String as = extClass.alias();
					String px = "axis.";
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
