package com.rx.base.bean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class RxBeanHelper implements RxBeanProvider {
	
	private static RxBeanHelper _factory = null;
	
	protected RxBeanHelper() {
		_factory = this;
	}
	
	public static RxBeanProvider getBeanProvider() throws Exception{
		
		if(_factory == null) {
			throw new Exception("尚未有Bean工厂实现RxBeanHelper");
		}
		return _factory;
	}
	
	public static<T> T getFactoryBean(Class<T> requiredType) {
		try {
			return getBeanProvider().getBean(requiredType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static<T> T getFactoryBean(String name, Class<T> requiredType){
		try {
			return getBeanProvider().getBean(name,requiredType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object getFactoryBean(String name) {
		try {
			return getBeanProvider().getBean(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static<T> T getFactoryBean(Class<T> requiredType,Type[] genericTypes){
		try {
			return getBeanProvider().getBean(requiredType,genericTypes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
     * 获取类或接口的泛型
     * @param clazz
     * @return
     */
    
    public static Class<?> getClassActualType(Class<?> clazz, Class<?> targetClass,int index) {
    	
    	if(targetClass.isInterface()){
    		Type[] types = clazz.getGenericInterfaces();
    		if(types.length > 0) {
	    		for(Type type : types) {
	    			if(type instanceof ParameterizedType) {
	    				if(((ParameterizedType)type).getRawType().getTypeName().equals(targetClass.getName())) {
		    				return (Class<?>)((ParameterizedType)type).getActualTypeArguments()[index];
		    			}
	    			}
	    		}
    		}
    		Class<?> res = null;
    		if(!clazz.isInterface()) {
    			if(clazz.getSuperclass() != Object.class) {
        			res = getClassActualType(clazz.getSuperclass(),targetClass,index);
        		}
    		}
    		if(res != null) {
    			return res;
    		}
    		Class<?>[] ss = clazz.getInterfaces();
			for(Class<?> s:ss) {
				res = getClassActualType(s,targetClass,index);
				if(res != null) {
					return res;
				}
			}
    	}else {
    		if(clazz.getSuperclass() == targetClass) {
    			return getSuperclassActualType(clazz,index);
    		} else if(clazz.getSuperclass() != Object.class) {
    			return getClassActualType(clazz.getSuperclass(),targetClass,index);
    		}
    	}
        return null;
    }
    /**
     * 获取父类泛型
     * @param clazz
     * @param index
     * @return
     */
    public static Class<?> getSuperclassActualType(Class<?> clazz,int index) {
    	return (Class<?>) ((ParameterizedType)clazz.getGenericSuperclass()).getActualTypeArguments()[index];
    }
    
    /**
     * 获取类或接口 的直接接口 泛型
     * @param clazz
     * @param targetClass
     * @param index
     * @return
     */
    public static Class<?> getInterfacesActualType(Class<?> clazz,Class<?> targetClass,int index) {
    	Type[] types = clazz.getGenericInterfaces();
		if(types.length > 0) {
    		for(Type type : types) {
    			if(type instanceof ParameterizedType) {
    				if(((ParameterizedType)type).getRawType().getTypeName().equals(targetClass.getName())) {
	    				return (Class<?>)((ParameterizedType)type).getActualTypeArguments()[index];
	    			}
    			}
    		}
		}
		return null;
    }
    
}
