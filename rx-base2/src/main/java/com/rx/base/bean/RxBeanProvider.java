package com.rx.base.bean;
import java.lang.reflect.Type;
import java.util.Map;

public interface RxBeanProvider {
	
	Object getBean(String name) throws Exception;
	
	<T> T getBean(String name, Class<T> requiredType) throws Exception;
	
	Object getBean(String name, Object... args) throws Exception;
	
	<T> T getBean(Class<T> requiredType) throws Exception;
	
	<T> T getBean(Class<T> requiredType, Object... args) throws Exception;
	
	<T> T getBean(Class<T> requiredType,Type[] genericType) throws Exception;
	
	<T> Map<String, T> getBeans(Class<T> clazz) throws Exception;
	
	boolean containsBean(String name);
	
	boolean isSingleton(String name) throws Exception;
	
	boolean isPrototype(String name) throws Exception;
	
	boolean isTypeMatch(String name,Class<?> typeToMatch) throws Exception;
	
	Class<?> getType(String name) throws Exception;
	
	String[] getAliases(String name);
	
	Class<?> getProxyTargetClass(Class<?> clazz);
	
}
