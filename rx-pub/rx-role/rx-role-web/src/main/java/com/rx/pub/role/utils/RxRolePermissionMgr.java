package com.rx.pub.role.utils;

import java.util.HashMap;
import java.util.Map;
import com.rx.base.user.RxGroupable;
import com.rx.web.user.RxUser;

public class RxRolePermissionMgr {

	
	
	
	private static Map<Class<? extends RxUser>, Class<? extends RxGroupable>> userRoleGroupType = new HashMap<>();
	
	/*
	 * 
	 */
	public static void registerUserRoleGroupType(Class<? extends RxUser> userClass,Class<? extends RxGroupable> groupClass){
		userRoleGroupType.put(userClass, groupClass);
	}
	
	public static Class<? extends RxGroupable> getUserRoleGroupType(Class<? extends RxUser> userClass){
		return userRoleGroupType.get(userClass);
	}
}
