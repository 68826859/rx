package com.rx.ext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface DefinePlugin<T>{
	
	public void applyBase(Base base);
	
	public void applyField(Field field,Object value) throws Exception;
	
	public void applyClass(Class<?> clazz,Object value) throws Exception;
	
	public void applyMethod(Method method,Object object) throws Exception;
	
	public void applyFinish(Base base);
}
