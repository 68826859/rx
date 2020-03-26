package com.rx.ext.data.proxy;

import java.lang.annotation.Annotation;

import com.rx.ext.Base;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.data.reader.Reader;

@ExtClass(alias="proxy.proxy")
public class Proxy extends Base{
	
	@ExtConfig
	private String type;
	
	@ExtConfig
	private Reader reader;
	
	

	@Override
	public void applyAnnotation(Annotation annotation,Class<?> clazz,Object value) throws Exception{
	super.applyAnnotation(annotation, clazz, value);
	if(annotation instanceof ExtClass){
			ExtClass extClass = (ExtClass)annotation;
			if(!ExtClass.NULL.equals(extClass.alias())){
				if(!this.isDefine){
					String as = extClass.alias();
					java.lang.String px = "proxy.";
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
	
	
	public Reader getReader() {
	return reader;
	}
	public void setReader(Reader reader) {
	this.reader = reader;
	}
}
