package com.rx.base.serialize;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public abstract class SnapshotList<T> extends ArrayList<T> implements Snapshot<T>{

	private static final long serialVersionUID = 1L;

	private Class<T> clazz;
	
	
	public void applyShot(Object st,Field field) {
		Type type = field.getGenericType();
		if (type instanceof ParameterizedType) {
			Type ty = ((ParameterizedType)type).getActualTypeArguments()[0];
			if(ty instanceof Class<?>) {
				clazz  = (Class<T>)ty;
			}else {
				System.out.println("SnapshotList未定义明确的泛型"+ty.toString());
			}
		}else {
			System.out.println("SnapshotList未定义泛型");
		}
		this.applyShot((T)st);
	}


	public Class<T> getClazz() {
		return clazz;
	}
}
