package com.rx.spring.utils;

import java.lang.reflect.Type;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import com.rx.base.bean.RxBeanHelper;
import com.rx.base.service.BaseService;

@Component
public class SpringContextHelper extends RxBeanHelper implements ApplicationContextAware {
	
    private ApplicationContext springContext = null;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        springContext = context;
    }

    public SpringContextHelper() {
    	super();
    }
    
    
    public ApplicationContext getApplicationContext() {
    	return springContext;
    }

    
    public static ApplicationContext getSpringContext() {
    	try {
			return ((SpringContextHelper)RxBeanHelper.getBeanProvider()).getApplicationContext();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public static BaseService<?> getBeanService(Class<?> clazz) {

    	try {
			return RxBeanHelper.getBeanProvider().getBean(BaseService.class,new Type[] {clazz});
		} catch (Exception e) {
			e.printStackTrace();
		}
    	/*
        BaseService<?> service = null;
        String[] beans = getSpringContext().getBeanDefinitionNames();
        for (String beanName : beans) {
            Class<?> beanType = getSpringContext().getType(beanName);
            if (BaseService.class.isAssignableFrom(beanType)) {
                service = (BaseService<?>) getSpringContext().getBean(beanName);
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
        */
        return null;
    }
    
    /**
     * 获取对象泛型
     * @param clazz
     * @return
    
    
    public static Class<?> getBeanActualType(Object obj, Class<?> targetClass,int index) {
    	Class<?> cls;
    	if(AopUtils.isAopProxy(obj)){
    		cls = AopUtils.getTargetClass(obj);
    	}else {
    		cls = obj.getClass();
    	}
    	return RxBeanHelper.getClassActualType(cls,targetClass,0);
    } */

	@Override
	public boolean containsBean(String name) {
		return springContext.containsBean(name);
	}

	@Override
	public boolean isSingleton(String name) throws Exception {
		return springContext.isSingleton(name);
	}

	@Override
	public boolean isPrototype(String name) throws Exception {
		return springContext.isPrototype(name);
	}

	@Override
	public boolean isTypeMatch(String name, Class<?> typeToMatch) throws Exception {
		return springContext.isTypeMatch(name, typeToMatch);
	}

	@Override
	public Class<?> getType(String name) throws Exception {
		return springContext.getType(name);
	}

	@Override
	public String[] getAliases(String name) {
		return springContext.getAliases(name);
	}


	@Override
	public Object getBean(String name) throws Exception {
		return springContext.getBean(name);
	}


	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws Exception {
		return springContext.getBean(name, requiredType);
	}


	@Override
	public Object getBean(String name, Object... args) throws Exception {
		return springContext.getBean(name, args);
	}


	@Override
	public <T> T getBean(Class<T> requiredType) throws Exception {
		return springContext.getBean(requiredType);
	}


	@Override
	public <T> T getBean(Class<T> requiredType, Object... args) throws Exception {
		return springContext.getBean(requiredType, args);
	}


	@Override
	public <T> Map<String, T> getBeans(Class<T> clazz) throws Exception {
		return springContext.getBeansOfType(clazz);
	}

	@Override
	public Class<?> getProxyTargetClass(Class<?> clazz) {
		return ClassUtils.getUserClass(clazz);
	}

	@Override
	public <T> T getBean(Class<T> requiredType, Type[] genericType) throws Exception {
		Map<String, T> map = springContext.getBeansOfType(requiredType);
		if(map.size() > 0) {
			loop1:for(T t:map.values()) {
				ResolvableType resolvableType = ResolvableType.forClass(requiredType,getProxyTargetClass(t.getClass()));
				for(int i = 0;i<genericType.length;i++) {
					if(genericType[i] != resolvableType.getGeneric(i).resolve()) {
						continue loop1;
					}
				}
				return t;
			}
		}
		return null;
	}
}
