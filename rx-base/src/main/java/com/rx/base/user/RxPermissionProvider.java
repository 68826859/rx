package com.rx.base.user;

import java.util.List;

import com.rx.base.user.RxPermissionable;

public interface RxPermissionProvider {
	
	
	public List<RxPermissionable> getUserPermissions(RxUserable user);
	
}
