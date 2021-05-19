package com.rx.base.attribute;

import java.lang.reflect.Type;
import java.util.List;
import com.rx.base.RxProviderable;
import com.rx.base.attribute.type.RxAttributeData;

public interface RxAttributeable extends RxProviderable{
	
	
	default String getOnlyKey() {
		return null;
	};
	
	default RxAttributeData<?> getRxAttribute(String name){
		
		RxAttributeProvider<?> provider =  getRxProvider(RxAttributeProvider.class,new Type[] {this.getClass()});
		return provider.getRxAttribute(this,name);
	};
	
	default List<? extends RxAttributeData<?>> getRxAttributes(String... name){

		RxAttributeProvider<?> provider =  getRxProvider(RxAttributeProvider.class,new Type[] {this.getClass()});
		return provider.getRxAttributes(this, name);
	};
	
	default void setRxAttribute(RxAttributeData<?> attribute){
		
		RxAttributeProvider<?> provider =  getRxProvider(RxAttributeProvider.class,new Type[] {this.getClass()});
		provider.setRxAttribute(this,attribute);
	};
	
	default void setRxAttributes(List<? extends RxAttributeData<?>> attributes){
		
		RxAttributeProvider<?> provider =  getRxProvider(RxAttributeProvider.class,new Type[] {this.getClass()});
		provider.setRxAttributes(this,attributes);
	};
	
}
