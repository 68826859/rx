package com.rx.base.attribute.type;

import com.rx.base.data.RxData;

public class RxAttributeData<T> extends RxData<T> {
	
	private String name; 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
