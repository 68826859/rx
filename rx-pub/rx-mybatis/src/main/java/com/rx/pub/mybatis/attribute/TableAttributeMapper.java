package com.rx.pub.mybatis.attribute;

import java.util.List;
import com.rx.base.attribute.RxAttributeProvider;
import com.rx.base.attribute.RxAttributeable;
import com.rx.base.attribute.type.RxAttributeData;

public interface TableAttributeMapper<T> extends RxAttributeProvider<T>{
	
	@Override
	default RxAttributeData<?> getRxAttribute(RxAttributeable model,String name){
		return null;
	}
	
	@Override
	default List<? extends RxAttributeData<?>> getRxAttributes(RxAttributeable model,String... names){
		return null;
	}
	
	@Override
	default void setRxAttribute(RxAttributeable model,RxAttributeData<?> attribute) {
		
	}
	
	@Override
	default void setRxAttributes(RxAttributeable model,List<? extends RxAttributeData<?>> attributes) {
		
	}
}
