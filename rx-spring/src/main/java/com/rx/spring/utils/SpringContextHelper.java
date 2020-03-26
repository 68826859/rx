package com.rx.spring.utils;

import com.rx.base.service.BaseService;
import com.rx.spring.SpringBaseService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
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
