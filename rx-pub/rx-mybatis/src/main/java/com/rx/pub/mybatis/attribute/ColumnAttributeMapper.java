package com.rx.pub.mybatis.attribute;

import java.util.List;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.rx.base.attribute.RxAttributeProvider;
import com.rx.base.attribute.RxAttributeable;
import com.rx.base.attribute.type.RxAttributeData;

import tk.mybatis.mapper.annotation.RegisterMapper;

@RegisterMapper
public interface ColumnAttributeMapper<T> extends RxAttributeProvider<T>{
	
	@Override
	@SelectProvider(type = ColumnAttributeProvider.class, method = "dynamicSQL")
	RxAttributeData<?> getRxAttribute(RxAttributeable model,String name);
	
	@Override
	@SelectProvider(type = ColumnAttributeProvider.class, method = "dynamicSQL")
	List<? extends RxAttributeData<?>> getRxAttributes(RxAttributeable model,String... names);
	
	@Override
	@UpdateProvider(type = ColumnAttributeProvider.class, method = "dynamicSQL")
	void setRxAttribute(RxAttributeable model,RxAttributeData<?> attribute);
	
	@Override
	@UpdateProvider(type = ColumnAttributeProvider.class, method = "dynamicSQL")
	void setRxAttributes(RxAttributeable model,List<? extends RxAttributeData<?>> attributes);
}
