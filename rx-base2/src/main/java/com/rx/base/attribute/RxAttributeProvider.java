package com.rx.base.attribute;

import java.util.List;

import com.rx.base.attribute.type.RxAttributeData;

public interface RxAttributeProvider<T>{

	RxAttributeData<?> getRxAttribute(RxAttributeable model,String name);
	
	List<? extends RxAttributeData<?>> getRxAttributes(RxAttributeable model,String... names);
	
	void setRxAttribute(RxAttributeable model,RxAttributeData<?> attribute);
	
	void setRxAttributes(RxAttributeable model,List<? extends RxAttributeData<?>> attributes);
	
	
	
}
