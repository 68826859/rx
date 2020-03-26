package com.rx.pub.dic.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.rx.ext.Base;
import com.rx.ext.DefinePlugin;
import com.rx.pub.dic.annotation.PermissionDic;
import com.rx.pub.dic.enm.DicPermissionEnum;

public class ExtDefinePermissionPlugin implements DefinePlugin<Base> {
	/*
	 * static{ Base.setupPlugin(ExtDefinePermissionPlugin.class); }
	 */

	private HashMap<String, String> permissions;

	@Override
	public void applyBase(Base base) {
		permissions = new HashMap<String, String>();
	}

	private void addPermission(PermissionDic pm) {
		if (pm != null) {
			DicPermissionEnum[] ems = pm.value();
			for (DicPermissionEnum em : ems) {
				permissions.put(em.getCode(), em.getCode());
			}
		}
	}

	@Override
	public void applyField(Field field, Object value) throws Exception {
		PermissionDic pm = field.getAnnotation(PermissionDic.class);
		addPermission(pm);
		// System.out.println("插件拦截到field:" + field.getName());
	}

	@Override
	public void applyClass(Class<?> clazz, Object value) throws Exception {
		if (clazz != null) {
			PermissionDic pm = clazz.getAnnotation(PermissionDic.class);
			addPermission(pm);
			// System.out.println("插件拦截到clazz:" + clazz.getName());
		}
	}

	@Override
	public void applyMethod(Method method, Object object) throws Exception {
		PermissionDic pm = method.getAnnotation(PermissionDic.class);
		addPermission(pm);
		// System.out.println("插件拦截到method:" + method.getName());
	}

	@Override
	public void applyFinish(Base base) {
		if (permissions != null && permissions.size() > 0) {
			base.addConfig("rightId", this.permissions.values());
		}
	}

}
