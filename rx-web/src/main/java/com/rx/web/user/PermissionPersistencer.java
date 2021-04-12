package com.rx.web.user;

import java.util.List;

import com.rx.base.user.RxPermissionable;
import com.rx.base.user.RxUserable;

public interface PermissionPersistencer {

	void regPermission(List<? extends RxPermissionable> list,Class<? extends RxUserable> userType);
	
	
	List<? extends RxPermissionable> getAllPermissionItems(Class<? extends RxUserable> userType);
	
	
	RxPermissionable getPermission(String permissionId);
}
