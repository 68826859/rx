package com.rx.base;

import java.lang.reflect.Type;

import com.rx.base.bean.RxBeanHelper;

public interface RxProviderable {
	
	default <T> T getRxProvider(Class<T> targetClass,Type[] genericTypes) {
		if(genericTypes == null) {
			return RxBeanHelper.getFactoryBean(targetClass);
		}
		return RxBeanHelper.getFactoryBean(targetClass,genericTypes);
	}
}
