package com.rx.base.model;

import java.io.Serializable;

public interface RxModelable<T> extends Serializable {
	
	T getId();
	String getName();
	
}
