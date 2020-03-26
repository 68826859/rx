package com.rx.pub.mybatis.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.rx.base.model.annotation.RxModelField;
import com.rx.pub.mybatis.impls.MyBatisMapper;
import com.rx.spring.utils.SpringContextHelper;

public class MybatisModelHelper {
	
	
	
	public static Class<?> getInterfaceGeneric(Class<?> clazz) {
		for(Type type : clazz.getGenericInterfaces()) {
			if(type instanceof ParameterizedType) {
				if(((ParameterizedType) type).getRawType().getTypeName().equals(MyBatisMapper.class.getName())) {
					return (Class<?>)((ParameterizedType) type).getActualTypeArguments()[0];
				}
			}
		}
		return null;
	}
	
	

	
	
	private static List<ModelDefine> models = null;
	
	//@Override
	public static List<ModelDefine> getModelRefs(Class<?> modelClass) {
		if(models == null) {
			models = new ArrayList<ModelDefine>();
			initAllModelClass();
		}
		
		List<ModelDefine> res = new ArrayList<ModelDefine>();
		for(ModelDefine md : models) {
			if(md.getModelClass() == modelClass) {
				res.add(md);
			}
		}
		return res;
	}

	
	public static void initAllModelClass() {
		String[] beanNames = SpringContextHelper.springContext.getBeanNamesForType(MyBatisMapper.class);
		for(String beanName:beanNames) {
			Class<?> beanType = SpringContextHelper.springContext.getType(beanName);
			Class<?> modelClass = getInterfaceGeneric(beanType);
			Field[] fields = modelClass.getDeclaredFields();
			for(Field field:fields) {
				RxModelField rmf = field.getAnnotation(RxModelField.class);
				if(rmf != null && !Object.class.equals(rmf.fk())) {
					models.add(new ModelDefine(rmf.fk(),field,modelClass,beanName));
				}
			}
		}
	}
	
}
