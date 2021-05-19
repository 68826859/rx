package com.rx.base.data;

public abstract class RxData<T> implements RxDataable<T> {
	
	
	private T data;
	
	
	@Override
	public T getData() {
		return data;
	}


	public void setData(T data) {
		this.data = data;
	}
	
	
}
