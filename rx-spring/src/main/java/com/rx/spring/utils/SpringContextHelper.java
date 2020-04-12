package com.rx.spring.utils;

import com.rx.base.service.BaseService;
import com.rx.spring.SpringBaseService;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

@Component
public class SpringContextHelper implements ApplicationContextAware {
    //public static final String SESSION_CATALOG = "spring:session:sessions:";//redis存session目录

    //public static final String SYS_USER_SESSION = "_sys_user_session";//后台session key
    //public static final String HAILE_A_KEY = "HAILE_A_KEY";
    public static ApplicationContext springContext = null;

    /**
     * 获取bean实例对象
     *
     * @param beanName
     * @param clazz
     * @return
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return springContext.getBean(beanName, clazz);
    }

    public static <T> T getBean(Class<T> clazz) {
    	
        return springContext.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        springContext = context;
    }


    public static BaseService<?> getBeanService(Class<?> clazz) {

        BaseService<?> service = null;
        String[] beans = springContext.getBeanDefinitionNames();
        for (String beanName : beans) {
            Class<?> beanType = springContext.getType(beanName);
            if (BaseService.class.isAssignableFrom(beanType)) {
                service = (BaseService<?>) springContext.getBean(beanName);
                if (Proxy.class.isAssignableFrom(beanType)) {
                    String bn = service.toString();//cn.com.gdca.remot.deposit.service.impl.RtDepositServiceImpl@70fdad84
                    bn = bn.substring(0, bn.indexOf("@"));
                    Class<?> cls = null;
                    try {
                        cls = Class.forName(bn);
                        if (SpringBaseService.class.isAssignableFrom(cls)) {
                            Class<?> pc = (Class<?>) ((ParameterizedType) cls.getGenericSuperclass()).getActualTypeArguments()[0];
                            if (clazz.equals(pc)) {
                                return service;
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if (service instanceof SpringBaseService) {
                    Class<?> pc = (Class<?>) ((ParameterizedType) service.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                    if (clazz.equals(pc)) {
                        return service;
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * 获取对象泛型
     * @param clazz
     * @return
     */
    
    public static Class<?> getBeanActualType(Object obj, Class<?> targetClass,int index) {
    	Class<?> cls;
    	if(AopUtils.isAopProxy(obj)){
    		cls = AopUtils.getTargetClass(obj);
    	}else {
    		cls = obj.getClass();
    	}
    	return SpringContextHelper.getClassActualType(cls,targetClass,0);
    }
    /**
     * 获取类的泛型
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

    public static Class<?> getSuperclassActualType(Class<?> clazz,int index) {
    	return (Class<?>) ((ParameterizedType)clazz.getGenericSuperclass()).getActualTypeArguments()[index];
    }
    
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
    
    
    public static <T> Map<String, T> getBeans(Class<T> clazz) {
        return BeanFactoryUtils.beansOfTypeIncludingAncestors(springContext, clazz);
    }


    public static <T> Collection<T> getBeanCollections(Class<T> clazz) {
        Map<String, T> map = getBeans(clazz);
        if (!map.isEmpty()) {
            return map.values();
        }
        return null;
    }

}
