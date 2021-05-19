package com.rx.base.convert;

import java.lang.reflect.Type;

import com.rx.base.bean.RxBeanHelper;

public interface RxConvertable{
	
	@SuppressWarnings("unchecked")
	default <T> T convertTo(Class<T> clazz){
		RxConvertProvider<T> converter = RxBeanHelper.getFactoryBean(RxConvertProvider.class,new Type[] {clazz});
		return converter.convert(this);
	};
}
