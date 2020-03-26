package com.rx.extrx.spring;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ContextLoader;

import com.rx.ext.annotation.ExtClass;
import com.rx.extrx.widget.ServerMethod;
import com.rx.extrx.widget.ServerProvider;

@ExtClass()
public class SpringProvider extends ServerProvider{

	
	
	@Override
	public void applyAnnotation(Annotation annotation,Class<?> clazz,Object value) throws Exception{
	if(annotation instanceof RestController){
			RestController restAn= (RestController)annotation;
			RequestMapping mapperAn = clazz.getAnnotation(RequestMapping.class);
			if(mapperAn != null){
				String path = "";
				try{
					path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getContextPath();
				}catch(Exception e){}
				this.setUrl(/*path + */mapperAn.value()[0].substring(1));
			}
	}
	super.applyAnnotation(annotation, clazz, value);
	}
	
	
	@Override
	public void applyAnnotation(Annotation annotation,Method method,Object value) throws Exception{
	if(annotation instanceof RequestMapping && method != null){//这就是proxy
			RequestMapping reqAn= (RequestMapping)annotation;
			
			org.springframework.web.bind.annotation.RequestMethod[] med = reqAn.method();
			ServerMethod sm = new ServerMethod();
			sm.setName(method.getName());
			String[] path = reqAn.path();
			if(path.length == 0){
				path = reqAn.value();
			}
			if(path.length != 0){
				sm.setUrl(this.getUrl()+path[0]);
			}
			if(med != null && med.length > 0){
				sm.setMethod(med[0].name());
			}
			this.addServerMethod(sm);
	}else{
			
	}
	super.applyAnnotation(annotation,method,value);
	}
	
	
	/*
	@Override
	public void applyAnnotation(Annotation annotation,Class<?> clazz,Object value) throws Exception{
	if(annotation instanceof RestController){
			RestController restAn= (RestController)annotation;
			RequestMapping mapperAn = clazz.getAnnotation(RequestMapping.class);
			if(mapperAn != null){
				String path = "";
				try{
					path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getContextPath();
				}catch(Exception e){}
				this.setUrl(path + mapperAn.value()[0]);
			}
	}
	super.applyAnnotation(annotation, clazz, value);
	}
	
	
	@Override
	public void applyAnnotation(Annotation annotation,Method mehod,Object value) throws Exception{
	if(annotation instanceof RequestMapping && mehod != null){//这就是proxy
			RequestMapping reqAn= (RequestMapping)annotation;
			String[] path = reqAn.value();
			org.springframework.web.bind.annotation.RequestMethod[] med = reqAn.method();
			Ajax ajax = new Ajax();
			ajax.setUrl(this.getUrl()+path[0]);
			Json json = new Json();
			json.setRootProperty("data");
			ajax.setReader(json);
			if(med != null && med.length > 0){
				ajax.setMethod(med[0].name());
			}
			this.addProxy(mehod.getName(), ajax);
	}else{
			
	}
	super.applyAnnotation(annotation,mehod,value);
	}*/

}
