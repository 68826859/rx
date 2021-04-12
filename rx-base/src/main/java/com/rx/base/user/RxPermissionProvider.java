package com.rx.base.user;

import java.util.List;

public interface RxPermissionProvider {
	
	
	public List<? extends RxPermissionable> getUserPermissions(RxUserable user);
	
}
